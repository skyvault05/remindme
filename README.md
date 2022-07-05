# RemindMe
## Dependencies
* springboot 2.3.1.RELEASE
* junit 5.8.1
* jpa 2.6.6
* mysql-connector-java 8.0.29
* log4j 2.17.1
* spring-boot-starter-oauth2-client 2.7.0
* spring-security-oauth2-autoconfigure 2.6.8
* spring-security-test 5.7.2
* spring-boot-test-autoconfigure 2.7.1

## Database
* mysql 8.0.29

`src/main/resources/application-oauth2.yml`
google OAuth clientId, Client Password입력
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            clientId: 클라이언트 ID
            client-secret: 클라이언트 비밀번호
```

`src/main/resources/application-db.yml`
mysql DB url, username , userpwd입력
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: mysql DB url
    username: mysql 유저 이름
    password: mysql 유저 비밀번호
```
