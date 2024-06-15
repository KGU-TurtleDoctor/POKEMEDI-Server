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
├─ src
│  ├─ main
│  │  └─ java
│  │     └─ com
│  │        └─ turtledoctor
│  │           └─ kgu
│  │              ├─ KguApplication.java
│  │              ├─ auth
│  │              │  ├─ config
│  │              │  │  ├─ CorsMvcConfig.java
│  │              │  │  └─ SecurityConfig.java
│  │              │  ├─ controller
│  │              │  │  ├─ MainController.java
│  │              │  │  └─ MyController.java
│  │              │  ├─ dto
│  │              │  │  ├─ CustomOAuth2User.java
│  │              │  │  ├─ KakaoResponse.java
│  │              │  │  ├─ LoginDTO.java
│  │              │  │  ├─ OAuth2Response.java
│  │              │  │  ├─ UserDTO.java
│  │              │  │  └─ isLoginCheckDTO.java
│  │              │  ├─ exception
│  │              │  │  ├─ AuthException.java
│  │              │  │  └─ CustomAuthenticationEntryPoint.java
│  │              │  ├─ jwt
│  │              │  │  ├─ CustomLogoutFilter.java
│  │              │  │  ├─ JWTExceptionFilter.java
│  │              │  │  ├─ JWTFilter.java
│  │              │  │  └─ JWTUtil.java
│  │              │  ├─ oauth2
│  │              │  │  └─ CustomSuccessHandler.java
│  │              │  └─ service
│  │              │     ├─ CustomOAuth2UserService.java
│  │              │     └─ MainService.java
│  │              ├─ chatbot
│  │              │  ├─ controller
│  │              │  │  └─ ChatBotController.java
│  │              │  ├─ openai
│  │              │  │  ├─ DTO
│  │              │  │  │  ├─ ChatBotApiRequest.java
│  │              │  │  │  ├─ ChatBotApiResponse.java
│  │              │  │  │  ├─ Message.java
│  │              │  │  │  ├─ OpenAiRequest.java
│  │              │  │  │  └─ OpenAiResponse.java
│  │              │  │  └─ service
│  │              │  │     └─ OpenAiApiService.java
│  │              │  └─ service
│  │              │     └─ ChatBotService.java
│  │              ├─ chathistory
│  │              │  ├─ DTO
│  │              │  │  ├─ ChatHistoryListRequest.java
│  │              │  │  └─ ChatHistoryListResponse.java
│  │              │  ├─ repository
│  │              │  │  └─ ChatHistoryRepository.java
│  │              │  └─ service
│  │              │     └─ ChatHistoryService.java
│  │              ├─ chattext
│  │              │  ├─ DTO
│  │              │  │  ├─ ChatTextListRequest.java
│  │              │  │  └─ ChatTextListResponse.java
│  │              │  ├─ repository
│  │              │  │  └─ ChatTextRepository.java
│  │              │  └─ service
│  │              │     └─ ChatTextService.java
│  │              ├─ comment
│  │              │  ├─ DTO
│  │              │  │  ├─ Request
│  │              │  │  │  ├─ CreateCommentRequest.java
│  │              │  │  │  ├─ DeleteCommentRequest.java
│  │              │  │  │  ├─ FindCommentsByPostRequest.java
│  │              │  │  │  └─ UpdateCommentRequest.java
│  │              │  │  ├─ Response
│  │              │  │  │  ├─ CreateCommentResponse.java
│  │              │  │  │  ├─ FindCommentsByPostResponse.java
│  │              │  │  │  └─ FindRepliesByCommentResponse.java
│  │              │  │  └─ createCommentDTO.java
│  │              │  ├─ ErrorMessage.java
│  │              │  ├─ controller
│  │              │  │  └─ CommentController.java
│  │              │  ├─ repository
│  │              │  │  └─ CommentRepository.java
│  │              │  └─ service
│  │              │     └─ CommentService.java
│  │              ├─ config
│  │              │  └─ RestTemplateConfig.java
│  │              ├─ converter
│  │              │  └─ DateConverter.java
│  │              ├─ entity
│  │              │  ├─ ChatHistory.java
│  │              │  ├─ ChatText.java
│  │              │  ├─ Comment.java
│  │              │  ├─ Image.java
│  │              │  ├─ Member.java
│  │              │  ├─ Post.java
│  │              │  ├─ PostLike.java
│  │              │  ├─ Reply.java
│  │              │  ├─ base
│  │              │  │  └─ BaseEntity.java
│  │              │  ├─ enums
│  │              │  │  ├─ ChatRole.java
│  │              │  │  └─ UserRole.java
│  │              │  └─ repository
│  │              │     └─ MemberRepository.java
│  │              ├─ error
│  │              │  └─ DTO
│  │              │     ├─ ErrorMessage.java
│  │              │     └─ ValidException.java
│  │              ├─ exception
│  │              │  ├─ ErrorCode.java
│  │              │  └─ GlobalExceptionHandler.java
│  │              ├─ post
│  │              │  ├─ controller
│  │              │  │  └─ PostController.java
│  │              │  ├─ dto
│  │              │  │  ├─ request
│  │              │  │  │  ├─ CreatePostRequest.java
│  │              │  │  │  ├─ DeletePostRequest.java
│  │              │  │  │  ├─ GetPostDetailRequest.java
│  │              │  │  │  ├─ SearchPostRequest.java
│  │              │  │  │  └─ UpdatePostRequest.java
│  │              │  │  └─ response
│  │              │  │     ├─ PostDetailResponse.java
│  │              │  │     └─ PostListResponse.java
│  │              │  ├─ exception
│  │              │  │  └─ PostException.java
│  │              │  ├─ repository
│  │              │  │  ├─ PostLikeRepository.java
│  │              │  │  └─ PostRepository.java
│  │              │  └─ service
│  │              │     └─ PostService.java
│  │              ├─ reply
│  │              │  ├─ ErrorMessage.java
│  │              │  ├─ controller
│  │              │  │  └─ ReplyController.java
│  │              │  ├─ dto
│  │              │  │  ├─ CreateReplyDTO.java
│  │              │  │  ├─ DeleteReplyDTO.java
│  │              │  │  ├─ UpdateReplyDTO.java
│  │              │  │  ├─ request
│  │              │  │  │  ├─ CreateReplyRequest.java
│  │              │  │  │  └─ UpdateReplyRequest.java
│  │              │  │  └─ response
│  │              │  │     └─ CreateReplyResponse.java
│  │              │  ├─ repository
│  │              │  │  └─ ReplyRepository.java
│  │              │  └─ service
│  │              │     └─ ReplyService.java
│  │              ├─ response
│  │              │  └─ ResponseDTO.java
│  │              └─ upload
│  │                 ├─ controller
│  │                 │  └─ UploadController.java
│  │                 ├─ dto
│  │                 │  ├─ ImageUploadDTO.java
│  │                 │  └─ PresignedUrlListResponse.java
│  │                 └─ service
│  │                    └─ UploadService.java
│  └─ test
│     └─ java
│        └─ com
│           └─ turtledoctor
│              └─ kgu
│                 └─ KguApplicationTests.java
└─ 
```
©generated by [Project Tree Generator](https://woochanleee.github.io/project-tree-generator)
