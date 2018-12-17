# JttpServer
JSON 기반으로 Request/Response 방식으로 통신하는 자바 서버 라이브러리<br>
커밋 하는법에서부터 디자인 패턴, 오류 지적 등 어떤 종류든 피드백/가르침은 언제나 환영합니다. owlsogul@naver.com 으로 문의 주세요.

## 개발 동기
- 단순히 Json 통신을 위해서 Http 서버를 설치하고, 익숙하지 않은 다른 언어로 코딩하는게 불편한 상황이었다.
- 여러 프로젝트를 하는 도중 Json 통신을 위한 서버를 반복하여 만들고 있었다. 불필요하게 같은 일을 반복하는 것이 마음에 들지 않았다.
- 플러그인처럼 간단하게 페이지를 모듈 처럼 개발하고 넣었다 빼는 것을 개발해보고 싶었다.

이러한 이유로 코드 몇 줄로 간단하게 사용할 수 있는 Json 서버 라이브러리를 만들어보았다.

## 사용한 라이브러리
### Gson v2.8.5
#### 설명
Json 오브젝트처리를 좀더 간단하고 쉽게 하고 싶어 사용하였다.<br>
JttpServer 내에서 페이지를 개발하기 위해 꼭 필요한 라이브러리이다.
#### 주소
https://github.com/google/gson

## 사용 방법
간단하게 사용방법을 알려드립니다.
### 다운로드 링크
최신 버전을 다운로드 받아주세요.<br>
https://github.com/owlsogul/JttpServer/releases


### 서버 여는 방법
#### 기존 프로젝트 내에서 JttpServer를 활용한 서버를 여는 방법
1. JttpServer 객체를 생성합니다.
2. (JttpServer 객체).open(원하는 포트); 로 포트를 선점합니다.
3. (JttpServer 객체).listen(); 로 소켓을 받아들이기 시작합니다.

#### JttpServer를 바로 서버로 활용하는 방법
1. java -jar JttpServer.jar (원하는 포트 번호)


### 옵저버 사용 방법
현재 옵저버(이벤트들이 발생했을 때 호출됨):<br>
ClientController 객체에서 등록
- ClientConnectObserver: 클라이언트가 연결되었을 때 호출됨
- ClientDisconnectObserver: 클라이언트 연결 해체 되었을 때 호출됨
RequestController 객체에서 등록
- ClientRequestObserver: 클라이언트가 요청을 하여 응답을 보내기전 호출됨

### 페이지 개발 방법
1. RequestPage를 상속받아 원하는 일을 작성합니다.
2. page-info.json 파일을 작성합니다.
3. jar 파일로 만들어 서버 디렉토리/RequestPages/ 디렉토리에 넣습니다.
4. 서버를 실행하여 성공 로그가 뜨는지 확인합니다.
#### page-info.json 예시
[
	{
		"main":"request.Main",
		"page_name":"test"
	},
	{
		"main":"request.Main2",
		"page_name":"test2"
	}
]<br>
- main은 RequestPage의 주소, page_name은 page_name 입니다.
- 만약 한 jar 파일에 페이지가 여러개라면 여러개 다 등록해주셔야 정상 작동합니다.



