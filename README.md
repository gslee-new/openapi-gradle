# Getting Started

### Api Document 자동 생성
OpenApi Generator를 이용하여 OAS 파일을 Spring code로 변환하고 Swagger-ui에서 api-spec 제공 및 mock 데이터 전달한다.

## 배경
백엔드와 프론트엔드는 프로젝트 성공을 위해서 원할한 소통이 필요하다. 소통의 수단으로 restapi 스펙을 문서로 공유하는 방법이 있다.
문서는 다양한 방법으로 작성될 수 있는데 일반 문서 서식에 인터페이스를 정의하는 방법, ML로 정의하는 방법 등 다양하다.
프론트엔드가 개발 구현 단계에서 딜레이 없이 일정을 소화하기 위해 설계 단계시 문서를 만들고 개발 구현 단계시 요청정보 및 응답정보(mock)를
제공할 수 있어야 한다. 또한 지속적으로 문서가 업데이트되고 테스트 될 수 있는 환경까지 고려되어야 한다.

## 방법
OpenApi Specification 마크업으로 RestApi를 정의하고 정의된 파일을 OpenApi Generator를 통해서 코드로 변환할 수 있다.
변환된 코드는 Swagger core를 통해 Swagger Ui(Documents)로 생성된다.
Documents를 통해서 프론트엔드, 백엔드, 기획자 등은 restapi 스펙을 확인할 수 있으며 초기 Sample 테스트 데이터도 제공된다.
restapi가 생성되고 실데이터가 연계되면서 Swagger는 테스트 데이터가 아닌 실 데이터값을 제공할 수 있다.

## 구현
- Gradle Dependency 설정
- Gradle Task 설정
- Gradle Build 설정
- OAS manifest 파일 생성
- 프로젝트 빌드
- OpenApi Object 코드 확인
- Controller 구현
- Swagger-ui

### spec
- Java 17
- Spring boot 3.2.1
- openapi generator 6.5.0
- springdoc openapi 2.3.0

### 실행
gradle clean
gradle openApiGeneratoe
gradle build
java -jar build/libs/***.jar
