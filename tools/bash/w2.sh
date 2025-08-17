#!/bin/bash

set -ex

cd /workspace/trinhminhtriet/leetcode-doocs
echo ">>> Go to leetcode-doocs repository"
git pull
echo ">>> Updated leetcode-doocs repository"

cd /workspace/trinhminhtriet/leetcode/tools

python3 --version
# python3 -m venv .venv
. ./.venv/bin/activate

# python -m pip install --upgrade pip
# python -m pip install --upgrade -r requirements.txt

python --version

cd /workspace/trinhminhtriet/leetcode/tools/leetcode_solver
python question.py
echo ">>> Copied solutions from leetcode-doocs to leetcode"
sleep 2

cd /workspace/trinhminhtriet/leetcode/tools
python ReadMeFileManager.py
echo ">>> Copied ReadMe files"

echo ">>> Finished Weekly Tasks"