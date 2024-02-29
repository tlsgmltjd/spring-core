package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

// request scope
// HTTP 요청이 당 하나씩 생성되고 요청이 끝나는 시점에 소멸된다.
// 초기화 시점에 uuid를 생성하여 저장한다. 각각의 요청을 식별할 수 있음
// requestURL은 초기화 시점에는 모르니까 외부에서 수정하게 세터 얼어둠

@Component
@Scope(value = "request")
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("["+uuid+"]" + "["+requestURL+"]" + ": " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("["+uuid+"] request scope bean close: " + this);
    }
}
