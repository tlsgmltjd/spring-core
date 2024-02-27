package hello.core.lifecycle;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }
}

// 스프링 빈의 라이프 사이클
// 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
// 초기화 콜백: 빈 생성, 의존관계 주입이 완료된 후 호출
// 소멸전 콜백: 빈이 소멸되기 직전에 호출

// 객체의 생성과 초기화를 분리하자
// 생성자는 생성에만 집중해야한다. 초기화는 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.
// 생성자 안에 무거운 초기화 작업을 함께하는 것 보다는 객체의 생성과 초기화를 명확하게 나누는 것이 좋다.

// 스프링이 빈 생명주기 콜백을 지원함
// 인터페이스 InitializingBean, DisposableBean
// 설정 정보에 초기화 메서드, 종료 메서드 지원
// @PostConstruct, @PreDestroy 어노테이션 지원