## log4j
log4j 2.17.1 이상 버전을 사용해야 함(보안이슈),
spring-boot-starter-web에 내장 logging 모듈이 있기 때문에 의존성 제거를 해야함.

## 지양해야 할 Annotations
@AllArgsConstructor
* 두 개의 같은 타입 인스턴스 멤버를 선언한 상황에서 개발자가 선언된 인스턴스 멤버의 순서를 바꾸면, 개발자도 인식하지 못하는 사이에 lombok이 생성자의 파라미터 순서를 필드 선언 순서에 따라 변경하게 된다. 이때, IDE가 제공해주는 리팩토링은 전혀 동작하지 않고, 두 필드가 동일 타입이기 때문에 기존 소스에서도 오류가 발생하지 않아 아무런 문제없이 동작하는 것으로 보이지만, 실제로 입력된 값이 바뀌어 들어가는 상황이 발생한다.

@EqualsAndHashCode

* 객체를 Set에 저장한 뒤 필드 값을 변경하면 hashCode가 변경되면서 이전에 저장한 객체를 찾을 수 없는 문제가 발생한다.

@NoArgsConstructor

* 필드들이 final로 생성되어 있는 경우에는 필드를 초기화할 수 없기 때문에 생성자를 만들 수 없고 에러가 발생한다. → @NoArgsConstructor(force=true) 옵션을 이용해 final 필드를 강제 초기화 시켜 생성자를 만들 수 있다.
* @Nonnull 같이 필드에 제약조건이 설정되어 있는 경우, 추후 초기화를 진행하기 전까지 생성자 내 null-check 로직이 생성되지 않는다.

## JPA
spring.jpa.hibernate.naming.strategy=org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
Entity Field값과 db의 coloumn값을 일치시기키 위함.

## git
아이디 비밀번호 보호를 위해 파일은 두고 변화 언트래킹
git update-index --assume-unchanged src/main/resources/application-db.yml
git update-index --assume-unchanged src/main/resources/application-oauth2.yml

## spring-security-oauth2-autoconfigure
SpringBoot 1.5와 2.X의 설정을 똑같은 방식으로 하기 위한 라이브러리

## Serializable
Java의 Object또는 Data를 자바 외부 시스템에서도 사용할 수 있게 Byte형식으로 변환

## SpringBoot Defendency의 Explicit Version
버전 명시 하는걸 지양해야함. SpringBoot에서 관리하는 compatible 버전을 자동으로 가져오게 하는 걸 추천.

##Google Login 구현
`public class SpringSecurity extends WebSecurityConfigurerAdapter`
* @EnableWebSecurity로 SpringSecurity설정들 활성화
* Configure 메소드를 @Override하여 Requset Url에 대한 Permission설정 및 로그인(로그인 로직을 담당하는 UserService의 구현체를 콜백함수로 할당) 로그아웃 설정.

`public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>`

__loadUser 메소드를 @Override__(로그인 구현)
> loadUer를 @Override하는 이유? Entity에 유저정보 저장 및 업데이트, 세션에 유저정보 저장.
* userRequest 파라미터로 User정보 load.

<details><summary>Parameter List</summary>

1. registrationId: 로그인 서비스 종류
2.  userNameAttributesName: 로그인 진행 시 Key가 되는 Field값.
3.  attributes: OAuth2UserService를 통해 가져온 Attributes를 담은 Custom Class
</details>

* delegate객체를 만들어서 DefaultOAuth2UserService클래스의 loadUser 메소드에 userRequest를 parameter로 OAuth2User 객체 반환.(회원정보를 받아오기 위한 prameter들 받기)
* OAuth2UserRequest와 OAuth2User객체로 Custom객체인 OAuthAttributes생성
```
OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributesName, oAuth2User.getAttributes());
```

* OAuthAttributes로 User를 반환 및 업데이트하는 saveOrUpdate 메소드 생성

* Session정보를 SessionUser객체로 바꿔 세션에 저장.(나중에 세션 저장소를 radis로 변환 예정)

* return __DefaultOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey)__
  SimpleGrantedAuthority(Role)객체를 여러개 생성하지 않게 Singleton으로 생성

## @AutoConfigureMockMvc
테스트 클래스에 @Controller, @RestController, @Service, @Repository Bean을 생성해 메모리에 올림
@WebMvcTest는 컨트롤러만 올리니 @SpringBootTest와 @AutoConfigureMockMvc두개를 함께 사용하는 것이 좋음.

## JPA Auditing 활성화
`Application.java`
클래스에 @EnableJpaAuditing 추가

## Redis로 Http세션 저장
Redis client로 Lettuce를 사용.
Spring Session Data Redis 라이브러리로 Http Session을 Redis에 저장.
spring:session:sessions:6f6b2447-4bbf-4908-8905-327aae68fa15

## CreatedDate와 ModifiedDate를 관리하기 위한 BaseTimeEntity를 생성

## @Converter와 implements AttributeConverter를 사용해 List<User>를 db 컬럼으로 사용


# Exception 구현. @LoginCheck Annotation 만들기 or AOP로 로그인체크. LoginSession Radis(Cache서버 데이터 lifetime)로 저장하기. API인 만큼 주석 달기.
# 각 일정들을 효율적으로 select 하기 위한 db index 조사.
# Redis.conf 파일로 설정하기
# mysql allowPublicKeyRetrieval 조사
# 세션 만료시간 지정하기

