package chap3;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Jack");
        String s = "jack";

        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));

        System.out.println("===============");

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println(list.contains(cis));
    }
}
