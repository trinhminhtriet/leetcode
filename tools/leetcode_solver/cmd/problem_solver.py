import os
import logging
from typing import Dict, Optional
from config.config import LeetCodeConfig
from services.database import DatabaseService
from services.leetcode_api import LeetCodeAPIService
from models.models import LeetcodeQuestion
from utils.utils import get_folder_path  # Assuming this exists


class LeetCodeProblemSolver:
    """Class to solve LeetCode problems by frontend_question_id."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.db_service = DatabaseService()
        self.api_service = LeetCodeAPIService()

    def get_question_by_frontend_id(self, frontend_question_id: int) -> Optional[LeetcodeQuestion]:
        """Retrieve question entity from database by frontend_question_id."""
        session = self.db_service.get_session()
        question = session.query(LeetcodeQuestion).filter(
            LeetcodeQuestion.frontend_question_id == frontend_question_id
        ).first()
        if question:
            logging.info(
                f"[{frontend_question_id}] Found question: {question.slug}")
            return question
        logging.error(
            f"[{frontend_question_id}] Question not found in database")
        return None

    def find_solutions(self, question: LeetcodeQuestion) -> Dict[str, str]:
        """Find solutions for a question in various languages from local filesystem."""
        solutions = {}
        frontend_id = question.frontend_question_id
        slug = question.slug
        folder_path = get_folder_path(frontend_id, slug)

        if not os.path.exists(folder_path):
            logging.warning(
                f"[{frontend_id}] Solution folder not found: {folder_path}")
            return solutions

        for lang, ext in self.config.EXTENSIONS.items():
            solution_file = os.path.join(folder_path, f"Solution.{ext}")
            if os.path.exists(solution_file):
                with open(solution_file, 'r', encoding='utf-8') as file:
                    solutions[lang] = file.read()
                self.db_service.upsert_solution(
                    question, lang, solutions[lang])
                logging.info(f"[{frontend_id}] Found solution in {lang}")
        return solutions

    def solve_by_frontend_question_id(self, frontend_question_id: int) -> bool:
        """Solve a problem by frontend_question_id."""
        logging.info(
            f"Attempting to solve problem with frontend_question_id: {frontend_question_id}")

        question = self.get_question_by_frontend_id(frontend_question_id)
        if not question:
            return False

        solutions = self.find_solutions(question)
        if not solutions:
            logging.error(f"[{frontend_question_id}] No solutions found")
            return False

        # Try solutions in preferred language first, then others
        langs_to_try = [self.config.MAIN_LANGUAGE] + \
            [lang for lang in solutions.keys() if lang != self.config.MAIN_LANGUAGE]

        for lang in langs_to_try:
            if lang not in solutions:
                continue

            logging.info(
                f"[{frontend_question_id}] Attempting solution in {lang}")
            solution = solutions[lang]
            submission_id = self.api_service.submit_solution(
                question, lang, solution)

            if submission_id and self.api_service.check_submission(submission_id, frontend_question_id):
                self.db_service.save_submission(question, lang, solution)
                logging.info(
                    f"[{frontend_question_id}] Successfully solved in {lang}")
                return True
            else:
                logging.warning(
                    f"[{frontend_question_id}] Solution in {lang} failed")
                # Remove failed solution from database
                session = self.db_service.get_session()
                failed_solution = session.query(LeetcodeSolution).filter(
                    LeetcodeSolution.question_id == question.question_id,
                    LeetcodeSolution.lang == lang
                ).first()
                if failed_solution:
                    session.delete(failed_solution)
                    session.commit()
                    logging.info(
                        f"[{frontend_question_id}] Removed failed solution in {lang}")

        logging.error(
            f"[{frontend_question_id}] Failed to solve with any available solution")
        return False
