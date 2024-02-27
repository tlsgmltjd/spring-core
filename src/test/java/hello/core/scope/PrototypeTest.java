package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    // 프로토타입 스코프
    // 빈을 요청할 때마다 항상 새로운 인스턴스를 생성해서 반환해준다.
    // 스프링 컨테이너는 프로토타입 빈을 생성, 의존관계 주입, 초기화까지만 관리하고
    // 빈을 클라이언트에 반환한 후 해당 빈을 더 이상 관리하지 않는다.
    // 그래서 @PreDestroy 같은 소멸 메서드가 동작하지 않는다. (클라이언트의 몫)

    // 요청마다 매번 새롭게 생성한다 / 스프링 컨테이너는 생성, 의존관계 주입, 초기화 까지만 관여
    // 소멸 메서드가 실행되지 않음 / 프로토타입 빈은 조회한 클라이언트가 관리해야함 소멸 메서드도 필요시 클라이언트에서 호출해야한다.

    @Test
    void prototypeBeanFind() {
        // 참고로 PrototypeBean.class 이렇게 등록하면 해당 클래스를 컴포넌트 스캔의 대상으로 등록해버려서 빈으로 등록이 된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find p1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find p2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        Assertions.assertThat(bean1).isNotSameAs(bean2);
        ac.close(); // 스프링 컨테이너가 종료되었지만 @PreDestroy(소멸) 메서드가 실행되지 않는다.
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("====================== PrototypeBean.destroy");
        }
    }
}
