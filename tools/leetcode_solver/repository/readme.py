import logging
import os
import re

from models.models import LeetcodeQuestion


class LeetcodeSolutionReadmeRepository:
    def __init__(self):
        self.filename = "README.md"
        self.question = None
        self.solution = {
            "slug": "",
            "title": "",
            "summary": "",
            "tags": [],
            "content": "",
            "thumbnail": "",  # Add if you have a thumbnail URL
            "is_serialized": False
        }

    def set_question(self, question: LeetcodeQuestion):
        self.question = question

    def get_solution(self):
        self.solution["slug"] = self.question.slug
        self.solution["title"] = f"✅ Detailed explanation with many programming languages."
        self.solution["summary"] = f"Solution for {self.question.title} in many programming languages."
        self.get_solution_detail()
        return self.solution

    def _get_folder_path(self) -> str:
        """Generate folder path for solutions (simplified assumption)."""
        frontend_question_id = self.question.frontend_question_id
        slug = self.question.slug

        start = (frontend_question_id // 100) * 100
        end = start + 99
        return (
            f"../../solutions/{start:04d}-{end:04d}/{frontend_question_id:04d}.{slug}"
        )

    def collect_languages(self, content: str):
        code_blocks = re.findall(r"#### (\w+)\n", content, re.DOTALL)

        languages = []
        for block in code_blocks:
            block = block.strip()
            if block:
                languages.append(block)

        self.solution["tags"] = languages

        return languages

    def get_solution_detail(self):
        frontend_question_id = self.question.frontend_question_id
        folder_path = self._get_folder_path()
        if not os.path.exists(f"{folder_path}/{self.filename}"):
            logging.error(f"[{frontend_question_id}] Solution not found.")
            return

        with open(f"{folder_path}/{self.filename}", "r") as f:
            solution_readme = f.read()

        self.collect_languages(solution_readme)

        solution_start = solution_readme.find("### Solution")
        solution_end = solution_readme.find("<!-- tabs:start -->")

        if solution_start == -1 or solution_end == -1:
            logging.error(f"[{frontend_question_id}] Solution section not found.")
            return

        solution_text = solution_readme[solution_start:solution_end].strip()

        prompt_text = """
    ---
    Rewrite solution above by this Markdown format:

    # Intuition
    <!-- Describe your first thoughts on how to solve this problem. -->

    # Approach
    <!-- Describe your approach to solving the problem. -->

    # Complexity
    - Time complexity:
    <!-- Add your time complexity here, e.g. $$O(n)$$ -->

    - Space complexity:
    <!-- Add your space complexity here, e.g. $$O(n)$$ -->
    """
        prompt_full = solution_text + prompt_text
        output_file_path = f"{folder_path}/prompt.md"
        with open(output_file_path, "w") as f:
            f.write(prompt_full)
        logging.info(f"[{frontend_question_id}] Solution detail written to {
                    output_file_path}.")

        tabs_start = solution_readme.find("<!-- tabs:start -->")
        tabs_end = solution_readme.find("<!-- tabs:end -->")

        if tabs_start == -1 or tabs_end == -1:
            logging.error(f"[{frontend_question_id}] Tabs section not found.")
            return

        codeblock = solution_readme[
            tabs_start : tabs_end + len("<!-- tabs:end -->")
        ].strip()
        codeblock = codeblock.replace("<!-- tabs:start -->", "").replace(
            "<!-- tabs:end -->", ""
        )

        codeblock2 = """

    # Code
    """
        codeblock3 = codeblock2 + codeblock

        contact_text = """

    # Contact information
    If you have any questions or suggestions, please feel free to contact me at https://trinhminhtriet.com/
    - Website: [trinhminhtriet.com](https://trinhminhtriet.com/)
    - Github: [trinhminhtriet](https://github.com/trinhminhtriet)
    - LinkedIn: [trinhminhtriet](https://www.linkedin.com/in/triet-trinh/)
    - Facebook: [trinhminhtriet](https://www.facebook.com/trinhminhtriet)
    - Twitter: [trinhminhtriet](https://twitter.com/trinhminhtriet)
    - Email: [contact@trinhminhtriet.com](mailto:contact@trinhminhtriet.com)
    """

        solution_full = solution_text + codeblock3 + contact_text
        solution_full = solution_full.replace("### Solution 1:", "# Solution:")

        with open(f"{folder_path}/SOLUTION.md", "w") as f:
            f.write(solution_full)
        logging.info(
            f"[{frontend_question_id}] Solution detail written to {folder_path}/SOLUTION.md"
        )

        self.solution["content"] = solution_text

        return solution_text
