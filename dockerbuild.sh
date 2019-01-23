#!/usr/bin/env bash
set -e
echo "Build images"
mvn clean package -U -Dmaven.test.skip=true
sudo docker-compose build





