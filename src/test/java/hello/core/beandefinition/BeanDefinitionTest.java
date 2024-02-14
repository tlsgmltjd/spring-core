package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // 스프링이 다양한 빈 설정 방식을 지원할 수 있는 이유는 BeanDefinition 이라는 추상화 덕분이다.
    // 역할과 구현을 나눈것.
    // 자바 코드를 읽어 BeanDefinition을 만든다 / XML을 읽어 BeanDefinition을 만든다 ...
    // 스프링 컨테이너는 자바코드인지 XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.

    // BeanDefinition은 빈 설정 메타데이터이다.
    // ApplicationContext를 구현하는 구현체들인 AnnotationConfigApplicationContext, GenericXmlApplicationContext은
    // XXXBeanDefinitionReader 라는 메서드가 있다 해당 메서드로 빈 설정 정보를 읽어 Beandefinition을 생성한다.

    // 이렇게 Beandefinition을 사용한 추상화 덕분에 스프링은 다양한 빈 설정 방식을 유연하게 지원할 수 있게 되었다.

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName);
                System.out.println("beanDefinition = " + beanDefinition);
            }
        }
    }
}
