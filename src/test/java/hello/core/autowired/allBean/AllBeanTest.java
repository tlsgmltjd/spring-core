package hello.core.autowired.allBean;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        int discountPrice = discountService.discount(member, 20000, "fix");
        int discountPrice2 = discountService.discount(member, 50000, "rate");

        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(discountPrice).isEqualTo(1000);
        Assertions.assertThat(discountPrice2).isEqualTo(5000);
    }

    static class DiscountService {

        // Map 자료형으로 모든 DiscountPolicy 빈을 주입 받는다. <빈이름, 빈객체> fix, rate
        // 클라이언트가 빈의 이름을 포함하여 DiscountService의 discount 메서드를 실행시키면
        // Map에서 해당 빈의 이름으로 빈을 조회하여 찾은 빈의 discount 메서드를 실행하여 결과값을 반환한다.

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode+"DiscountPolicy");
            return discountPolicy.discount(member, price);
        }
    }
}
