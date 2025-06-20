# CurPick Backend

**CurPick Backend**는 사용자 맞춤형 직업 콘텐츠 큐레이션 및 웹소캣 채팅을 위한 백엔드 애플리케이션입니다.

---

## 🛠 기술 스택

<div align="left"> 
  <img src="https://img.shields.io/badge/Java_21-007396?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"> 
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Data JPA">  
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle"> 
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"> 
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"> 
  <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security"> 
  <img src="https://img.shields.io/badge/Spring_Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Web"> 
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger"> 
</div>

---

## 📂 디렉터리 구조

프로젝트의 주요 디렉터리 구조는 다음과 같습니다:
``` 
CurPick_Backend 
├── gradle/ # Gradle 설정 파일 
├── src/ 
│ ├── main/ 
│ │ ├── java/ 
│ │ │ └── com.curpick.CurPick/ 
│ │ │ ├── domain/ # 도메인 계층 관련 코드 
│ │ │ ├── global/ # 전역 설정, 공통 유틸리티 
│ │ │ └── CurPickApplication.java # 메인 클래스 
│ │ ├── resources/ # 설정 파일 (application.yml 등) 
│ └── test/ # 테스트 코드 
├── build.gradle # Gradle 빌드 스크립트 
├── settings.gradle # 프로젝트 설정 파일 
├── gradlew* # Gradle 실행 스크립트 
├── HELP.md # 추가 도움말 파일
``` 

---

## 📋 기능

CurPick은 다음과 같은 주요 기능을 제공합니다.

🔐 사용자 인증 시스템
- JWT 기반 이메일 인증 및 회원가입
- Spring Security를 통한 엔드포인트 보안
- 패스워드 암호화 (BCrypt)

💼 직업 추천 기능
- 고용24 API 연동을 통한 실시간 직업 데이터 수집

💬 실시간 채팅 시스템
- WebSocket 기반 1:M 채팅 구현
- STOMP 프로토콜을 이용한 메시지 브로커

📝 커뮤니티 기능
- 면접 후기 게시판 CRUD 기능

👤 마이페이지 시스템
- 닉네임 변경 기능

⚙️ 관리자 기능
- 사용자 목록 모니터링
- 게시글 삭제 및 관리 기능

---
