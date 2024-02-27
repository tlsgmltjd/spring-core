package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient3 {

    private String url;

    public NetworkClient3() {
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

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient3.init");
        connect();
        call("초기화");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient3.close");
        disconnect();
    }
}

// @PostConstruct, @PreDestroy 어노테이션
// 메서드에 해당 어노테이션만 붙히면 바로 등록이 가능하다. (최신 스프링에서 권고)
// 임포트를 보면 스프링에 종속적인 기술이 아니다. (스프링에 종속적이지 않음)
// 컴포넌트 스캔에도 잘 어울린다.

// 단점: 외부 라이브러리에서 적용하지는 못한다. 외부 라이브러리에서 초기화, 소멸 메서드가 필요하면 @Bean의 기능을 사용하자

// @PostConstruct, @PreDestroy를 쓰자
// 외부 라이브러리를 초기화, 종료 해야한다면 @Bean의 initMethod, destroyMethod를 사용하자