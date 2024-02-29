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
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest httpServletRequest) {
        String requestURL = httpServletRequest.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}

// 오류 발생
// 이유: 스프링 애플리케이션 시작시 request scope 빈을 주입 받고 있는데
// request scope는 HTTP 요청이 오고 끝날때까지의 생명주기를 가지고 있기 때문에 아직 생성되지 않는다.