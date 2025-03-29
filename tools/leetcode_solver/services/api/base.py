import requests
from config.config import LeetCodeConfig


class LeetCodeAPIBaseService:
    """Base class for LeetCode API services with shared session."""

    def __init__(self):
        self.config = LeetCodeConfig()
        self.session = requests.Session()
        self.session.headers.update(self.config.DEFAULT_HEADERS)
