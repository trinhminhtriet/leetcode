from sqlalchemy import Column, Integer, String, Text, DateTime
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class LeetcodeQuestion(Base):
    __tablename__ = "leetcode_questions"
    question_id = Column(Integer, primary_key=True)
    frontend_question_id = Column(Integer)
    title = Column(String)
    slug = Column(String)
    content = Column(Text)
    difficulty_level = Column(Integer)
    example_testcase_list = Column(String)
    submissions = Column(Integer, default=0)
    created_at = Column(DateTime)
    updated_at = Column(DateTime)
    published_at = Column(DateTime)


class LeetcodeSolution(Base):
    __tablename__ = "leetcode_solutions"
    id = Column(Integer, primary_key=True, autoincrement=True)
    question_id = Column(Integer)
    frontend_question_id = Column(Integer)
    slug = Column(String)
    lang = Column(String)
    solution = Column(Text)
    created_at = Column(DateTime)
    updated_at = Column(DateTime)
    published_at = Column(DateTime)


class LeetcodeSubmission(Base):
    __tablename__ = "leetcode_submissions"
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_id = Column(String)
    question_id = Column(Integer)
    frontend_question_id = Column(Integer)
    slug = Column(String)
    lang = Column(String)
    solution = Column(Text)
    submitted_by = Column(String)
    submitted_at = Column(DateTime)
    created_at = Column(DateTime)
    updated_at = Column(DateTime)
