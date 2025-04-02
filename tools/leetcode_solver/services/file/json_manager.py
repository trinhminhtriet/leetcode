import json
import logging
import os
from typing import List, Dict
from services.file.base import FileBaseService


class JsonFileManager(FileBaseService):
    """Service to manage JSON file operations for LeetCode questions."""

    def load_questions(self) -> List[Dict]:
        """Load questions from local JSON file if it exists."""
        self.ensure_directory(self.config.QUESTIONS_JSON_PATH)
        if os.path.exists(self.config.QUESTIONS_JSON_PATH):
            with open(
                self.config.QUESTIONS_JSON_PATH, "r", encoding="utf-8"
            ) as json_file:
                data = json.load(json_file)
                logging.info(f"Loaded questions from {self.config.QUESTIONS_JSON_PATH}")
                return data.get("stat_status_pairs", [])
        logging.info(
            f"No local questions file found at {self.config.QUESTIONS_JSON_PATH}"
        )
        return []

    def save_questions(self, data: Dict) -> None:
        """Save questions to JSON file."""
        self.ensure_directory(self.config.QUESTIONS_JSON_PATH)
        with open(self.config.QUESTIONS_JSON_PATH, "w", encoding="utf-8") as json_file:
            json.dump(data, json_file, indent=4, ensure_ascii=False)
        logging.info(f"Stored questions to {self.config.QUESTIONS_JSON_PATH}")

    def save_question_json(self, frontend_id: int, slug: str, api_data: Dict) -> None:
        """Save question API data to JSON file."""
        json_path = f"{self.config.QUESTIONS_JSON_DIR}/{frontend_id:04d}.{slug}.json"
        self.ensure_directory(json_path)
        with open(json_path, "w", encoding="utf-8") as json_file:
            json.dump(api_data, json_file, indent=4, ensure_ascii=False)
        logging.info(f"[{frontend_id}] Saved JSON content to {json_path}")
