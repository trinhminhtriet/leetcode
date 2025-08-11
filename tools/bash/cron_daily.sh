#!/bin/bash

cd /workspace/trinhminhtriet/leetcode/tools

# python --version

python3 -m venv .venv
. ./.venv/bin/activate

python -m pip install --upgrade pip
python -m pip install --upgrade -r requirements.txt

python --version
pip --version

cd /workspace/trinhminhtriet/leetcode/tools/leetcode_solver

python daily.py
