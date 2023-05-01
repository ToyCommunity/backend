#!/usr/bin/env bash
REPOSITORY=/home/ec2-user/toy_com
cd $REPOSITORY

JAR_PATH=$REPOSITORY/build/libs/com-0.0.1-SNAPSHOT.jar

FILENAME="com-0.0.1-SNAPSHOT"

PID=$(lsof | grep $FILENAME | awk '{print $2}')

if [ -n "$PID" ]; then
    echo "Killing process $PID running on port 8080"
    kill $PID
else
    echo "No process found running on port 8080"
fi
echo "> $JAR_PATH start"
sudo nohup java -jar /home/ec2-user/toy_com/build/libs/com-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &