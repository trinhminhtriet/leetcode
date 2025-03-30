import json
import logging
from typing import List
from models.models import LeetcodeQuestion
from repository.question import LeetCodeQuestionRepository
from repository.readme import LeetcodeSolutionReadmeRepository
from solver.problem_solver import LeetCodeProblemSolver
from services.api.daily_question import DailyQuestionAPIService
from services.api.publish_solution import PublishSolutionAPIService

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
        frontend_question_id = svc.get_daily_question()
        if frontend_question_id:
            self.solution_solver.solve_by_frontend_question_id(frontend_question_id)

    def publish_solution(self, frontend_question_id: int):
        question = self.question_repo.get_by_frontend_question_id(frontend_question_id)

        repo = LeetcodeSolutionReadmeRepository()
        repo.set_question(question=question)

        solution = repo.get_solution()
        logging.info(json.dumps(solution, indent=2))

        svc = PublishSolutionAPIService()
        svc.publish_solution(
            title=solution["title"],
            content=solution["content"],
            tags=solution["tags"],
            question_slug=question.slug,
            summary=solution["summary"]
        )

        logging.info(question.slug)

    def get_unsolved_questions(self, submitted_by: str, limit: int = 10):
        questions = self.question_repo.get_unsolved_questions(
            submitted_by=submitted_by,
            limit=limit
        )
        for question in questions:
            logging.info(question.frontend_question_id)
