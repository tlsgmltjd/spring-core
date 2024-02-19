package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);

        // memberService, orderService, memberRepository 빈을 호출시 메서드 이름을 출력해주는 로직을 추가하였다.
        // 해당 테스트 코드를 봤을때 출력되는 예상 값은

        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.orderService
        // call AppConfig.memberRepository
        // call AppConfig.memberRepository

        // 하지만 실제 출력값은

        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.orderService

        // 스프링 컨테이너가 싱글톤을 보장해주고 있기 때문이다
    }
}
