import os
from dotenv import load_dotenv

load_dotenv()


class LeetCodeConfig:
    """Configuration class for LeetCode-related constants."""

    MAIN_LANGUAGE = "java"
    EXTENSIONS = {
        "rust": "rs",
        "ruby": "rb",
        "csharp": "cs",
        "cpp": "cpp",
        "golang": "go",
        "typescript": "ts",
        "javascript": "js",
        "python": "py",
        "java": "java",
        "php": "php",
        "mysql": "sql",
        "bash": "sh",
        "swift": "swift",
    }
    BASE_URL = "https://leetcode.com"
    DATABASE_URL = f"mysql+pymysql://{os.getenv('DB_USERNAME')}:{os.getenv('DB_PASSWORD')}@{os.getenv('DB_HOST')}/{os.getenv('DB_NAME')}"
    STRAPI_USERID = os.environ.get("STRAPI_USERID")
    STRAPI_USERNAME = os.environ.get("STRAPI_USERNAME")
    COOKIE = os.environ.get("COOKIE")
    CSRF_TOKEN = os.environ.get("CSRF_TOKEN")

    DEFAULT_HEADERS = {
        "Origin": BASE_URL,
        "Content-Type": "application/json",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36",
        "X-CSRFToken": CSRF_TOKEN,
        "Cookie": COOKIE,
    }
