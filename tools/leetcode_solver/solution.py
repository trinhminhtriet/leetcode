import logging
import os
from controller.solution_ctl import LeetCodeSolutionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)

LEETCODE_USER = os.environ.get("STRAPI_USERNAME")


def solve_by_frontend_question_id():
    ctl = LeetCodeSolutionController()
    ctl.solve_by_frontend_question_id(question_frontend_id=3515)


def solve_by_submmitted_language():
    ctl = LeetCodeSolutionController()
    ctl.solve_by_submmitted_language(submitted_by=LEETCODE_USER, lang="java", limit=9)


def solve_daily_question():
    ctl = LeetCodeSolutionController()
    ctl.solve_daily_question()


def publish_submmitted_language():
    ctl = LeetCodeSolutionController()
    ctl.publish_submmitted_language(submitted_by=LEETCODE_USER, lang="java", limit=7)


def publish_solution():
    frontend_question_id = 3509
    ctl = LeetCodeSolutionController()
    ctl.publish_solution(frontend_question_id=frontend_question_id)


def get_unsolved_questions():
    ctl = LeetCodeSolutionController()
    ctl.get_unsolved_questions(submitted_by=LEETCODE_USER, limit=100)


if __name__ == "__main__":
    # solve_by_frontend_question_id()
    # solve_by_submmitted_language()
    # publish_submmitted_language()
    solve_daily_question()
