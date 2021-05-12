# Spring

## 스프링 설정 
- Build Path - Configure BuildPath - 자바 버전확인, Project Facets에서 버전확인과 Tomcat 확인

### 1. pom.xml
- 라이브러리 다운 
- WEB-INF 밑에 있는 spring 파일에 있는 mvc-servlet.xml,  context-main.xml을 src/main/resources 밑에 spring 파일을 만들어 거기에 넣는다.

### 2. web.xml
- ContextLoaderListner - 위치 classpath : spring/context-xml , service와 mapper 가지고 있어야 함
- DispatherServlet - 위치 classpath : spring/mvc-servlet.xml

- web.xml
```
<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/context-*.xml</param-value>
	</context-param>
```

### 3. context.xml, mvc-servlet.xml

### 4. 빈 : Spring 관련 컨테이너가 갖고있는 객체 -> 서버시작 때 객체 생성 후 스프링 관련 컨테이너가 갖고있음 

### 5. 흐름
1. 서버 시작 시 Dispath Servlet은 @Controller 빈 가지고 있음, Controller Loader는 service 빈 가지고 있음 
2. 요청이 오면 Dispath Servlet가 요청을 받아 자기가 가지고 있는 @Controller에서 url을 조사 후 있으면 해당 메소드 실행한다. 


### 6. Context-main.xml에 추가
```
	 <context:component-scan base-package="com.study" use-default-filters="true">
	 	<context:exclude-filter type="annotation" 
	 	expression="org.springframework.stereotype.Controller"/>
	 </context:component-scan>
```

### 7. namespace에서 필요한 것들 추가

----- 기본 스프링 설정 끝 -----

## DB 설정 
1. context-datasource.xml, appconfig.properties를 spring 파일에 넣기
2. mybatis폴더를 src/main/resources에 넣기

---------------------------


mvc-servlet(DispatcherServlet)
context-* (ContextLoaderListener) 설정파일 작성
mybatis 설정 (데이터처리 pdf 참고) 준비 끝 

@Controller 객체 만들고
@Service 만들고
@Mapper 만들고 mapper에 맵핑된 mapper.xml 만들고

@controller에 reture에 해당하는 .jsp만들고 끝
DB에 접속까지 

---------------------------

