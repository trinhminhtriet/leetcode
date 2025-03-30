from typing import Dict, Optional
from config.config import LeetCodeConfig
from services.database.question import QuestionDatabaseService
from models.models import LeetcodeQuestion, LeetcodeSubmission

class LeetCodeQuestionRepository:
    """Class to solve LeetCode problems by frontend_question_id."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.question_db = QuestionDatabaseService()

    def get_unsolved_questions(self, submitted_by: str, limit: int) -> Dict[str, str]:
        """Retrieve unsubmitted solutions from the database."""
        session = self.question_db.get_session()
        sub_query = (
            session.query(LeetcodeSubmission.question_id)
            .filter(LeetcodeSubmission.submitted_by == submitted_by)
            .subquery()
        )
        questions = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.question_id.not_in(sub_query))
            .order_by(LeetcodeQuestion.frontend_question_id.desc())
            .limit(limit)
            .all()
        )
        return questions
