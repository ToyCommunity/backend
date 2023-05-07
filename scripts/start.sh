#!/usr/bin/env bash

sleep 5
echo "> $JAR_PATH start"
sudo nohup java -jar /home/ec2-user/toy_com/build/libs/com-0.0.1-SNAPSHOT.jar > $REPOSITORY/nohup.out 2>&1 &