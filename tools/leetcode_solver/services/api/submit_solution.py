import json
import logging
from typing import Optional
from services.api.base import LeetCodeAPIBaseService


class SubmitSolutionAPIService(LeetCodeAPIBaseService):
    """Service to submit a solution to LeetCode."""

    def submit_solution(self, question, lang: str, solution: str) -> Optional[str]:
        """Submit a solution and return submission ID if successful."""
        frontend_id = question.frontend_question_id
        slug = question.slug
        url = f"{self.config.BASE_URL}/problems/{slug}/submit/"

        self.session.headers["Referer"] = f"{self.config.BASE_URL}/problems/{slug}/"
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
