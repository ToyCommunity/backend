#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/toy_com
JAR_PATH=$REPOSITORY/build/libs/*.jar
BUILD_JAR=$(ls $JAR_PATH)
JAR_NAME=$(basename $BUILD_JAR)
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]; then
  echo "No application Running"
else
  echo "kill application"
  kill -9 $CURRENT_PID
fi