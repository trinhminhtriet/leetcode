import os
from pathlib import Path
import logging
from typing import List, Dict


class ReadMeFileManager:
    """Recursively manages solution files in subfolders, handling README file operations."""

    def __init__(self, base_dir: str = "../solutions"):
        """Initialize with base directory and setup logging."""
        self.base_dir = Path(base_dir)
        self.setup_logging()
        self.processed_files: Dict[str, List[str]] = {
            "removed": [],
            "renamed": [],
            "skipped": []
        }

    def setup_logging(self) -> None:
        """Configure logging format and level."""
        logging.basicConfig(
            level=logging.INFO,
            format='%(asctime)s - %(levelname)s - %(message)s'
        )

    def process_file(self, file_path: Path, directory: Path) -> None:
        """Process files in a directory, handling README files conditionally."""
        readme_path = directory / "README.md"
        readme_en_path = directory / "README_EN.md"
        file_name = file_path.name

        # Check if both README files exist in the directory
        both_exist = readme_path.exists() and readme_en_path.exists()

        if file_name == "README.md" and both_exist:
            try:
                file_path.unlink()
                self.processed_files["removed"].append(str(file_path))
                logging.info(f"Removed: {file_path} (both READMEs existed)")
            except OSError as e:
                logging.error(f"Error removing {file_path}: {e}")
                self.processed_files["skipped"].append(str(file_path))

        elif file_name == "README_EN.md":
            new_path = directory / "README.md"
            try:
                # Only rename if README.md doesn't exist or was just removed
                if not new_path.exists() or both_exist:
                    file_path.rename(new_path)
                    self.processed_files["renamed"].append(
                        f"{file_path} -> {new_path}")
                    logging.info(f"Renamed: {file_path} to {new_path}")
            except OSError as e:
                logging.error(f"Error renaming {file_path}: {e}")
                self.processed_files["skipped"].append(str(file_path))

    def process_directory(self, directory: Path) -> None:
        """Recursively process all files and subdirectories."""
        if not directory.exists() or not directory.is_dir():
            logging.warning(f"Skipping {directory}: not a valid directory")
            return

        logging.info(f"Processing directory: {directory}")

        # Process all items in current directory
        for item in directory.iterdir():
            if item.is_file():
                self.process_file(item, directory)
            elif item.is_dir():
                # Recursively process subdirectories
                self.process_directory(item)

    def process_all(self) -> Dict[str, List[str]]:
        """Start recursive processing from base directory."""
        if not self.base_dir.exists():
            logging.error(f"Base directory {self.base_dir} does not exist")
            return self.processed_files

        self.process_directory(self.base_dir)
        return self.processed_files

    def print_summary(self) -> None:
        """Print summary of processed files."""
        logging.info("\nProcessing Summary:")
        logging.info(f"Files removed: {len(self.processed_files['removed'])}")
        for path in self.processed_files["removed"]:
            logging.info(f"  - {path}")

        logging.info(f"Files renamed: {len(self.processed_files['renamed'])}")
        for path in self.processed_files["renamed"]:
            logging.info(f"  - {path}")

        logging.info(
            f"Files skipped (errors): {len(self.processed_files['skipped'])}")
        for path in self.processed_files["skipped"]:
            logging.info(f"  - {path}")


def main():
    """Main execution function."""
    file_manager = ReadMeFileManager()
    results = file_manager.process_all()
    file_manager.print_summary()


if __name__ == "__main__":
    main()
