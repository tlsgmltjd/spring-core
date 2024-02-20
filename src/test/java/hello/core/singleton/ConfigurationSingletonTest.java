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

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean);
        // bean = hello.core.AppConfig$$SpringCGLIB$$0@46b61c56 ??
        // 스프링이 CGLIB라는 바이트 코드 조작 라이브러리를 사용해서 Appconfig를 상속받은 임의의 클래스를 만들고 스프링 빈으로 등록한 것이다!!

        // 이 임의의 클래스가 싱글톤이 되도록 보장해준다.

        // CGLIB 예상 코드 로직
        // @Bean이 붙은 메서드마다 이미 스프링 빈이 존재한다면 존재하는 빈을 반환하고
        // 스프링 빈이 존재하지 않는다면 인스턴스를 생성하여 스프링 빈으로 등록시키고 반환하는 코드가 동적으로 만들어진다

        // 싱글톤을 보장해주는 @Configuration을 제거하고 @Bean만 등록한다면?
        // CGLIB 기술을 사용하지 않는다.
        // 싱글톤이 적용되지 않는다.
        // 그냥 new해서 넣는거랑 다를게 없다. 스프링 컨테이너가 관리해주는 빈이라고 말할 수 없다.

        // @Bean만 해도 빈 등록이 되지만 싱글톤이 적용되지 않는다.
    }
}
