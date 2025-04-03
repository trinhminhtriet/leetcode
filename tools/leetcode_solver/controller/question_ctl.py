import json
import logging
import os
import shutil
from typing import List
from services.api.all_questions import AllQuestionsAPIService
from utils.utils import get_folder_path


class LeetCodeQuestionController:
    """Controller to manage LeetCode questions."""

    def __init__(self):
        self.all_questions_service = AllQuestionsAPIService()

    def fetch_all_questions(self):
        questions = self.all_questions_service.fetch_all_questions()
        logging.info(f"Total questions fetched: {len(questions)}")
        if not questions:
            logging.error("No questions fetched")
            return []
        self.save_questions(questions)
        return questions

    def save_questions(self, questions: List[dict]):
        """Save questions to local JSON file."""
        if not questions:
            logging.warning("No questions to save")
            return False

        with open("../data/output/leetcode_questions.json", "w") as file:
            json.dump(questions, file, indent=4, ensure_ascii=False)

        logging.info(f"Saved {len(questions)} questions successfully")
        return True

    def mkdir(self):
        with open("../data/output/leetcode_questions.json", "r") as file:
            data = json.load(file)
            if not data:
                logging.error("No data to process")
                return

        for question in data:
            question = question.get("stat", {})
            self._copy_by_question(question)

    def sync_by_frontend_question_id(self, frontend_question_id: int):
        """Synchronize question by frontend question ID."""
        with open("../data/output/leetcode_questions.json", "r") as file:
            data = json.load(file)
            if not data:
                logging.error("No data to process")
                return

        for question in data:
            question = question.get("stat", {})
            if question.get("frontend_question_id") == frontend_question_id:
                self._copy_by_question(question)
                break

    def _copy_by_question(self, question):
        SRC_DIR = "/Users/triettrinh/project/triet/trinhminhtriet/leetcode-doocs"
        DIST_DIR = "/Users/triettrinh/project/triet/trinhminhtriet/leetcode"

        frontend_question_id = question.get("frontend_question_id")
        question_slug = question.get("question__title_slug")
        question_title = question.get("question__title")

        start0 = int(int(frontend_question_id) / 100) * 100
        start_str = f"{start0:04d}"
        end0 = start0 + 99
        end_str = f"{end0:04d}"
        src_folder_path = f"{SRC_DIR}/solution/{start_str}-{end_str}/{frontend_question_id:04d}.{question_title}"
        dist_folder_path = f"{DIST_DIR}/solutions/{start_str}-{end_str}/{frontend_question_id:04d}.{question_slug}"

        if not os.path.exists(dist_folder_path):
            os.makedirs(dist_folder_path)
            logging.info(
                f"Created distination directory: {dist_folder_path}")

        if os.path.exists(src_folder_path):
            for filename in os.listdir(src_folder_path):
                if filename == "README.md":
                    continue
                if filename == "README_EN.md":
                    filename = "README.md"

                src_file = os.path.join(src_folder_path, filename)
                dest_file = os.path.join(dist_folder_path, filename)
                if os.path.isfile(src_file):
                    if not os.path.exists(dest_file):
                        shutil.copy(src_file, dest_file)
                        logging.info(f"Copied {src_file} to {dest_file}")
        else:
            logging.warning(
                f"[{frontend_question_id}] Src folder path does not exist.")
