from sqlalchemy import func
from models.models import LeetcodeSolution
from services.database.base import DatabaseBaseService
import logging


class SolutionDatabaseService(DatabaseBaseService):
    """Database operations for LeetcodeSolution model."""

    def upsert_solution(self, question, lang: str, solution: str) -> None:
        """Upsert a solution into the database."""
        session = self.get_session()

        solution_entity = (
            session.query(LeetcodeSolution)
            .filter(
                LeetcodeSolution.question_id == question.question_id,
                LeetcodeSolution.lang == lang,
            )
            .first()
        )

        if solution_entity:
            solution_entity.solution = solution
            solution_entity.updated_at = func.now()
            logging.info(
                f"[{question.frontend_question_id}] Updated solution in {lang}"
            )
        else:
            solution_entity = LeetcodeSolution(
                question_id=question.question_id,
                frontend_question_id=question.frontend_question_id,
                slug=question.slug,
                lang=lang,
                solution=solution,
                created_at=func.now(),
                updated_at=func.now(),
                published_at=func.now(),
            )
            session.add(solution_entity)
            logging.info(
                f"[{question.frontend_question_id}] Stored new solution in {lang}"
            )

        session.commit()
