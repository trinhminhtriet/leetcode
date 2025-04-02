import logging
import os
from controller.question_ctl import LeetCodeQuestionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)

LEETCODE_USER = os.environ.get("STRAPI_USERNAME")


def fetch_all_questions():
    ctl = LeetCodeQuestionController()
    # ctl.fetch_all_questions()
    ctl.mkdir()


if __name__ == "__main__":
    fetch_all_questions()
