#!/usr/bin/env bash
PROJECT_PATH=jdc-monitor-api
PROJECT_NAME=jdc-monitor-api
PROJECT_VERSION=1.0.0-SNAPSHOT
LOG_PATH=~/jdc-monitor-api/logs/$PROJECT_NAME/
BASE_DIR=$(dirname "$PWD")
cd $BASE_DIR
mvn docker:build
docker stop $PROJECT_NAME
docker rm $PROJECT_NAME
docker run --net=host --name=$PROJECT_NAME -it -d --restart=always -v $LOG_PATH:/logs -v /etc/hosts:/etc/hosts $PROJECT_NAME:$PROJECT_VERSION
docker images|grep none|awk '{print $3}'|xargs docker rmi
docker logs -f --tail=1 $PROJECT_NAME