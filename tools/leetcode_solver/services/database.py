from sqlalchemy import create_engine, func
from sqlalchemy.orm import sessionmaker
from config.config import LeetCodeConfig
from models.models import Base
import logging


class DatabaseService:
    """Manages database connection and session."""

    def __init__(self):
        self.engine = create_engine(LeetCodeConfig.DATABASE_URL)
        Base.metadata.create_all(self.engine)
        self.Session = sessionmaker(bind=self.engine)
        self.session = self.Session()

    def get_session(self):
        """Return the current session."""
        return self.session

    def upsert_solution(self, question, lang: str, solution: str) -> None:
        """Upsert solution into database."""
        from models.models import LeetcodeSolution

        solution_entity = self.session.query(LeetcodeSolution).filter(
            LeetcodeSolution.question_id == question.question_id,
            LeetcodeSolution.lang == lang
        ).first()

        if solution_entity:
            solution_entity.solution = solution
            solution_entity.updated_at = func.now()
            logging.info(
                f"[{question.frontend_question_id}] Updated solution in {lang}")
        else:
            solution_entity = LeetcodeSolution(
                question_id=question.question_id,
                frontend_question_id=question.frontend_question_id,
                slug=question.slug,
                lang=lang,
                solution=solution,
                created_at=func.now(),
                updated_at=func.now(),
                published_at=func.now()
            )
            self.session.add(solution_entity)
            logging.info(
                f"[{question.frontend_question_id}] Stored new solution in {lang}")

        self.session.commit()

    def save_submission(self, question, lang: str, solution: str) -> None:
        """Save successful submission to database."""
        from models.models import LeetcodeSubmission
        from config.config import LeetCodeConfig

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
            updated_at=func.now()
        )
        self.session.add(submission)
        self.session.commit()
        logging.info(f"[{question.frontend_question_id}] Submission saved")
