#!/usr/bin/env bash

# 빌드된 JAR 파일이 있는 디렉토리로 이동
cd /POKEMEDI-Server/build/libs || exit

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla java | grep "kgu" | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

# 빌드된 JAR 파일 중 가장 최신 파일 선택
JAR_NAME=$(ls -tr *.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

# nohup을 통해 JAR 파일 실행
nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME >> /POKEMEDI-Server/nohup.out 2>&1 &
