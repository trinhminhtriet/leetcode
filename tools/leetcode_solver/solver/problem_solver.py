import logging
from typing import Dict, Optional
from config.config import LeetCodeConfig
from services.database.question import QuestionDatabaseService
from services.database.solution import SolutionDatabaseService
from services.database.submission import SubmissionDatabaseService
from services.api.submit_solution import SubmitSolutionAPIService
from services.api.check_submission import CheckSubmissionAPIService
from models.models import LeetcodeQuestion, LeetcodeSolution
import os


class LeetCodeProblemSolver:
    """Class to solve LeetCode problems by frontend_question_id."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.question_db = QuestionDatabaseService()
        self.solution_db = SolutionDatabaseService()
        self.submission_db = SubmissionDatabaseService()
        self.submit_service = SubmitSolutionAPIService()
        self.check_service = CheckSubmissionAPIService()

    def get_question_by_frontend_id(
        self, frontend_question_id: int
    ) -> Optional[LeetcodeQuestion]:
        """Retrieve question entity from database by frontend_question_id."""
        session = self.question_db.get_session()
        question = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.frontend_question_id == frontend_question_id)
            .first()
        )
        if question:
            logging.info(f"[{frontend_question_id}] Found question: {question.slug}")
            return question
        logging.error(f"[{frontend_question_id}] Question not found in database")
        return None

    def find_solutions(self, question: LeetcodeQuestion) -> Dict[str, str]:
        """Find solutions for a question in various languages from local filesystem."""
        solutions = {}
        frontend_id = question.frontend_question_id
        slug = question.slug
        folder_path = self._get_folder_path(frontend_id, slug)

        if not os.path.exists(folder_path):
            logging.warning(f"[{frontend_id}] Solution folder not found: {folder_path}")
            return solutions

        for lang, ext in self.config.EXTENSIONS.items():
            solution_file = os.path.join(folder_path, f"Solution.{ext}")
            if os.path.exists(solution_file):
                with open(solution_file, "r", encoding="utf-8") as file:
                    solutions[lang] = file.read()
                self.solution_db.upsert_solution(question, lang, solutions[lang])
                logging.info(f"[{frontend_id}] Found solution in {lang}")
        return solutions

    def _get_folder_path(self, frontend_id: int, slug: str) -> str:
        """Generate folder path for solutions (simplified assumption)."""
        start = (frontend_id // 100) * 100
        end = start + 99
        return f"../../solutions/{start:04d}-{end:04d}/{frontend_id:04d}.{slug}"

    def solve_by_frontend_question_id(self, frontend_question_id: int) -> bool:
        """Solve a problem by frontend_question_id."""
        logging.info(
            f"Attempting to solve problem with frontend_question_id: {frontend_question_id}"
        )

        question = self.get_question_by_frontend_id(frontend_question_id)
        if not question:
            return False

        solutions = self.find_solutions(question)
        if not solutions:
            logging.error(f"[{frontend_question_id}] No solutions found")
            return False

        langs_to_try = [self.config.MAIN_LANGUAGE] + [
            lang for lang in solutions.keys() if lang != self.config.MAIN_LANGUAGE
        ]

        for lang in langs_to_try:
            if lang not in solutions:
                continue

            logging.info(f"[{frontend_question_id}] Attempting solution in {lang}")
            solution = solutions[lang]
            submission_id = self.submit_service.submit_solution(
                question, lang, solution
            )

            if submission_id and self.check_service.check_submission(
                submission_id, frontend_question_id
            ):
                self.submission_db.save_submission(question, lang, solution)
                logging.info(f"[{frontend_question_id}] Successfully solved in {lang}")
                return True
            else:
                logging.warning(f"[{frontend_question_id}] Solution in {lang} failed")
                session = self.solution_db.get_session()
                failed_solution = (
                    session.query(LeetcodeSolution)
                    .filter(
                        LeetcodeSolution.question_id == question.question_id,
                        LeetcodeSolution.lang == lang,
                    )
                    .first()
                )
                if failed_solution:
                    session.delete(failed_solution)
                    session.commit()
                    logging.info(
                        f"[{frontend_question_id}] Removed failed solution in {lang}"
                    )

        logging.error(
            f"[{frontend_question_id}] Failed to solve with any available solution"
        )
        return False

    def solve_by_frontend_question_ids(self, frontend_question_ids: list[int]) -> bool:
        """Solve problems by a list of frontend_question_ids."""
        logging.info(
            f"Attempting to solve problems with frontend_question_ids: {frontend_question_ids}"
        )

        for frontend_question_id in frontend_question_ids:
            if not self.solve_by_frontend_question_id(frontend_question_id):
                logging.error(
                    f"Failed to solve problem with frontend_question_id: {frontend_question_id}"
                )
        logging.info(
            f"Finished attempting to solve problems with frontend_question_ids: {frontend_question_ids}"
        )
        return True

    def solve_by_difficulty(self, difficulty: str):
        """Solve problems by difficulty level."""
        logging.info(f"Attempting to solve problems with difficulty: {difficulty}")

        session = self.question_db.get_session()
        questions = (
            session.query(LeetcodeQuestion)
            .filter(LeetcodeQuestion.difficulty_level == difficulty)
            .all()
        )

        if not questions:
            logging.error(f"No questions found with difficulty: {difficulty}")
            return False

        for question in questions:
            if not self.solve_by_frontend_question_id(question.frontend_question_id):
                logging.error(
                    f"Failed to solve problem with frontend_question_id: {question.frontend_question_id}"
                )
        logging.info(
            f"Finished attempting to solve problems with difficulty: {difficulty}"
        )
        return True
