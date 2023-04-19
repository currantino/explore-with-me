#!/bin/bash

mvn clean install -DskipTests 
docker-compose -f ./docker-compose.yaml build --no-cache
docker-compose -f ./docker-compose.yaml up --force-recreate
