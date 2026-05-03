#!/usr/bin/env bash

PORT="$1"

if [ -z "$PORT" ]; then
  echo "Usage: ./run.sh <port>"
  exit 1
fi

./mvnw clean package

if [ $? -ne 0 ]; then
  echo "Maven build failed"
  exit 1
fi

APP_PORT="$PORT" docker compose up --build