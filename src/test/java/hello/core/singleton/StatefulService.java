package hello.core.singleton;

public class StatefulService {

    private int price; // 상태는 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name + price = " + name + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
