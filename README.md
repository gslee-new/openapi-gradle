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

## spec
- Java 17
- Spring boot 3.2.1
- openapi generator 6.5.0
- springdoc openapi 2.3.0

## 구현
- Gradle Dependency 설정
- Gradle Task 설정
- Gradle Build 설정
- OAS manifest 파일 생성
- 프로젝트 빌드
- OpenApi Object 코드 확인
- Controller 구현
- Swagger-ui

### Gradle Dependency 설정
~~~
plugins {
    id 'org.openapi.generator' version '6.5.0'
}    
dependencies {
    // springdoc-openapi webmvc-ui 설정
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
    //openapi generator 설정
    implementation ('org.openapitools:openapi-generator:6.5.0') {
        exclude group: 'org.slf4j', module: 'slf4j-simple'
    }
	implementation ("org.openapitools:openapi-generator-gradle-plugin:6.5.0") {
        exclude group: 'org.slf4j', module: 'slf4j-simple'
    }
}

compileJava.dependsOn tasks.named("openApiGenerate")    
~~~

### Gradle Task 설정
명령창에서 gradle openApiGenerate 실행하면 outputDir 하위에 api object 코드가 생성된다.
~~~
openApiGenerate {
    verbose.set(true)
    generatorName.set("spring")
    library.set("spring-boot")
    inputSpec.set(project.file("$rootDir/src/main/resources/petstore.yaml").absolutePath)
    outputDir.set(project.file("$buildDir/generated-sources").absolutePath)
    apiPackage.set("glsee.oas.openapi.rest")
    modelPackage.set("glsee.oas.openapi.domain")
    configOptions.set(
            [
                    interfaceOnly: "true",
                    useBeanValidation: "true",
                    performBeanValidation: "true",
                    serializableModel: "true",
                    useSpringBoot3 : "true",
                    openApiNullable: "false",
                    useTags        : "true",
                    interfaceOnly  : "true",
                    sourceFolder: "/java",
                    implFolder: "/java",
            ]
    )
}
~~~


### Gradle Build 설정
openapiGenerate를 통해서 생성된 코드가 컴파일 될 수 있도록 설정한다.
~~~
sourceSets {
    main {
        java {
            srcDirs = ['src/main/java', 'build/generated-sources/java']
        }
    }
}
~~~


### OAS manifest 파일 생성
OAS 파일을 정의하고 openApiGenerate task의 inputSpec 경로에 파일을 만든다.
~~~
openapi: 3.0.3
info:
  title: Swagger Petstore - OpenAPI 3.0
  description: 
  termsOfService: http://swagger.io/terms/
  contact:
    email: apiteam@swagger.io
  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html
  version: 1.0.11
externalDocs:
  description: Find out more about Swagger
  url: http://swagger.io
servers:
  - url: https://petstore3.swagger.io/api/v3
tags:
  - name: pet
    description: Everything about your Pets
    externalDocs:
      description: Find out more
      url: http://swagger.io
  - name: store
    description: Access to Petstore orders
    externalDocs:
      description: Find out more about our store
      url: http://swagger.io
  - name: user
    description: Operations about user
paths:
  /pet:
    put:
      tags:
        - pet
      summary: Update an existing pet
      description: Update an existing pet by Id
      operationId: updatePet
      requestBody:
        description: Update an existent pet in the store
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Pet'
          application/xml:
            schema:
              $ref: '#/components/schemas/Pet'
          application/x-www-form-urlencoded:
            schema:
              $ref: '#/components/schemas/Pet'
        required: true
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Pet'          
            application/xml:
              schema:
                $ref: '#/components/schemas/Pet'
        '400':
          description: Invalid ID supplied
        '404':
          description: Pet not found
        '405':
          description: Validation exception
      security:
        - petstore_auth:
            - write:pets
            - read:pets
~~~


### 프로젝트 빌드
~~~
$ gradle clean
$ gradle build
~~~
gradle build을 실행하고 build 디렉터리 하위에 generated-sources 코드가 생성되었는지 확인한다.

### OpenApi Object 코드 확인
OAS manifest 파일에 정의한 objects class 파일이 생성되었는지 확인

### Controller 구현
API Controller 클래스를 생성하고 OpenApi Object interface를 implements 한다.
interface의 메서드를 override 처리
~~~
@RestController
public class PetControllerImpl implements PetApi {
  @Override
  public ResponseEntity<Pet> getPetById(Long petId) {
    return PetApi.super.getPetById(petId);
  }

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return PetApi.super.getRequest();
  }

  @Override
  public ResponseEntity<Pet> addPet(Pet pet) {
    return PetApi.super.addPet(pet);
  }

  @Override
  public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
    return PetApi.super.deletePet(petId, apiKey);
  }

  @Override
  public ResponseEntity<List<Pet>> findPetsByStatus(String status) {
    return PetApi.super.findPetsByStatus(status);
  }

  @Override
  public ResponseEntity<List<Pet>> findPetsByTags(List<String> tags) {
    return PetApi.super.findPetsByTags(tags);
  }

  @Override
  public ResponseEntity<Pet> updatePet(Pet pet) {
    return PetApi.super.updatePet(pet);
  }

  @Override
  public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
    return PetApi.super.updatePetWithForm(petId, name, status);
  }

  @Override
  public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata,
      Resource body) {
    return PetApi.super.uploadFile(petId, additionalMetadata, body);
  }
}
~~~

### Swagger-ui
서버를 실행하고 swaggger ui를 확인하여 명세한 rest api가 존재하는지 확인
url: localhost:8080/swagger-ui.html


![ex_screenshot](/src/main/resources/static/img/img.png)

### 실행
- gradle clean
- gradle openApiGeneratoe
- gradle build
- java -jar build/libs/***.jar


### 참조
https://openapi-generator.tech/docs/plugins