import logging
import os
import requests
import json
from dotenv import load_dotenv
import time
from typing import Optional, List, Dict, Any
from dataclasses import dataclass


@dataclass
class StudyPlanConfig:
    study_plans: List[str] = None

    def __post_init__(self):
        if self.study_plans is None:
            self.study_plans = [
                "top-interview-150",
                "top-100-liked",
                "leetcode-75",
                "top-sql-50",
                "introduction-to-pandas",
            ]


class LeetCodeStudyPlanClient:
    BASE_URL = "https://leetcode.com/graphql"
    DEFAULT_HEADERS = {
        "Origin": "https://leetcode.com",
        "Content-Type": "application/json",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36",
    }

    def __init__(self):
        load_dotenv()
        self._setup_logging()
        self.session = requests.Session()
        self.session.headers.update(
            {
                "X-CSRFToken": os.environ.get("CSRF_TOKEN"),
                "Cookie": os.environ.get("COOKIE"),
                "uuuserid": os.environ.get("UUUSERID"),
                "random-uuid": os.environ.get("RANDOM_UUID"),
            }
        )
        self.output_dir = "data/output"
        os.makedirs(self.output_dir, exist_ok=True)

    def _setup_logging(self):
        logging.basicConfig(
            level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
        )

    def _make_request(self, payload: Dict[str, Any], plan_slug: str) -> Optional[Dict]:
        headers = self.DEFAULT_HEADERS.copy()
        headers["Referer"] = f"https://leetcode.com/studyplan/{plan_slug}/"

        try:
            response = self.session.post(self.BASE_URL, headers=headers, json=payload)
            response.raise_for_status()
            return response.json()
        except requests.RequestException as e:
            logging.error(f"Request failed: {e}")
            return None
        except json.JSONDecodeError as e:
            logging.error(f"JSON decode error: {e}")
            return None

    def join_study_plan(
        self, plan_slug: str, weekly_task_schedule: Optional[List[int]] = None
    ) -> bool:
        payload = {
            "query": """
                mutation joinStudyPlan($slug: String!, $weeklyTaskSchedule: [Int!]) {
                    joinStudyPlan(planSlug: $slug, weeklyTaskSchedule: $weeklyTaskSchedule) {
                        ok
                        progressId
                    }
                }
            """,
            "variables": {
                "slug": plan_slug,
                "weeklyTaskSchedule": weekly_task_schedule,
            },
            "operationName": "joinStudyPlan",
        }

        data = self._make_request(payload, plan_slug)
        if data and data.get("data", {}).get("joinStudyPlan", {}).get("ok"):
            logging.info("Study plan joined successfully")
            return True
        logging.error(f"Failed to join study plan: {data}")
        return False

    def study_plan_progress(
        self, plan_slug: str, history_id: Optional[str] = None
    ) -> Optional[Dict]:
        payload = {
            "query": """
                query studyPlanProgress($slug: String!, $historyId: ID) {
                    studyPlanV2ProgressDetail(planSlug: $slug, id: $historyId) {
                        id status weeklyTaskScheduleResettable finishedQuestionNum
                        studyPlanDetail { questionNum planSubGroups { slug questions { titleSlug status } } }
                    }
                }
            """,
            "variables": {"slug": plan_slug, "historyId": history_id},
            "operationName": "studyPlanProgress",
        }

        data = self._make_request(payload, plan_slug)
        if data and data.get("data", {}).get("studyPlanV2ProgressDetail"):
            logging.info("Progress retrieved successfully")
            return data
        return None

    def mark_solved_question(self, title_slug: str, plan_slug: str) -> Optional[Dict]:
        payload = {
            "query": """
                mutation markSolvedQuestion($titleSlug: String!, $planSlug: String!) {
                    markSolvedQuestion(titleSlug: $titleSlug, planSlug: $planSlug) {
                        ok progressDetail { id finishedQuestionNum status }
                    }
                }
            """,
            "variables": {"titleSlug": title_slug, "planSlug": plan_slug},
            "operationName": "markSolvedQuestion",
        }

        return self._make_request(payload, plan_slug)

    def get_study_plan_details(self, plan_slug: str) -> Optional[Dict]:
        payload = {
            "query": """
                query studyPlanPastSolved($slug: String!) {
                    studyPlanV2Detail(planSlug: $slug) {
                        planSubGroups { slug questions { titleSlug status } }
                    }
                }
            """,
            "variables": {"slug": plan_slug},
            "operationName": "studyPlanPastSolved",
        }

        return self._make_request(payload, plan_slug)

    def quit_study_plan(self, plan_slug: str) -> bool:
        payload = {
            "query": """
                mutation quitStudyPlan($slug: String!) {
                    quitStudyPlan(planSlug: $slug) { progressId ok deleted }
                }
            """,
            "variables": {"slug": plan_slug},
            "operationName": "quitStudyPlan",
        }

        data = self._make_request(payload, plan_slug)
        if data and data.get("data", {}).get("quitStudyPlan", {}).get("ok"):
            logging.info("Successfully quit study plan")
            return True
        logging.error(f"Failed to quit study plan: {data}")
        return False

    def process_study_plans(self, config: StudyPlanConfig):
        for plan_slug in config.study_plans:
            logging.info(f"{'='*40}\nProcessing study plan: {plan_slug}")

            self.quit_study_plan(plan_slug)
            time.sleep(2)  # Reduced sleep time

            self.join_study_plan(plan_slug)
            time.sleep(2)

            details = self.get_study_plan_details(plan_slug)
            if not details:
                continue

            # Save details
            output_path = f"{self.output_dir}/study_plan_details_{plan_slug}.json"
            with open(output_path, "w", encoding="utf-8") as f:
                json.dump(details, f, indent=2, ensure_ascii=False)

            # Process questions
            for group in details["data"]["studyPlanV2Detail"]["planSubGroups"]:
                for question in group["questions"]:
                    title_slug = question["titleSlug"]
                    status = question["status"]

                    if status == "SOLVED":
                        logging.info(f"Question {title_slug} already solved")
                        continue

                    logging.info(f"Marking {title_slug} as solved")
                    result = self.mark_solved_question(title_slug, plan_slug)
                    if result:
                        logging.info(json.dumps(result, indent=2))
                    time.sleep(0.5)  # Reduced sleep time


if __name__ == "__main__":
    client = LeetCodeClient()
    config = StudyPlanConfig()
    client.process_study_plans(config)
