# 아이템 1. 생성자 대신 정적 팩터리 메서드를 고려하라 

- [이펙티브자바 스터디 이슈](https://github.com/java-squid/effective-java/issues/1)

p.8 정적팩터리 메서드가 생성자보다 좋은 장점 5가지

1. 이름을 가질 수 있다. 
2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다. → 존재하는 인스턴스를 그대로 사용해도 된다. 반면 생성자는 매번 객체를 생성해야 한다.  
3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다. → 생성자는 반환타입이 정해져 있다. 
4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다. 
5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

p.12 단점 2가지

1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다. 
2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다

