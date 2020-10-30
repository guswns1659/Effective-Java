package chap3.item13;

public class HashTable {

    private Entry[] buckets;

    /** 내부 클래스이기 때문에 해당 필드에 바로 접근하기 위해 static을 사용?
     */
    private static class Entry {
        final Object key;
        Object value;
        Entry next;

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    Entry deepCopy() {
        String key = "key";
        String value = "value";
        Entry result = new Entry(key, value, null);

        /** for문을 제대로 활용
         *  1. 초기화, 2. 조건문 3. 로직수행 4. 증감
         */
        for (Entry p = result; p.next != null; p = p.next) {
            p.next = new Entry(p.next.key, p.next.value, p.next.next);
        }
        return result;
    }
}
