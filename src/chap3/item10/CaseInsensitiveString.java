package chap3.item10;

import java.util.Objects;

public class CaseInsensitiveString {

    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CaseInsensitiveString) {
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
        }
        if (o instanceof String) { // String의 equals에서는 CaseInsensitiveString을 모르기 때문에 매번 값이 달라진다.
            return s.equalsIgnoreCase((String) o);
        }
        return false;
    }
}
