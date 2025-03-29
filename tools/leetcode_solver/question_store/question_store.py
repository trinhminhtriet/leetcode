import logging
from typing import List, Optional
from config.config import LeetCodeConfig
from services.database.question import QuestionDatabaseService
from services.api.all_questions import AllQuestionsAPIService
from services.api.question_content import QuestionContentAPIService
from services.api.daily_question import DailyQuestionAPIService
from services.file.json_manager import JsonFileManager
from services.file.html_manager import HtmlFileManager


class QuestionStore:
    """Class to fetch and store LeetCode questions."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.db_service = QuestionDatabaseService()
        self.all_questions_service = AllQuestionsAPIService()
        self.content_service = QuestionContentAPIService()
        self.daily_service = DailyQuestionAPIService()
        self.json_manager = JsonFileManager()
        self.html_manager = HtmlFileManager()

    def store_questions(
        self, min_frontend_id: int = 3479, include_daily: bool = False
    ) -> None:
        """Store all questions, optionally including the daily question."""
        questions = self.json_manager.load_questions()
        daily_frontend_id = None

        if include_daily:
            daily_frontend_id = self.daily_service.get_daily_question()
            if daily_frontend_id:
                logging.info(
                    f"Including daily question with frontend ID: {daily_frontend_id}"
                )

        if not questions:
            questions = self.all_questions_service.fetch_all_questions()
            if questions:
                self.json_manager.save_questions({"stat_status_pairs": questions})

        if not questions and not daily_frontend_id:
            logging.error("No questions retrieved to store")
            return

        if questions:
            for question_data in questions:
                self._process_question(question_data, min_frontend_id)

        if daily_frontend_id:
            daily_question = next(
                (
                    q
                    for q in questions
                    if q["stat"]["frontend_question_id"] == daily_frontend_id
                ),
                None,
            )
            if not daily_question:
                daily_question = self._fetch_question_by_frontend_id(daily_frontend_id)
                if daily_question:
                    self._process_question(daily_question, min_frontend_id)
            else:
                self._process_question(daily_question, min_frontend_id)

        logging.info("Question storage completed")

    def _process_question(self, question_data: dict, min_frontend_id: int) -> None:
        """Helper method to process and store a single question."""
        frontend_id = question_data["stat"]["frontend_question_id"]

        if frontend_id < min_frontend_id:
            logging.info(f"Skipping question {frontend_id} (below threshold)")
            return

        slug = question_data["stat"]["question__title_slug"]
        logging.info(f"[{frontend_id}] {slug}")

        content = self.html_manager.load_question_html(frontend_id, slug)
        api_data = None

        if not content:
            logging.info(f"[{frontend_id}] Fetching from GraphQL")
            api_data = self.content_service.fetch_question_content(
                question_data["stat"]["question_id"], frontend_id, slug
            )
            if api_data:
                content = api_data["data"]["question"]["content"]
                self.html_manager.save_question_html(frontend_id, slug, content)
                self.json_manager.save_question_json(frontend_id, slug, api_data)
            else:
                return

        self.db_service.upsert_question(question_data, content)

    def _fetch_question_by_frontend_id(self, frontend_id: int) -> Optional[dict]:
        """Fetch a single question by frontend ID from the API."""
        all_questions = self.all_questions_service.fetch_all_questions()
        for question in all_questions:
            if question["stat"]["frontend_question_id"] == frontend_id:
                return question
        logging.error(f"Could not find question with frontend ID {frontend_id}")
        return None
