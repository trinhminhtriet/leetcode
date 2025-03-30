from utils import get_folder_path
import logging
import os
import requests
import json
from dotenv import load_dotenv


class LeetCodeStudyPlanManager:
    def __init__(self):
        # Load environment variables
        load_dotenv()

        # Configure logging
        logging.basicConfig(
            level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
        )

        # Initialize instance variables from environment
        self.cookie = os.environ.get("COOKIE")
        self.csrf_token = os.environ.get("CSRF_TOKEN")
        self.uuuserid = os.environ.get("UUUSERID")
        self.random_uuid = os.environ.get("RANDOM_UUID")

        # Base URL for API
        self.api_url = "https://leetcode.com/graphql/"

        # Headers template
        self.headers = {
            "Origin": "https://leetcode.com",
            "Content-Type": "application/json",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36",
            "X-CSRFToken": self.csrf_token,
            "Cookie": self.cookie,
            "uuuserid": self.uuuserid,
            "random-uuid": self.random_uuid,
        }

        # GraphQL query template
        self.query = """
        query studyPlanDetail($slug: String!) {
            studyPlanV2Detail(planSlug: $slug) {
                slug
                name
                highlight
                staticCoverPicture
                colorPalette
                threeDimensionUrl
                description
                premiumOnly
                needShowTags
                awardDescription
                defaultLanguage
                award {
                    name
                    config {
                        icon
                        iconGif
                        iconGifBackground
                    }
                }
                relatedStudyPlans {
                    cover
                    highlight
                    name
                    slug
                    premiumOnly
                }
                planSubGroups {
                    slug
                    name
                    premiumOnly
                    questionNum
                    questions {
                        translatedTitle
                        titleSlug
                        title
                        questionFrontendId
                        paidOnly
                        id
                        difficulty
                        hasOfficialSolution
                        topicTags {
                            slug
                            name
                        }
                        solutionInfo {
                            solutionSlug
                            solutionTopicId
                        }
                    }
                }
            }
        }
        """

    def _create_folder(self, folder_path):
        """Helper method to create folder if it doesn't exist"""
        if not os.path.exists(folder_path):
            os.makedirs(folder_path)

    def generate_readme(self, studyplan_slug):
        """Generate README file for a study plan"""
        folder_path = f"../studyplan/{studyplan_slug}"
        self._create_folder(folder_path)

        # Read study plan details
        with open(f"{folder_path}/study_plan_details.json", "r") as f:
            data = json.load(f)

        # Build README content
        header = f"# {data['data']['studyPlanV2Detail']['name']}\n"
        plan_subgroups = data["data"]["studyPlanV2Detail"]["planSubGroups"]

        question_counter = 0
        sections_content = ""
        for subgroup in plan_subgroups:
            sections_content += f"\n## {subgroup.get('name')}\n\n"
            questions = subgroup.get("questions")

            for question in questions:
                folder_path = get_folder_path(
                    question.get("questionFrontendId"), question.get("titleSlug")
                )
                question_counter += 1
                sections_content += f"{question_counter}. [{question.get('title')}]({folder_path}/README.md)\n"

        # Write to README file
        with open(f"../studyplan/{studyplan_slug}/README.md", "w") as f:
            f.write(header + sections_content)

        logging.info(f"Generated README for {studyplan_slug}")

    def get_study_plan(self, slug):
        """Fetch study plan data from LeetCode API"""
        folder_path = f"../studyplan/{slug}"
        self._create_folder(folder_path)

        # Update headers with specific referer
        self.headers["Referer"] = f"https://leetcode.com/studyplan/{slug}/"

        # Prepare API payload
        variables = {"slug": slug}
        payload = {
            "query": self.query,
            "variables": variables,
            "operationName": "studyPlanDetail",
        }

        try:
            response = requests.post(self.api_url, headers=self.headers, json=payload)
            response.raise_for_status()

            # Save response to JSON file
            output_path = f"{folder_path}/study_plan_details.json"
            with open(output_path, "w") as f:
                json.dump(response.json(), f, indent=2)

            logging.info(f"Successfully fetched study plan: {slug}")
            return response.json()

        except requests.exceptions.RequestException as e:
            logging.error(f"Error fetching study plan {slug}: {e}")
            return None

    def process_study_plans(self, studyplan_slugs):
        """Process multiple study plans"""
        for slug in studyplan_slugs:
            self.get_study_plan(slug)
            self.generate_readme(slug)


if __name__ == "__main__":
    manager = LeetCodeStudyPlanManager()
    studyplan_list = [
        "google-spring-23-high-frequency",
    ]
    manager.process_study_plans(studyplan_list)
