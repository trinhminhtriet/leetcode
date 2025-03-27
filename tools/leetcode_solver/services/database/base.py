from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from config.config import LeetCodeConfig
from models.models import Base


class DatabaseBaseService:
    """Base class for database connection and session management."""

    def __init__(self):
        self.engine = create_engine(LeetCodeConfig.DATABASE_URL)
        Base.metadata.create_all(self.engine)
        self.Session = sessionmaker(bind=self.engine)
        self.session = self.Session()

    def get_session(self):
        """Return the current session."""
        return self.session
