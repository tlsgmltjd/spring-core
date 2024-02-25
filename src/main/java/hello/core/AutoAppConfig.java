package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 탐색 시작 위치 설정
// basePackages: 탐색을 시작할 패키지의 위치를 지정한다. 해당 패키지를 포함해 하위 모든 패키지를 탐색한다.
// basePackageClasses: 지정한 클래스를 탐색 시작 위치로 지정한다. ex. AutoAppConfig.class -> "hello.core"
// Default? -> 해당 패키지를 탐색 시작 위치로 지정
// 권장하는 방법은 최상단에 설정 정보 클래스를 위치시켜주는 것이다. (패키지 지정 생략)

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
// 컴포넌트 스캔을 사용하려면 @ComponentScan 어노테이션을 붙히면 된다.
// 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 빈으로 등록해버리기 때문에 앞서 만든 수동 빈 설정 정보, 테스트 빈 설정도 함께 등록된다.
// excludeFilters를 이용하여 설정 정보는 컴포넌트 스캔의 대상에서 제외한디.
// -> 기존 예제 코드의 유지를 위해. 실무에서는 굳이 안해도됨

// 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 루트에 두는것이 관례이다.
// @SpringBootApplication안에 @ComponentScan이 들어있다!

// 컴포넌트 스캔은 이름 그대로 @Component 어노테이션이 붙은 클래스를 스프링 빈으로 등록시킨다.
public class AutoAppConfig {
}
