import logging
import os
from controller.solution_ctl import LeetCodeSolutionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)

LEETCODE_USER = os.environ.get("STRAPI_USERNAME")


def solve_by_submmitted_language():
    ctl = LeetCodeSolutionController()
    ctl.solve_by_submmitted_language(submitted_by=LEETCODE_USER, lang="java", limit=450)


def solve_daily_question():
    ctl = LeetCodeSolutionController()
    ctl.solve_daily_question()


def public_solution():
    frontend_question_id = 2
    ctl = LeetCodeSolutionController()
    ctl.publish_solution(frontend_question_id)


def get_unsolved_questions():
    ctl = LeetCodeSolutionController()
    ctl.get_unsolved_questions(submitted_by=LEETCODE_USER, limit=100)


if __name__ == "__main__":
    get_unsolved_questions()
