#!/bin/sh

REPOSITORY="/home/ec2-user/toy_com"
sleep 5
echo "> $JAR_PATH start"
nohup java -jar /home/ec2-user/toy_com/build/libs/com-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &