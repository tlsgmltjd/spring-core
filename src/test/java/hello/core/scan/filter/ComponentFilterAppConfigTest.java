package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    // includeFilters: 컴포넌트 스캔의 대상을 추가로 등록
    // excludeFilters: 컴포넌트 스캔에서 제외할 대상 등록
    // MyIncludeComponent, MyIncludeComponent 어노테이션을 만들어서 빈을 조건적으로 등록하였다.
    // type은 ANNOTATION이 Default이기 때문에 생략해도 된다.
    static class ComponentFilterAppConfig {
    }
}

// 중복 등록과 충돌

// 자동 빈 등록 - 자동 빈 등록
// 컴포넌트 스캔에 의해 자동으로 등록되는 스프링 빈의 이름이 같을 경우
// ConflictingBeanDefinitionExcepion 발생

// 수동 빈 등록 - 자동 빈 등록
// 이 경우에는 수동 빈 등록이 우선권을 가진다. 수동 빈이 자동 빈을 오바라이딩 해버린다.
// 대부분의 상황은 의도적이지 않고 여러 설정들이 꼬여서 이런 상황이 발생한다. 정말 잡기 힘든 애매한 버그
// 최근 스프링은 수동 - 자동 충돌시 오류가 발생하도록 기본 값을 바꾸었다.

// 개발을 할땐 애매한 상황을 만들지 않는게 중요하다.
// 코드가 좀 더워지더라도 명확하고 한 눈에 들어오는 코드가 더 좋다.