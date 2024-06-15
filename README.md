# POKEMEDI-Server

# 📦프로젝트 구조

```
📦 
├─ .github
│  ├─ ISSUE_TEMPLATE
│  │  ├─ ♻️refactor.md
│  │  ├─ ❌delete.md
│  │  ├─ ⭐feature.md
│  │  └─ 🔨fix.md
│  ├─ pull_request_template.md
│  └─ workflows
│     └─ main.yml
├─ .gitignore
├─ README.md
├─ appspec.yml
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ scripts
│  └─ deploy.sh
├─ settings.gradle
└─ src
   ├─ main
   │  └─ java
   │     └─ com
   │        └─ turtledoctor
   │           └─ kgu
   │              ├─ auth
   │              │  ├─ config
   │              │  ├─ controller
   │              │  ├─ dto
   │              │  ├─ exception
   │              │  ├─ jwt
   │              │  ├─ oauth2
   │              │  └─ service
   │              ├─ chatbot
   │              │  ├─ controller
   │              │  ├─ openai
   │              │  │  ├─ DTO
   │              │  │  └─ service
   │              │  └─ service
   │              ├─ chathistory
   │              │  ├─ DTO
   │              │  ├─ repository
   │              │  └─ service
   │              ├─ chattext
   │              │  ├─ DTO
   │              │  ├─ repository
   │              │  └─ service
   │              ├─ comment
   │              │  ├─ DTO
   │              │  │  ├─ Request
   │              │  │  ├─ Response
   │              │  ├─ controller
   │              │  ├─ repository
   │              │  └─ service
   │              ├─ config
   │              ├─ converter
   │              ├─ entity
   │              │  ├─ base
   │              │  ├─ enums
   │              │  └─ repository
   │              ├─ error
   │              │  └─ DTO
   │              ├─ exception
   │              ├─ post
   │              │  ├─ controller
   │              │  ├─ dto
   │              │  │  ├─ request
   │              │  │  └─ response
   │              │  ├─ exception
   │              │  ├─ repository
   │              │  └─ service
   │              ├─ reply
   │              │  ├─ controller
   │              │  ├─ dto
   │              │  │  ├─ request
   │              │  │  └─ response
   │              │  ├─ repository
   │              │  └─ service
   │              ├─ response
   │              └─ upload
   │                 ├─ controller
   │                 ├─ dto
   │                 └─ service
   └─ test
      └─ java
         └─ com
            └─ turtledoctor
               └─ kgu
```
