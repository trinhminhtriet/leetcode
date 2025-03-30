from typing import Dict, Optional
from config.config import LeetCodeConfig
from services.database.question import QuestionDatabaseService
from services.database.solution import SolutionDatabaseService
from services.database.submission import SubmissionDatabaseService
from models.models import LeetcodeQuestion, LeetcodeSolution, LeetcodeSubmission

class LeetCodeQuestionController:
    """Class to solve LeetCode problems by frontend_question_id."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.question_db = QuestionDatabaseService()
        self.solution_db = SolutionDatabaseService()
        self.submission_db = SubmissionDatabaseService()

    def get_unsolved_questions(self, submitted_by: str, limit: int) -> Dict[str, str]:
        """Retrieve unsubmitted solutions from the database."""
        session = self.submission_db.get_session()
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
