package chap3.item11;

public class AutoValueMain {

    public static void main(String[] args) {
        Item item = Item.builder().price(10000).name("name").build();
        System.out.println(item);
    }
}
