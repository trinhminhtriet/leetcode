#!/bin/bash

cd /workspace/trinhminhtriet/leetcode/tools

python3 --version

# python3 -m venv .venv
. ./.venv/bin/activate

# python -m pip install --upgrade pip
# python -m pip install --upgrade -r requirements.txt

python --version
pip --version

cd /workspace/trinhminhtriet/leetcode/tools/leetcode_solver

python question.py
echo ">>> Copied solutions from leetcode-doocs to leetcode"
sleep 2

cd /workspace/trinhminhtriet/leetcode/tools
python ReadMeFileManager.py
echo ">>> Copied ReadMe files"
echo ">>> Finished Weekly Tasks"