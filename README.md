# 게시판 프로젝트

생성일: 2024년 11월 20일 오후 11:21

# 실행방법

- 사전 준비
    - board/h2-database-2023-07.zip
        - h2-database 다운로드 및 실행 ( [h2.sh](http://h2.sh) )
        - 데이터베이스 : “board” 생성
        - 계정 : sa / 0000
    - [application.properties](http://application.properties) 수정 (board/src/main/resources/application.properties)
        - file.dir : 업로드할 파일 다운로드 경로 지정 (서버 경로)

<aside>
💡

IDE 실행

- h2 database 실행 → ./h2.sh
- BoardApplication.class 실행
</aside>

<aside>
💡

JAR 실행

- h2 database 실행 → ./h2.sh
- ./gradlew clean bootJar
- java -jar build/libs/board-snapshot-001.jar 실행
</aside>

# 인프라 구성

![image.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/image.png)

# 도메인 흐름

![image.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/image%201.png)

# URL 디자인

[게시판 URL 디자인](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20URL%20%E1%84%83%E1%85%B5%E1%84%8C%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AB%20144cc60c603b80e0b27df7d0a709a4ce.csv)

# 로그인 화면

- 로그인기능
    - Filter + Interceptor 구현
        - Filter :  Http Session 검증
        - Interceptor :  Web Url 아이디 검증

![스크린샷 2024-11-20 오후 11.14.15.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.14.15.png)

- Validation
    - 스프링 검증 : 커스텀 애노테이션 + @Bean Validation
    - 수동 검증 : 커스텀 클래스 적용 ( @Validated +  CustomValidation 주입)

<aside>
💡

블로그 정리

https://gradualprecision.tistory.com/207

[https://gradualprecision.tistory.com/210](https://gradualprecision.tistory.com/210)

</aside>

![스크린샷 2024-11-20 오후 11.11.21.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.11.21.png)

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

![스크린샷 2024-11-20 오후 11.13.52.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.13.52.png)

![스크린샷 2024-11-20 오후 11.13.14.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.13.14.png)

![스크린샷 2024-11-20 오후 11.13.21.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.13.21.png)

# 회원 정보 수정

<aside>
💡

블로그 정리

https://gradualprecision.tistory.com/139

</aside>

- 회원 업데이트
    - Validation 적용
        - 패스워드 복잡성 체크

![스크린샷 2024-11-20 오후 11.13.29.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.13.29.png)

# 메인페이지

- 회원 정보 표시
- 회원이 작성한 글 표시
- 게시글 삭제 기능

![스크린샷 2024-11-20 오후 11.13.35.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-11-20_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_11.13.35.png)

![image.png](%E1%84%80%E1%85%A6%E1%84%89%E1%85%B5%E1%84%91%E1%85%A1%E1%86%AB%20%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8C%E1%85%A6%E1%86%A8%E1%84%90%E1%85%B3%20144cc60c603b806598f3c49c14c3894b/image%202.png)

# Github

[](https://github.com/sweetpark/springBoard)