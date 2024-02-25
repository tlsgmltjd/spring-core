package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void AutowiredOption() {
        new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // Member 는 스프링 빈이 아니기 때문에 의존관계 주입이 안됨
        // 옵션 처리 방법 @Autowired(required = false) / @Nullable / Optional<>

        // @Autowired(required = false) 자동 주입할 대상이 없으면 세터 메서드 자체가 수행이 안된다.
        // @Nullable 호출은 되는데 자동 주입할 대상이 없으면 값에 null이 들어온다
        // Optional<> 스프링 빈이 없으면 값에 Optional.empty가 들어온다.

        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("member = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member2) {
            System.out.println("member2 = " + member2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member3) {
            System.out.println("member3 = " + member3);
        }

        // member2 = null
        // member3 = Optional.empty
    }

}
