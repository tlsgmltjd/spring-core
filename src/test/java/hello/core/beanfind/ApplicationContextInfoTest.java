package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력")
    void findAllBean() {
        // ac.getBeanDefinitionNames() 스프링 컨테이너에 등록된 모든 빈 이름을 조회한다.
        // ac.getBean(BeanName) 빈 이름으로 빈 인스턴스를 조회한다.
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + ", " + "bean = " + bean);
        }
    }

    @Test
    @DisplayName("모든 애플리케이션 빈 출력")
    void findAllApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            // BeanDefinition: 빈에 대한 메타데이터 정보이다.
            // ac.getBeanDefinition(BeanName): 빈 이름으로 BeanDefinition 조회.
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // Role - ROLE_APPLICATION: 직접 등록한 빈
            // Role - ROLE_INFRASTRUCTURE: 스프링 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + ", " + "bean = " + bean);
            }
        }
    }
}
