import json
import logging
from typing import List, Dict
from services.api.base import LeetCodeAPIBaseService


class AllQuestionsAPIService(LeetCodeAPIBaseService):
    """Service to fetch all LeetCode questions."""

    def fetch_all_questions(self) -> List[Dict]:
        """Fetch all questions from LeetCode API."""
        url = f"{self.config.BASE_URL}/api/problems/all/"
        response = self.session.get(url)

        if response.status_code != 200:
            logging.error(f"Failed to fetch all questions: HTTP {response.status_code}")
            return []

        data = json.loads(response.text)
        logging.info("Successfully fetched all questions from API")
        return data.get("stat_status_pairs", [])
