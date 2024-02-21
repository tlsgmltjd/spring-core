package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
// 컴포넌트 스캔을 사용하려면 @ComponentScan 어노테이션을 붙히면 된다.
// 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 빈으로 등록해버리기 때문에 앞서 만든 수동 빈 설정 정보, 테스트 빈 설정도 함께 등록된다.
// excludeFilters를 이용하여 설정 정보는 컴포넌트 스캔의 대상에서 제외한디.
// -> 기존 예제 코드의 유지를 위해. 실무에서는 굳이 안해도됨

// 컴포넌트 스캔은 이름 그대로 @Component 어노테이션이 붙은 클래스를 스프링 빈으로 등록시킨다.
public class AutoAppConfig {
}
