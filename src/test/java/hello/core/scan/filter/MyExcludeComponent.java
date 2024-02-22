package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // @Target: 어디에 붙냐, TYPE: 클래스 레벨
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
