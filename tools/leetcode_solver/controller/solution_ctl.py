import logging
from typing import List
from models.models import LeetcodeQuestion
from repository.question import LeetCodeQuestionRepository
from solver.problem_solver import LeetCodeProblemSolver
from services.api.daily_question import DailyQuestionAPIService

class LeetCodeSolutionController:
    def __init__(self):
        self.question_repo = LeetCodeQuestionRepository()
        self.solution_solver = LeetCodeProblemSolver()

    def solve_by_frontend_question_id(self, question_frontend_id: int):
        success = self.solution_solver.solve_by_frontend_question_id(
            question_frontend_id
        )
        if success:
            logging.info(f"Problem {question_frontend_id} solved successfully")
        else:
            logging.error(f"Failed to solve problem {question_frontend_id}")

    def solve_questions(self, questions: List[LeetcodeQuestion]):
        if not questions:
            logging.warning("No questions")
            return False
        for question in questions:
            logging.info(f"Frontend question id: {question.frontend_question_id}")
            self.solution_solver.solve_by_frontend_question_id(
                question.frontend_question_id
            )

    def solve_by_submmitted_language(self, submitted_by: str, lang: str):
        questions = self.question_repo.find_by_submmitted_language(
            submitted_by=submitted_by, lang=lang, limit=10
        )

        if not questions:
            logging.error("No questions found with the specified language.")
            return

        self.solve_questions(questions=questions)

    def solve_daily_question(self):
        svc = DailyQuestionAPIService()
        fronted_question_id = svc.get_daily_question()
        if fronted_question_id:
            self.solution_solver.solve_by_frontend_question_id(fronted_question_id)
