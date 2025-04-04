FROM python:3.13.2-alpine3.21

RUN apk update && \
    apk add --no-cache bash && \
    rm -rf /var/cache/apk/*

SHELL ["/bin/bash", "-c"]

WORKDIR /app

COPY ../ /app

RUN pip install --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# 86400 seconds = 24 hours; # 3600 seconds = 1 hour
CMD ["bash", "-c", "while true; do sleep 86400; done"]
