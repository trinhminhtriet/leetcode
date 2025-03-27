import requests
import time
import json
import logging
from config.config import LeetCodeConfig
from typing import Optional


class LeetCodeApiSolution:
    """Handles interactions with LeetCode API."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.session = requests.Session()
        self.session.headers.update(self.config.DEFAULT_HEADERS)

    def submit_solution(self, question, lang: str, solution: str) -> Optional[str]:
        """Submit a solution to LeetCode and return submission ID if successful."""
        frontend_id = question.frontend_question_id
        slug = question.slug
        url = f"{self.config.BASE_URL}/problems/{slug}/submit/"

        self.session.headers["Referer"] = f"{self.config.BASE_URL}/problems/{slug}/"
        self.session.headers.update(self.session.headers)

        payload = {
            "question_id": question.question_id,
            "lang": lang,
            "typed_code": solution,
        }

        response = self.session.post(url, json=payload)

        if response.status_code == 429:
            logging.error(f"[{frontend_id}] Rate limit exceeded")
            return None
        elif response.status_code != 200:
            logging.error(
                f"[{frontend_id}] Submission failed with status: {response.status_code}"
            )
            return None

        try:
            submission_id = response.json().get("submission_id")
            if submission_id:
                logging.info(
                    f"[{frontend_id}] Submitted successfully, ID: {submission_id}"
                )
                return submission_id
            logging.error(f"[{frontend_id}] No submission ID in response")
            return None
        except json.JSONDecodeError:
            logging.error(f"[{frontend_id}] Failed to parse submission response")
            return None

    def check_submission(self, submission_id: str, frontend_id: int) -> bool:
        """Check submission status and return True if accepted."""
        check_url = f"{self.config.BASE_URL}/submissions/detail/{submission_id}/check/"

        time.sleep(10)  # Wait for LeetCode to process
        response = self.session.get(check_url)

        if response.status_code == 200:
            try:
                result = response.json()
                status = result.get("status_msg")
                logging.info(f"[{frontend_id}] Submission status: {status}")
                return status == "Accepted"
            except json.JSONDecodeError:
                logging.error(f"[{frontend_id}] Failed to parse check response")
                return False
        logging.error(
            f"[{frontend_id}] Check failed with status: {response.status_code}"
        )
        return False
