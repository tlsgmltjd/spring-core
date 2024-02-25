package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 필수값 생성자
public class OrderServiceImpl implements OrderService {

    // 인터페이스에만 의존하게 DIP를 준수했다.
    // AppConfig를 사용해서 구현체를 바꿔도 해당 클래스는 어떤 구현체가 들어옴
    // 해당 클래스는 그냥 역할만 보고 로직을 실행하기만 하면 된다. OCP를 준수했다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    } // == @RequiredArgsConstructor 롬복이 어노테이션 프로세서라는 기능을 활용하여 컴파일 시점에 생성자 코드를 자동으로 생성해준다

    // 생성자 주입
    // 불변, 필수 의존관계
    // 클래스에 생성자가 딱 한개 있으면 @Autowired를 생략해도된다.

    // 수정자 주입 (setter 메서드를 작성해서 빈을 주입할 수 있다.)
    // 선택(@Autowired(required = false)), 변경 가능성이 있는 의존관계에 사용

    // 필드주입 (warning: Field injection is not recommended)
    // 스프링이 없는 상태에서 의존관계 주입이 되니까 테스트를 할 수가 없다.
    // DI 프레임워크가 없으면 아무것도 할 수가 없다.
    // 수동으로 의존관계를 주입하려면 세터를 열어야하는데 그럴바엔 세터 주입 하는게 낫다.
    // -> 애플리케이션 코드와 관계없는 테스트 코드를 작성할땐 사용해도된다. Config 클래스(스프링에서만 쓸거)

    // 일반 메서드 주입
    // 일반적으로 잘 사용하지 않는다
//    @Autowired void init(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        /**
         * SRP 잘 지킴, 할인과 관련된 부분을 DiscountPolicy로 위임시켜서 책임을 분리시킴
         * 이렇게 설계하지 않으면 할인과 관련된 변경이 있으면 OrderService를 고쳐야함
         */
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
