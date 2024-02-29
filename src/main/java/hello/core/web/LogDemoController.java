package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final MyLogger myLogger;
    // 오류 발생

    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest httpServletRequest) {
        String requestURL = httpServletRequest.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject(); // <- 이 시점에 빈이 생성됨
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