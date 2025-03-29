import json
import time
import logging
from services.api.base import LeetCodeAPIBaseService


class CheckSubmissionAPIService(LeetCodeAPIBaseService):
    """Service to check submission status on LeetCode."""

    def check_submission(self, submission_id: str, frontend_id: int) -> bool:
        """Check submission status and return True if accepted."""
        check_url = f"{self.config.BASE_URL}/submissions/detail/{submission_id}/check/"

        time.sleep(10)  # Wait for LeetCode to process
        response = self.session.get(check_url)

        if response.status_code != 200:
            logging.error(
                f"[{frontend_id}] Check failed with status: {response.status_code}"
            )
            return False

        try:
            result = response.json()
            status = result.get("status_msg")
            logging.info(f"[{frontend_id}] Submission status: {status}")
            return status == "Accepted"
        except json.JSONDecodeError:
            logging.error(f"[{frontend_id}] Failed to parse check response")
            return False
