import json
import logging
from typing import Optional, Dict
from services.api.base import LeetCodeAPIBaseService


class QuestionContentAPIService(LeetCodeAPIBaseService):
    """Service to fetch LeetCode question content."""

    def fetch_question_content(
        self, question_id: int, frontend_id: int, slug: str
    ) -> Optional[Dict]:
        """Fetch question content via GraphQL and return full response data."""
        url = f"{self.config.BASE_URL}/graphql"
        query = """
        query questionContent($titleSlug: String!) {
            question(titleSlug: $titleSlug) {
                content
                mysqlSchemas
                dataSchemas
            }
        }
        """
        variables = {"titleSlug": slug}
        payload = {
            "query": query,
            "variables": variables,
            "operationName": "questionContent",
        }

        response = self.session.post(url, json=payload)

        if response.status_code != 200:
            logging.error(
                f"[{frontend_id}] HTTP request failed with status {response.status_code}"
            )
            return None

        try:
            data = json.loads(response.text)
            if data["data"]["question"]["content"] is None:
                logging.error(f"[{frontend_id}] No content found for question")
                return None
            return data
        except (json.JSONDecodeError, KeyError) as e:
            logging.error(f"[{frontend_id}] Error processing response: {e}")
            return None
