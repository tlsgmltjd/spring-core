package hello.core.lifecycle;

public class NetworkClient2 {

    private String url;

    public NetworkClient2() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("close: " + url);
    }

    public void init() {
        System.out.println("NetworkClient2.init");
        connect();
        call("초기화");
    }

    public void close() {
        System.out.println("NetworkClient2.close");
        disconnect();
    }
}

// 설정 정보에 초기화 메서드, 종료 메서드 지원

// @Bean(initMethod = "init", destroyMethod = "close")
// 빈 등록시 해당 속성을 부여하여 초기화, 소멸 메서드를 등록시킨다.

// 메서드 이름을 자유롭게 등록 가능, 스프링에 의존하지 않는다
// 코드가 아니라 설정 정보를 사용하기 때문에 외부 라이브러리를 초기화, 소멸 메서들 등록 가능하다 (빈 등록할때 initMethod, destroyMethod 속성 값으로)

// destroyMethod의 default 값: "(inferred)" (추론)
// 추론 기능은 close, shutdown이라는 이름으로 자동으로 메서드를 호출해준다! 종료 메서드의 이름을 추론해서 호출해준다
// 외부 라이브러리를 사용할 때나 스프링 빈을 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다..
// "" 공백 넣어주면 추론 기능을 막을 수도 있다.