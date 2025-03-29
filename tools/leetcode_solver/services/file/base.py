import os
import logging
from config.config import LeetCodeConfig


class FileBaseService:
    """Base class for file services with shared utilities."""

    def __init__(self):
        self.config = LeetCodeConfig()

    def ensure_directory(self, path: str) -> None:
        """Ensure the directory exists, creating it if necessary."""
        os.makedirs(
            os.path.dirname(path) if os.path.isfile(path) else path, exist_ok=True
        )
        logging.debug(f"Ensured directory exists: {path}")
