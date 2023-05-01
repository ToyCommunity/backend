#!/usr/bin/env bash
REPOSITORY=/home/ubuntu/toy_com
# shellcheck disable=SC2164
cd $REPOSITORY

JAR_PATH=$REPOSITORY/build/libs/com-0.0.1-SNAPSHOT.jar

echo ">kill java process"
sudo killall java

echo "> $JAR_PATH start"
sudo nohup java -jar /home/ubuntu/toy_com/build/libs/com-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &
