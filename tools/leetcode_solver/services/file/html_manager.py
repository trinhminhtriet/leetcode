import logging
from typing import Optional
from services.file.base import FileBaseService


class HtmlFileManager(FileBaseService):
    """Service to manage HTML file operations for LeetCode questions."""

    def load_question_html(self, frontend_id: int, slug: str) -> Optional[str]:
        """Load question content from local HTML file if it exists."""
        file_path = f"{self.config.QUESTIONS_HTML_DIR}/{frontend_id:04d}.{slug}.html"
        self.ensure_directory(file_path)
        if os.path.exists(file_path):
            with open(file_path, "r", encoding="utf-8") as html_file:
                content = html_file.read()
                logging.info(f"[{frontend_id}] Loaded question content from local file")
                return content
        return None

    def save_question_html(self, frontend_id: int, slug: str, content: str) -> None:
        """Save question content to HTML file."""
        html_path = f"{self.config.QUESTIONS_HTML_DIR}/{frontend_id:04d}.{slug}.html"
        self.ensure_directory(html_path)
        with open(html_path, "w", encoding="utf-8") as html_file:
            html_file.write(content)
        logging.info(f"[{frontend_id}] Saved HTML content to {html_path}")
