[게시판 URL 디자인 144cc60c603b80e0b27df7d0a709a4ce_all.csv](https://github.com/user-attachments/files/18048156/URL.144cc60c603b80e0b27df7d0a709a4ce_all.csv)# 게시판 프로젝트

생성일: 2024년 11월 20일 오후 11:21

# 실행방법

- 사전 준비
    - board/h2-database-2023-07.zip
        - h2-database 다운로드 및 실행 ( [h2.sh](./h2.sh) )
        - 데이터베이스 : “board” 생성
        - 계정 : sa / 0000
    - [application.properties](application.properties) 수정 (board/src/main/resources/application.properties)
        - file.dir : 업로드할 파일 다운로드 경로 지정 (서버 경로)

<pre>
💡

IDE 실행

- h2 database 실행 → ./h2.sh
- BoardApplication.class 실행
</pre>

<pre>
💡

JAR 실행

- h2 database 실행 → ./h2.sh
- ./gradlew clean bootJar
- java -jar build/libs/board-snapshot-001.jar 실행
</pre>

# 인프라 구성

![image](https://github.com/user-attachments/assets/a448c109-a99e-40ca-bc55-4186859aea03)


# 도메인 흐름

<img width="843" alt="image 2" src="https://github.com/user-attachments/assets/6be44148-8436-4fcf-b1ce-4ea16a849bc4">


# URL 디자인
[Uploading 게시판 URL 디자인 144cc60c603b80e이름,TYPE,URL,Description
[멤버 URL],,,
회원 등록,POST,http://localhost/members/form,회원 등록
회원 등록 페이지 조회,GET,http://localhost/members/form,회원 등록 페이지 조회
홈,GET,http://localhost/members,홈
회원 정보 조회,GET,http://localhost/members/{loginId}/edit,회원정보 조회
회원 정보 수정,POST,http://localhost/members/{loginId}/edit,회원정보 수정
,,,
[로그인 URL],,,
로그아웃,GET,http://localhost/members/logout,로그아웃 진행
로그인,GET,http://localhost/members/login,로그인 페이지 조회
로그인,POST,http://localhost/members/login,로그인
,,,
[게시판 URL],,,
게시판 조회,GET,http://localhost/members/{loginId}/boards,게시판 조회
게시글 개별 조회,GET,http://localhost/members/{loginId}/boards/{id},게시판 개별 조회
게시글 수정페이지 조회,GET,http://localhost/members/{loginId}/boards/edit/{id},게시글 수정 페이지 조회
게시글 수정,POST,http://localhost/members/{loginId}/boards/edit/{id},게시글 수정
게시글 생성 페이지 조회,GET,http://localhost/members/{loginId}/boards/new/form,게시글 생성 페이지 조회
게시글 생성,POST,http://localhost/members/{loginId}/boards/new/form,게시글 생성
게시글 이미지 파일 다운로드,GET,http://localhost/members/{loginId}/boards/images/{filename},게시글 세부 내용중 이미지 파일 다운로드
게시글 파일 다운로드,GET,http://localhost/members/{loginId}/boards/attach/{id},게시글 세부 내용중 파일 다운로드
게시글 삭제,POST,http://localhost/members/{loginId}/boards/{id}/delete,게시글 삭제
,,,
[ErrorPage URL],,,
500 에러 페이지,GET,http://localhost/errors/custom/500,500 에러 페이지
502 에러 페이지,GET,http://localhost/errors/custom/502,502 에러 페이지
404 에러 페이지,GET,http://localhost/errors/custom/404,404 에러페이지0b27df7d0a709a4ce_all.csv…]()


# 로그인 화면

- 로그인기능
    - Filter + Interceptor 구현
        - Filter :  Http Session 검증
        - Interceptor :  Web Url 아이디 검증

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 14 15](https://github.com/user-attachments/assets/24dbc49d-326c-4438-a7c5-8dc6858bb997)


- Validation
    - 스프링 검증 : 커스텀 애노테이션 + @Bean Validation
    - 수동 검증 : 커스텀 클래스 적용 ( @Validated +  CustomValidation 주입)

<aside>
💡

블로그 정리

https://gradualprecision.tistory.com/207

[https://gradualprecision.tistory.com/210](https://gradualprecision.tistory.com/210)

</aside>

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 11 21](https://github.com/user-attachments/assets/cae4b804-8837-4186-a282-ce005ea59f91)


# 게시판

<aside>
💡

https://gradualprecision.tistory.com/211

</aside>

- 파일 업/다운로드
    - 파일 클래스  : 업로드 이름 + 서버 저장 이름
    - 파일 업로드 : DB (파일 경로) 저장 + 실제 파일 (서버 저장)
    - 파일 다운로드 : Content Disposition (Response Entity  적용)
- Member + Board 맵핑
    - Board 외래키 → Member

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 13 52](https://github.com/user-attachments/assets/e8ea9a48-4f32-4162-a11c-ffdc4d73af46)

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 13 14](https://github.com/user-attachments/assets/22e6bd5b-bdb9-4939-b4cc-0386a0b3d392)

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 13 21](https://github.com/user-attachments/assets/8e5afe53-2205-476b-8195-1f7504a7da16)


# 회원 정보 수정

<aside>
💡

블로그 정리

https://gradualprecision.tistory.com/139

</aside>

- 회원 업데이트
    - Validation 적용
        - 패스워드 복잡성 체크

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 13 29](https://github.com/user-attachments/assets/eb5c3a96-efa5-46a7-9d62-3218ae93e94f)


# 메인페이지

- 회원 정보 표시
- 회원이 작성한 글 표시
- 게시글 삭제 기능

![%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-20_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11 13 35](https://github.com/user-attachments/assets/c3ac03f4-5253-491f-83db-cc36b379f840)


