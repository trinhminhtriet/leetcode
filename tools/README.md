# Leetcode Tools

## Docker

```sh
docker build -t trinhminhtriet/leetcode .
```

## Store questions to database

```sh
. ~/.venv/bin/activate

python main.py --store --min-id=3647
```

## Solve questions

```sh
python main.py --question 3648
python main.py --start 3647 --end 3686
```
