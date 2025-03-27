from sqlalchemy import func
from models.models import LeetcodeQuestion
from services.database.base import DatabaseBaseService
import logging


class QuestionDatabaseService(DatabaseBaseService):
    """Database operations for LeetcodeQuestion model."""

    def upsert_question(self, question_data: dict, content: str) -> None:
        """Upsert a question into the database."""
        session = self.get_session()
        question_id = question_data["stat"]["question_id"]

        question_entity = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.question_id == question_id)
            .first()
        )

        if question_entity:
            question_entity.frontend_question_id = question_data["stat"][
                "frontend_question_id"
            ]
            question_entity.title = question_data["stat"]["question__title"]
            question_entity.slug = question_data["stat"]["question__title_slug"]
            question_entity.content = content
            question_entity.difficulty_level = question_data["difficulty"]["level"]
            question_entity.updated_at = func.now()
            logging.info(f"[{question_entity.frontend_question_id}] Updated question")
        else:
            question_entity = LeetcodeQuestion(
                question_id=question_id,
                frontend_question_id=question_data["stat"]["frontend_question_id"],
                title=question_data["stat"]["question__title"],
                slug=question_data["stat"]["question__title_slug"],
                content=content,
                difficulty_level=question_data["difficulty"]["level"],
                created_at=func.now(),
                updated_at=func.now(),
                published_at=func.now(),
            )
            session.add(question_entity)
            logging.info(
                f"[{question_entity.frontend_question_id}] Stored new question"
            )

        session.commit()
