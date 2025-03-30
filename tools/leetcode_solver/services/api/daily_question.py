import json
import logging
from typing import Optional
from services.api.base import LeetCodeAPIBaseService


class DailyQuestionAPIService(LeetCodeAPIBaseService):
    """Service to fetch the daily LeetCode question."""

    def get_daily_question(self) -> Optional[int]:
        """Fetch the daily coding challenge question's frontend ID."""
        url = f"{self.config.BASE_URL}/graphql"
        headers = self.config.DEFAULT_HEADERS.copy()
        headers["Referer"] = f"{self.config.BASE_URL}/problemset/all/"

        payload = {
            "query": """
            query questionOfToday {
                activeDailyCodingChallengeQuestion {
                    date
                    userStatus
                    link
                    question {
                        acRate
                        difficulty
                        freqBar
                        frontendQuestionId: questionFrontendId
                        isFavor
                        paidOnly: isPaidOnly
                        status
                        title
                        titleSlug
                        hasVideoSolution
                        hasSolution
                        topicTags {
                            name
                            id
                            slug
                        }
                    }
                }
            }
            """,
            "variables": {},
            "operationName": "questionOfToday",
        }

        response = self.session.post(url, headers=headers, json=payload)

        if response.status_code != 200:
            logging.error(
                f"Failed to fetch daily question: HTTP {response.status_code}"
            )
            return None

        try:
            data = json.loads(response.text)
            daily_data = data["data"]["activeDailyCodingChallengeQuestion"]
            if daily_data["userStatus"] == "NotStart":
                frontend_id = daily_data["question"]["frontendQuestionId"]
                logging.info(f"Daily question frontend ID: {frontend_id}")
                return frontend_id
            else:
                frontend_id = daily_data["question"]["frontendQuestionId"]
                logging.warning(
                    f"Daily question [{frontend_id}] already started or completed"
                )
                return None
        except (json.JSONDecodeError, KeyError) as e:
            logging.error(f"Error processing daily question response: {e}")
            return None
