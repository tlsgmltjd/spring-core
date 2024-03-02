package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final MyLogger myLogger;
    // 오류 발생

//    private final ObjectProvider<MyLogger> myLoggerProvider;

    // proxyMode = ScopedProxyMode.TARGET_CLASS를 스코프에 적용시킨 후
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest httpServletRequest) {

        // $$SpringCGLIB$$0
        System.out.println("myLogger = " + myLogger.getClass());

        String requestURL = httpServletRequest.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject(); // <- 이 시점에 빈이 생성됨
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}

// 오류 발생
// 이유: 스프링 애플리케이션 시작시 request scope 빈을 주입 받고 있는데
// request scope는 HTTP 요청이 오고 끝날때까지의 생명주기를 가지고 있기 때문에 아직 생성되지 않는다.

// ObjectProvider를 사용하여 해결할 수 있다.
// ObjectProvider를 사용하여 HTTP 요청 시점까지 스프링 빈을 불러오는 것을 지연시킬 수 있었다.
// 같은 HTTP 요청이면 다른 클래스에서 getObject()를 해서 DL을 해도 같은 스프링 빈이 반환된다.

// 프록시
// 스코프 어노테이션에 proxyMode = ScopedProxyMode.TARGET_CLASS를 추가하면
// 해당 빈의 가짜 프록시 클래스를 만들어두고 HTTP 요청에 상관없이 다른 빈에서 주입받을 수 있다.
// 그래서 일반 싱글톤 빈처럼 주입 받을 수 있고 해당 빈의 getClass() 메서드를 호출해보면
// GCLIB 라이브러리(@Configuration에도 쓰임)가 만든 해당 클래스를 상속받은 가짜 클래스인 것을 확인할 수 있다.

// 가짜 프록시 객체는 원본 클래스를 찾는 방법을 알고 있다.
// 스프링 컨테이너에 해당 빈을 상속받은 가짜 빈(프록시)을 생성하고 해당 빈의 메서드를 실행시키면 그때 진짜 빈의 메서드로 요청을 위임하는 역할을 한다.
// 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 해당 객체를 사용하는 클라이언트의 입장에서는 원본인지 프록시인지 모르게 동일하게 사용한다(다형성)

// 해당 가짜 빈은 실제로 request scope랑 관계가 없다 그냥 진짜 빈을 상속받은 가짜이고 내부에 단순히 위임 로직만 있고 싱글톤처럼 동작한다.

// ObjectProvider를 사용하든 프록시 기술을 사용하든 핵심은 "진짜 빈의 조회을 요청 시점까지 지연"시키는 것이다.