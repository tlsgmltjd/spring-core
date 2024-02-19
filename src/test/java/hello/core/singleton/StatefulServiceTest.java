package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    // 싱글톤 패턴에서는 객체 인스턴스를 하나만 생성하고 여러 클라이언트끼리 공유하는 방식이기 때문에
    // 싱글톤 객체는 상태를 유지하게 설계하면 안된다
    // 무상태로 설계해야한다
    // 특정 클라이언트에 의존적인 필드가 있으면 안된다
    // 값을 변경할 수 있는 필드가 있으면 안된다
    // 가급적 읽기만 가능해야한다
    // 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터 ThreadLocal 등을 사용해야한다

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService bean1 = ac.getBean(StatefulService.class);
        StatefulService bean2 = ac.getBean(StatefulService.class);

        // ThreadA(가정) : A 사용자가 10000원 주문
        bean1.order("userA", 10000);

        // ThreadA : B 사용자가 20000원 주문
        bean2.order("userB", 20000);

        // ThreadA : A 사용자가 주문 금액 조회
        int price = bean1.getPrice();

        Assertions.assertThat(bean1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService service() {
            return new StatefulService();
        }
    }

    // 공유 필드는 레전드 조심해야한다
    // 스프링 빈은 항상 무상태로 설계하자
}