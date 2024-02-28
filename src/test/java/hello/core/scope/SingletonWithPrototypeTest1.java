package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

// 싱글톤, 프로토타입 스코프의 빈을 함께 사용시 문제점

// 프로토타입 빈은 클라이언트가 빈을 조회하면 스프링 컨테이너가 빈을 생성, 의존관계 주입, 초기화 후 반환한다.
// 이렇게 요청시 매번 새로운 객체를 생성하여 반환해주는데

// 만약 싱글톤 빈이 프로토타입 스코프의 빈을 의존관계 주입받아 필드로 가지고 있다면...
// 싱글톤 빈은 보통 스프링 컨테이너 생성 시점에 함께 생성되고 의존관계 주입도 발생한다.
// 그렇기 때문에 싱글톤 빈은 의존관계 주입 시점에 프토로타입의 빈을 요청하고 필드에 가지고 있는다!

// 이렇게 되면 여러 클라이언트가 싱글톤 빈을 조회하여 필드의 프로토타입의 빈을 사용한다면 항상 같은 객체를 사용할 수 밖에 없다
// 이건 좀 예상 밖인 / 클라이언트가 요청 할 때마다 다른 프로토타입의 빈을 마주하고 싶었는데
// 싱글톤 빈이 가지고 있는(정확히는 참조값) 프토로타입의 빈은 이미 과거에 싱글톤 빈이 의존관계 주입 시 주입되어 보관되고 있는 빈이고
// 클라이언트가 요청할 때마다 항상 같은 객체를 주는 싱글톤 빈 안에 있는 프로토타입의 빈은
// 사용할 때마다 새로 생성되지 않고 원래 있던 같은 프로토타입 빈이 나온다.

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

        ac.close();
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBea1 = ac.getBean(ClientBean.class);
        ClientBean clientBea2 = ac.getBean(ClientBean.class);

        int c1 = clientBea1.logic();
        assertThat(c1).isEqualTo(1);

        int c2 = clientBea2.logic();
        assertThat(c2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
//        @Autowired
//        private final PrototypeBean prototypeBean; // 생성 시점에 이미 주입되어서 여러 클라이언트에서 요청해도 같은 객체

//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;

        // 싱글톤과 동시 사용 문제를 직접 AC을 주입받아서 프로토타입 빈이 필요할 때마다 주입시켜서 해결할 수도 있지만
        // 지금 필요한 기능은 지정한 빈을 컨테이너에서 찾아주는 기능만 필요하므로 복잡한 기능이 있는 AC의 DI보다 DL정도의 기능이 적합하다.

        // ObjectProvider의 getObject() 메서드를 사용해서 항상 새로운 프로토타입 빈이 생성된다!
        // getObject() 을 호출하면 스프링 컨테이너를 통해 해당 빈을 찾아서 반환해준다 (DL)

        // ObjectFactory 기능이 단순하다 getObject() 메서드만 있음 / 스프링에 의존적
        // ObjectProvider ObjectFactory를 상속, 옵션, 스트림 처리 같은 편의 기능이 많다 / 스프링에 의존적

        /// JSR330 Provider
        // 자바 표준인 Provider를 사용하는 방법이다.
        // provider.get() 메서드를 사용해서 항상 새로운 프로토타입의 빈이 생성된다.
        // get() 를 사용하면 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다.
        // 자바 표준이여서 스프링에 의존적이지 않고 단위테스트, 모킹에도 수월하다

        // implementation 'jakarta.inject:jakarta.inject-api:2.0.1' 해당 라이브러리를 땡겨야한다.

        // @Lockup 어노테이션을 쓸 수도 있다.

        @Autowired
        private Provider<PrototypeBean> provider;

        public int logic() {
            PrototypeBean prototypeBean = provider.get();

            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            this.count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init: " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
