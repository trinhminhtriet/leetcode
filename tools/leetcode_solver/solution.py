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

if __name__ == "__main__":
    solve_daily_question()
