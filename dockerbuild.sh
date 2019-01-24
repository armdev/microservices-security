#!/usr/bin/env bash
set -e
echo "Build images"
#mvn clean package -U -Dmaven.test.skip=true
sudo docker-compose up -d --no-deps --build mysql
sudo docker-compose up -d --no-deps --build phpmyadmin
sudo docker-compose build





