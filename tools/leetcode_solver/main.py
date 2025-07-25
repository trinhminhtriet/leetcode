import logging
import argparse
from question_store.question_store import QuestionStore
from solver.problem_solver import LeetCodeProblemSolver

# Setup logging
logging.basicConfig(
    level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s"
)


def main():
    parser = argparse.ArgumentParser(
        description="LeetCode Question Store and Solver")
    parser.add_argument("--store", action="store_true", help="Store questions")
    parser.add_argument(
        "--daily", action="store_true", help="Include the daily question when storing"
    )
    parser.add_argument(
        "--min-id",
        type=int,
        default=3479,
        help="Minimum frontend ID to process when storing",
    )
    parser.add_argument(
        "--question", type=int, help="Solve a problem by frontend_question_id"
    )

    parser.add_argument(
        "--start", type=int, help="Solve a problem by frontend_question_id"
    )
    parser.add_argument(
        "--end", type=int, help="Solve a problem by frontend_question_id"
    )

    args = parser.parse_args()

    if args.store:
        store = QuestionStore()
        store.store_questions(min_frontend_id=args.min_id,
                              include_daily=args.daily)

    if args.question:
        solver = LeetCodeProblemSolver()
        success = solver.solve_by_frontend_question_id(args.question)
        if success:
            logging.info(f"Problem {args.question} solved successfully")
        else:
            logging.error(f"Failed to solve problem {args.question}")

    if args.start and args.end:
        solver = LeetCodeProblemSolver()
        for problem_id in range(args.start, args.end):
            success = solver.solve_by_frontend_question_id(problem_id)
            if success:
                logging.info(f"Problem {problem_id} solved successfully")
            else:
                logging.error(f"Failed to solve problem {problem_id}")


if __name__ == "__main__":
    main()
