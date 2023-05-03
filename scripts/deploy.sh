#!/usr/bin/env bash
REPOSITORY=/home/ec2-user/toy_com
JAR_PATH="$REPOSITORY/build/libs/*.jar"
BUILD_JAR=$(ls $JAR_PATH)
JAR_NAME=${basename $BUILD_JAR}

CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
    echo "kill application pid : $CURRENT_PID"
    kill -9 $CURRENT_PID
else
    echo "No application running"
fi

sleep 5
echo "> $JAR_PATH start"
sudo nohup java -jar /home/ec2-user/toy_com/build/libs/com-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &