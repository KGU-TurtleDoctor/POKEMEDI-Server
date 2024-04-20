#!/bin/bash
BUILD_JAR="/home/ubuntu/app/kgu-0.0.1-SNAPSHOT.jar"
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> /home/ubuntu/app/log/deploy.log

echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/app/log/deploy.log

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/app/log/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/app/log/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/app/log/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포"    >> /home/ubuntu/app/log/deploy.log
sudo nohup java -jar $BUILD_JAR >> /home/ubuntu/app/log/deploy.log 2>/home/ubuntu/app/log/deploy_err.log &
