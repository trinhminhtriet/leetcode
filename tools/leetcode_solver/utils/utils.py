def get_folder_path(frontend_question_id, question_slug):
    start0 = int(int(frontend_question_id) / 100) * 100
    start_str = f"{start0:04d}"
    end0 = start0 + 99
    end_str = f"{end0:04d}"
    folder_path = f"../../solutions/{start_str}-{end_str}/{frontend_question_id:04d}.{question_slug}"
    return folder_path
