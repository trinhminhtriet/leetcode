# Leetcode Tools

## Docker

```sh
docker build -t trinhminhtriet/leetcode .
```

## Store questions to database

```sh
. ~/.venv/bin/activate

python main.py --store --min-id=3700
```

## Solve questions

```sh
python main.py --question 3700
python main.py --start 3700 --end 3700
```
