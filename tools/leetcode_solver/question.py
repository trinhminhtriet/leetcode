import logging
import os
from controller.question_ctl import LeetCodeQuestionController

logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)

LEETCODE_USER = os.environ.get("STRAPI_USERNAME")


def sync_all_questions():
    ctl = LeetCodeQuestionController()
    ctl.sync_all_questions()


def sync_by_frontend_question_id(frontend_question_id: int):
    ctl = LeetCodeQuestionController()
    ctl.sync_by_frontend_question_id(frontend_question_id)


if __name__ == "__main__":
    sync_all_questions()
    # sync_by_frontend_question_id(437)
