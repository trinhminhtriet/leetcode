import logging
import argparse
import os
from solver.problem_solver import LeetCodeProblemSolver
from repository.question import LeetCodeQuestionRepository

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)

def main():
    controller = LeetCodeQuestionRepository()
    questions = controller.get_unsolved_questions(
        submitted_by=os.environ.get("STRAPI_USERNAME"), limit=3500
    )

    for question in questions:
        logging.info(f"Unsolved question: {question.frontend_question_id}")
        # solver = LeetCodeProblemSolver()
        # success = solver.solve_by_frontend_question_id(question.frontend_question_id)
        # if success:
        #     logging.info(f"Problem {question.frontend_question_id} solved successfully")
        # else:
        #     logging.error(f"Failed to solve problem {question.frontend_question_id}")

    # Uncomment the above lines to actually solve the problems
    logging.info(f"Unsolved solutions count: {len(questions)}")

if __name__ == "__main__":
    main()
