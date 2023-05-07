#!/usr/bin/env bash

REPOSITORY="/home/ec2-user/toy_com"
JAR_FILE="$REPOSITORY/build/libs/com-0.0.1-SNAPSHOT.jar"
CURRENT_PID=$(pgrep -f $JAR_FILE)

if [ -z $CURRENT_PID ]; then
  echo "No application Running"
else
  echo "kill application"
  sudo kill -9 $CURRENT_PID
fi