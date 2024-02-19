package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    // 인터페이스에만 의존하게 DIP를 준수했다.
    // AppConfig를 사용해서 구현체를 바꿔도 해당 클래스는 어떤 구현체가 들어옴
    // 해당 클래스는 그냥 역할만 보고 로직을 실행하기만 하면 된다. OCP를 준수했다.
    private final MemberRepository memberRepository ;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
