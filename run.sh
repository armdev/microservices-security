#!/usr/bin/env bash

set -e
#sudo docker rm -f $(sudo docker ps -a -q)
#sudo docker rmi $(sudo docker images | grep "^<none>" | awk "{print $3}")
echo "Starting API For Hero!"
mvn clean package -U -Dmaven.test.skip=true
sudo docker-compose down
#sudo docker network create shipnet
sudo docker-compose  up -d --build
# sudo docker-compose up -d --no-deps --build






