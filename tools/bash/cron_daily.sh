#!/usr/bin/env bash

cd /data/trinhminhtriet/leetcode/tools

python --version

python -m venv .venv
. ./.venv/bin/activate

# python -m pip install --upgrade pip
# python -m pip install --upgrade -r requirements.txt

python --version
pip --version

cd /data/trinhminhtriet/leetcode/tools/leetcode_solver

python daily.py
