#!/usr/bin/env bash

set -e

echo "Starting Socnet API"
mvn clean package -U -Dmaven.test.skip=true
sudo docker-compose down
#sudo docker network create shipnet
sudo docker-compose  up -d --build
# sudo docker-compose up -d --no-deps --build






