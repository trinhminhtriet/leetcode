import logging
import os
from controller.solution_ctl import LeetCodeSolutionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)


def main():
    ctl = LeetCodeSolutionController()
    ctl.find_by_submmitted_language("trinhminhtriet", "java")


if __name__ == "__main__":
    main()
