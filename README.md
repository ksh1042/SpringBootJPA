# SpringBoot JPA
- [인프런 - 김영한 - 실전! 스프링 부트와 JPA 활용1](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1)

## 목표
- JPA와 SpringBoot를 접목시킨 웹 어플리케이션 개발 방법 복습
- Swagger를 통한 통합 테스트 환경 복습
- DDD 개발법 학습

## Stack
![img_java](https://img.shields.io/badge/java&nbsp;11-007396?style=for-the-badge&logo=java&logoColor=white)
![img_gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![img_springboot](https://img.shields.io/badge/spring&nbsp;boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![img_thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![img_tomcat](https://img.shields.io/badge/apache&nbsp;tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![img_hibernate](https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![img_h2](https://img.shields.io/badge/H2&nbsp;DB-113348?style=for-the-badge&logo=h2&logoColor=white)

## 1. 특이사항
- Lombok 사용 시 Intellij IDEA 설정에서 반드시 Enable annotation processing을 활성화 시켜주어야 한다.
![img.png](images/img.png)
- Springboot 2 부터 Connection Pool은 Hikari를 기본으로 사용된다.
- slf4j는 로그 인터페이스 모음이며, 구현체로 logback, log4j, log4j2 등이 있다.

### 1.1. Thymeleaf
#### 특징
- 스프링과 연동되는 기능이 많으며, 스프링에서 전폭적으로 지원해주는 서버 사이드 뷰 엔진이다.
- 자연스러운 템플릿으로 JSP처럼 기존의 HTML의 형식을 깨지 않는다.
```html
<!-- JSP -->
<thead>
  <tr>
    <c:if test="${msgs.product.name ne null}">
      <th th:text="msgs.product.name">name</th>
      <th th:text="msgs.product.value">value</th>
    </c:if>
  </tr>
</thead>
```
```html
<!-- Thymeleaf -->
<thead>
  <tr>
    <th th:text="#{msgs.product.name}">name</th>
    <th th:text="#{msgs.product.value}">value</th>
  </tr>
</thead>
```
- Thymeleaf 3.0 이전의 경우는 성능상 이슈가 존재한다.
- Thymeleaf 3.0 이전에서는 태그가 완전히 닫혀야지만 인식되는 문제가 존재한다.
```html
<!-- ERROR -->
<br>
```
```html
<!-- SUCCESS -->
<br></br>
```

### 1.2. Springboot DevTools
- ```build.gradle```에 해당 의존성을 추가하면 뷰 파일 수정 시 매 번 재기동해이햐는 번거로움을 덜 수 있다.
```
dependencies {
  ...
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'org.springframework.boot:spring-boot-devtools'
  compileOnly 'org.projectlombok:lombok'
  ...
}
```
- 해당 의존성 추가 후 부터는 뷰 파일 수정 후 재기동 없이 해당 파일을 리컴파일 하면 정상적으로 반영된다. (Ctrl+Shift+F9)
- springboot 기동 시 아래 이미지처럼 restartedMain 문구가 뜨면 정상 적용된 것이다.
![img.png](images/img3.png)

### 1.3. Gradle 라이브러리 의존성 확인 방법
- intelliJ IDEA 사용 시 Gradle 탭의 dependencies를 통해 확인할 수 있다.
![img.png](images/img2.png)
- 터미널에서 프로젝트 홈 경로에 있는 ```gradlew ```를 실행해주면 의존성을 확인할 수 있다.
```shell
user$ ./gradlew dependencies
```
```
> Task :dependencies

------------------------------------------------------------
Root project 'SpringBootJPA'
------------------------------------------------------------

annotationProcessor - Annotation processors and their dependencies for source set 'main'.
\--- org.projectlombok:lombok -> 1.18.24

apiElements - API elements for main. (n)
No dependencies

archives - Configuration for archive artifacts. (n)
No dependencies

bootArchives - Configuration for Spring Boot archive artifacts. (n)
No dependencies

compileClasspath - Compile classpath for source set 'main'.
+--- org.projectlombok:lombok -> 1.18.24
+--- org.springframework.boot:spring-boot-starter-data-jpa -> 2.7.1
|    +--- org.springframework.boot:spring-boot-starter-aop:2.7.1
|    |    +--- org.springframework.boot:spring-boot-starter:2.7.1
|    |    |    +--- org.springframework.boot:spring-boot:2.7.1
|    |    |    |    +--- org.springframework:spring-core:5.3.21
|    |    |    |    |    \--- org.springframework:spring-jcl:5.3.21
|    |    |    |    \--- org.springframework:spring-context:5.3.21
...
...
...
```

## 2. 테스트
### 2.1. ```@Rollback(false)```
- ```@Transactional``` 어노테이션 사용 시 테스트 환경에서 트랜잭션 의존성 주입을 받는데, 테스트 종료 후 강제로 롤백을 수행하기 때문에 직접 데이터베이스에 적용된 것을 확인하고 싶으면 해당 롤백 어노테이션을 사용하여 수동으로 제어해야 한다.
```java
@Test
@Transactional
@Rollback(false)
void test(){
  // TODO --> do something
}
```