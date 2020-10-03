# 아이템 5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

p.29 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다. 

- 아래 방식은 사전을 단 하나만 사용한다고 가정한다는 점에서 그리 훌륭해보이지 않다.

> 정적 유틸리티를 잘못 사용한 예 - 유연하지 않고 테스트하기 어렵다.

```java
public class SpellCheck {
    private static final Lexicon dictionary = ...;
    private SpellCheck() {} // 객체 생성 방지
    
    public static boolean isValid(String word) {}
    public static List<String> suggestions(String typo) {}
}
```

p.29 클래스(SpellCheck)가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원(dictionary)를 사용해야 한다. 이 조건을 만족하는 간단한 패턴이 있으니 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식이다. 

> 의존 객체 주입은 유연성과 테스트 용이성을 높여준다.

```java
public class SpellCheck {
    private static final Lexicon dictionary;
    private SpellCheck(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    } 
    
    public static boolean isValid(String word) {}
    public static List<String> suggestions(String typo) {}
}
```

- 의존 객체 주입은 불변을 보장하여 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있기도 하다. 
- 이 패턴의 쓸만한 변형으로 생성자에 자원 팩터리를 넘겨주는 방식이 있다. 팩터리란 호출할 때마다 특정 타입의 인스턴스를 반복해서 만들어주는 객체를 말한다. 자바 8에서 소개한 Supplier<T> 인터페이스가 팩터리를 표현한 완벽한 예다. 

# 이슈에 등록된 질문과 답변

> 1. java8에서 나온 Supplier라는 인터페이스에 대해 간단하게 나마 설명해주시면 감사하겠습니다!

Supplier 인터페이스만 분리해서 보기 보다는 전체적인 functional interface의 구조를 보면 자연스럽게 이해가 될 거 같네요. [java.util.function](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/function/package-summary.html)에서 가장 대표적인 개념들을 꼽아보자면
- `Function<T, R>` - 타입 T의 인풋을 받아서 타입 R의 아웃풋을 반환
- `Consumer<T>` - 타입 T의 인풋을 받고 아웃풋 x
- `Supplier<T>` - 인풋 x,  타입 T의 아웃풋을 반환
- `Predicate<T>` - 타입 T의 인풋을 받아 boolean 값을 반환

> 2. 한정적 와일드 타입을... 이건 아마도 `Supplier<? extends Tile>` 코드를 설명하는 것 같은 데 제가 이해한 바로는 `Tile` 클래스를 상속받는 클래스라면 모든지 가능하다? 라는 의미같은 데, 아마도 1번의 설명과 맞물려서 ... 한번 이야기해보면 좋을듯 합니다.

이해하신 것이 맞습니다. Supplier를 사용해 의존 객체 주입을 사용할 때 한정적 와일드 타입을 사용함으로써 하위 클래스의 객체를 주입하는 것을 가능하게 해주기 위해 사용한다고 이해하면 되겠네요.

## Q) 스프링에서 필드 주입을 왜 deprecated하는가?
- A인터페이스가 있고, 이를 구현한 B,C,D 구현체가 있다고 가정할 때, A 인터페이스를 필드에 선언해놓고 필드 주입한다면, A만 받을 수 있음.
- 반면에 생성자 주입을 하면, 선언은 A 인터페이스로 하고, B,C,D 중 원하는 것으로 주입 받을 수 있음.