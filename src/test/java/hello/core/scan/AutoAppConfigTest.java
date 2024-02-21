package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    // 스프링에서 빈을 등록하는 방법은 @Bean이나 XML등을 통해서 설정 정보에 직접 등록할 빈을 작성해야했다.
    // 이렇게 일일이 다 등록하는건 너무 귀찮고 비효율적이다.
    // 그래서 스프링에서는 설정 정보 없이도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.

    // @ComponentScan 어노테이션을 설정 클래스에 붙힌다면 클래스 패스를 뒤져서 @Component 어노테이션이 붙은 클래스를 자동으로 스프링 빈에 등록해준다.
    // 하지만 이렇게 자동으로 등록해주면 기존에 명시해주었던 의존관계 주입을 명시할 수 없게된다.
    // 스프링에서는 @Autowired 어노테이션을 제공하여 생성자에 붙힌다면 주입받아야할 클래스의 타입으로 빈을 찾아 자동으로 의존관계를 주입해준다!

    @Test
    void basicScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
