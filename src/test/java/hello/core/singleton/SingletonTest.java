package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 업는 순수한 DI 컨테이너")
    void pureContainer() {
        // 순수한 DI 컨테이너는 요청할 때마다 객체를 계속 새로 생성한다. 메모리 낭비가 심하다
        // 해결 방안 -> 객체 하나를 생성 후 생성된 객체 인스턴스를 공유하도록 생성하면 된다 = 싱글톤

        // 싱글톤 패턴
        // 클래스 인스턴스가 딱 1개만 생성되도록 보장하는 디자인 패턴이다.

        AppConfig appConfig = new AppConfig();
        // 1. 조회 호출할 때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 호출할 때마다 객체 생성
        MemberService memberService2 = appConfig.memberService();

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        assertThat(singletonService1).isSameAs(singletonService2);

        // isSameAs ==
        // isEqualTo .equals
    }
}
