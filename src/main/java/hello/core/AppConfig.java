package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    private static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    private static DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}

/**
 *  AppConfig로 관심사를 분리했다. 기존의 OrderService나 MemberService는
 *  추상화에 의존했지만 구현체에도 의존을 했다. 공연에 비유하자면 주인공 배우가 공연도 하고 상대 배역을 캐스팅 하는 등의 책임도 지니고 있다.
 *  이런 공연을 구성하고 배우를 섭외하는 등의 책임을 지니는 공연 기획자가 필요하다. 그것이 Appconfig
 *  AppConfig에서 직접 구현 객체를 생성하고 객체 내부의 의존하는 구현체도 직접 정하여 생성하고 주입해준다.
 *  그렇게 되면 클라이언트는 역할을 실행하는 것에만 집중을 하게 되고 권한이 줄어든다 -> 책임이 명확해진다.
 *
 *  AppConfig가 등장하면서 구현 객체는 역할을 실행하는 것에만 집중할 수 있게 된디.
 *  그리고 프로그램의 흐름은 AppConfig가 가져간다. 예를 들어 OrderService는 의존하고 있는 인터페이스의 메서드를 호출하는 로직이 있지만
 *  정작 어떤 구현 객체들이 주입되어 실행될지는 모른다. 알 필요가 없다.
 *  이렇게 프로그램의 제어의 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 IOC 제어의 역전이라고 한다.
 *
 *  OrderService의 구현체인 OrderSerbiceImpl은 DiscountPolicy 인터페이스에 의존한다.
 *  하지만 OrderSerbiceImpl은 실제로 어떤 구현객체가 사용될지는 꿈에도 모른다.
 *  의존관계는 정적인 클래스 의존관계 다이어그램과 실행 시점에 결정되는 동적인 객체 의존관계 다이어그램을 분리해서 생각해야한다.
 *  실행 시점에 외부에서 실제 구현 객체를 생성하고 클아이언트에 주입하여 의존관계를 연결하는 것을 의존관게 주입 DI라고 한다.
 */
