import logging
import os
from controller.solution_ctl import LeetCodeSolutionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)


def find_by_submmitted_language():
    ctl = LeetCodeSolutionController()
    ctl.find_by_submmitted_language("trinhminhtriet", "java")


def solve_daily_question():
    ctl = LeetCodeSolutionController()
    ctl.solve_daily_question()

def public_solution():
    frontend_question_id = 2
    ctl = LeetCodeSolutionController()
    ctl.publish_solution(frontend_question_id)

if __name__ == "__main__":
    public_solution()
