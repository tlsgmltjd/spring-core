package hello.core.singleton;

public class SingletonService {

    // static 영역에 미리 객체를 딱 1개 설정해둔다.
    // 이 객체 인스턴스가 필요하면 static method인 getInstance()를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
    // 생성자를 private로 막아두어 외부에서 객체 인스턴스를 생성하는 것을 막는다.

    // 하지만 문제점이 많다
    // 구현하는 코드가 많이 들어간다
    // 의존 관계상 클라이언트가 구현체를 의존한다. ex. SingletonService.getInstance() DIP 위반
    // 클라이언트가 구현체에 의존해서 OCP 위반 우려
    // 테스트 하기 어렵고 내부 속성을 변경하거나 초기화 하기 어렵다
    // private constructor로 자식 클래스를 만들기 어렵다
    // 유연성이 떨어지며 안티패턴으로 불리기도 한다.

    // 하지만22 스프링 프레임워크는 싱글톤 패턴의 단점을 해결하고 장점을 살려 사용한다.

    // 스프링 컨테이너는 객체(빈)을 하나만 생성하여 관리해준다
    // 스프링 컨테이는 싱글톤 컨테이너 역할을 하며 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스터라고 한다.
    // 스프링 컨테이너의 이런 기능 덕분에 싱글톤 패턴의 단점을 없애버리면서 객체를 싱글톤 상태로 유지할 수 있다.
    // 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 되며 DIP OCP 테스트, private 생성자로부터 자유롭게 싱글톤을 사용할 수 있다.

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
