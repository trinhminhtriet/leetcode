import logging
from typing import Dict, Optional
from config.config import LeetCodeConfig
from services.database.question import QuestionDatabaseService
from models.models import LeetcodeQuestion, LeetcodeSolution, LeetcodeSubmission


class LeetCodeQuestionRepository:
    """Class to solve LeetCode problems by frontend_question_id."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.question_db = QuestionDatabaseService()

    def get_by_frontend_question_id(self, frontend_question_id: int) -> Optional[LeetcodeQuestion]:
        session = self.question_db.get_session()
        question = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.frontend_question_id == frontend_question_id)
            .first()
        )
        if not question:
            logging.warning(f"Question [{frontend_question_id}] not found")
            return False

        return question

    def get_unsolved_questions(self, submitted_by: str, limit: int) -> Dict[str, str]:
        """Retrieve unsubmitted solutions from the database."""
        session = self.question_db.get_session()
        sub_query_submitted_by = (
            session.query(LeetcodeSubmission.question_id)
            .filter(LeetcodeSubmission.submitted_by == submitted_by)
            .subquery()
        )

        questions = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.question_id.not_in(sub_query_submitted_by))
            .order_by(LeetcodeQuestion.frontend_question_id.desc())
            .limit(limit)
            .all()
        )
        return questions

    def get_by_difficulty(
        self, difficulty: str, limit: int
    ) -> Optional[LeetcodeQuestion]:
        """Get problems by difficulty level from the database."""

        session = self.question_db.get_session()
        questions = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.difficulty_level == difficulty)
            .order_by(LeetcodeQuestion.frontend_question_id.desc())
            .limit(limit)
            .all()
        )

        if not questions:
            logging.warning(f"No questions found with difficulty: {difficulty}")
            return False

        return questions

    def find_by_submmitted_language(
        self, submitted_by: str, lang: str, limit: int
    ) -> Optional[LeetcodeQuestion]:
        """Retrieve questions by submitted language."""
        session = self.question_db.get_session()
        sub_query_submitted_by = (
            session.query(LeetcodeSubmission.question_id)
            .filter(
                LeetcodeSubmission.submitted_by == submitted_by,
            )
            .subquery()
        )
        sub_query_lang = (
            session.query(LeetcodeSolution.question_id)
            .filter(LeetcodeSolution.lang == lang)
            .subquery()
        )
        questions = (
            session.query(LeetcodeQuestion)
            .filter(
                LeetcodeQuestion.question_id.in_(sub_query_submitted_by),
                LeetcodeQuestion.question_id.in_(sub_query_lang),
                LeetcodeQuestion.difficulty_level.in_([1]),
            )
            .order_by(LeetcodeQuestion.frontend_question_id.desc())
            .limit(limit)
            .all()
        )
        if not questions:
            logging.warning(f"No questions found with submitted language: {lang}")
            return False
        logging.info(
            f"Found {len(questions)} questions with submitted language: {lang}"
        )

        return questions
