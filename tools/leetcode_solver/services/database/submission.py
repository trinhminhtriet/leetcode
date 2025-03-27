from sqlalchemy import func
from models.models import LeetcodeSubmission
from services.database.base import DatabaseBaseService
from config.config import LeetCodeConfig
import logging


class SubmissionDatabaseService(DatabaseBaseService):
    """Database operations for LeetcodeSubmission model."""

    def save_submission(self, question, lang: str, solution: str) -> None:
        """Save a submission into the database."""
        session = self.get_session()

        submission = LeetcodeSubmission(
            user_id=LeetCodeConfig.STRAPI_USERID,
            question_id=question.question_id,
            frontend_question_id=question.frontend_question_id,
            slug=question.slug,
            lang=lang,
            solution=solution,
            submitted_by=LeetCodeConfig.STRAPI_USERNAME,
            submitted_at=func.now(),
            created_at=func.now(),
            updated_at=func.now(),
        )
        session.add(submission)
        session.commit()
        logging.info(f"[{question.frontend_question_id}] Submission saved")
