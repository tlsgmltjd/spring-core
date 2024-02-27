package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

// 빈 스코프
// 스프링 빈이 스프링 컨테이너에서 관리되는 범위, 존재할 수 있는 범위이다.
// 일반적으로 빈을 등록하면 싱글톤 스코프가 적용되어있으며 스프링 컨테이너 시작과 함께 생성되어 종료때 까지 유지된다.

// 싱글톤 - default, 스프링 컨테이너의 시작과 끝까지 유지되는 가장 넓은 범위의 스코프
// 프로토타입 - 스프링 컨테이너는 빈의 생성, 의존관계 주입, 생성 콜백까지만 관여하고 더는 관리하지 않는다.
// request - 웹 요청이 들어오고 나갈때 까지 유지되는 스코프
// session - 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프
// application - 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

public class SingletonTest {

    // 싱글톤 스코프
    // 여러 클라이언트가 빈을 요청해도 항상 똑같은 인스턴스를 반환하도록 보장하는 스코프이다.
    // 컨테이너의 시작과 끝가지 빈이 유지된다.

    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);
        Assertions.assertThat(bean1).isSameAs(bean2);
        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("====================== SingletonBean.destroy");
        }
    }
}
