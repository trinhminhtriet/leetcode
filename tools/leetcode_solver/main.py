import logging
from cmd.problem_solver import LeetCodeProblemSolver

# Setup logging
logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s - %(levelname)s - %(message)s')


def main():
    solver = LeetCodeProblemSolver()
    frontend_question_id = 4
    success = solver.solve_by_frontend_question_id(frontend_question_id)
    if success:
        logging.info(f"Problem {frontend_question_id} solved successfully")
    else:
        logging.error(f"Failed to solve problem {frontend_question_id}")


if __name__ == "__main__":
    main()
