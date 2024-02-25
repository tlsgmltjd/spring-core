package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
//        helloLombok.getName();
//        helloLombok.setAge(12);
        // 게터, 세터, 생성자들을 클래스에 어노테이션만 붙혀서 구현가능

        System.out.println(helloLombok);
    }
}
