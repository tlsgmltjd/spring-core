package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}

// Qualifier로 추가 지정자를 등록해줄 수 있지만 문자열을 작성하여 비교하기 때문에 컴파일 단계에서 확인하기 여렵다.
// -> 어노테이션으로 만든다.