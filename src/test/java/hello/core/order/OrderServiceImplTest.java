package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder() {
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);

        // 만약 이렇게 실행하려고 했을때 세터 인젝션이라면 런타임 오류가 터진다(NPE)
        // 하지만 생성자 인젝션이라면 컴파일 단계에서 필수로 넣어야하는 의존관계를 알려주기 때문에 더 안전하다. (아래와 같다)

        MemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "userA", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

        // *생성자 인젝션을 사용하자*

        // 의존관계 주입은 처음에 주입 후 변경될 일이 거의 없다. 그리고 세터 인젝션을 사용하면 public으로 세터 메서드를 다 열어야하는 위험이 있다.
        // 생성자 인젝션은 객체 생성시 딱 1번만 호출되므로 이후에 의존관계가 변경될 일이 없다. ("불변"하게 설계가능)
        // DI 프레임워크에 의존하지 않고 순수한 자바 코드의 특성을 살리고 단위 테스트에도 용의하다.
        // 생성자 인젝션을 사용한다면 DI된 객체의 필드를 final 키워드를 사용할 수 있다. (생성자에서 넣어주지 않는 실수를 하더라도 컴파일 오류가 발생해서 안전)
        // 컴파일 오류는 세상에서 가장 좋은 오류다
    }
}