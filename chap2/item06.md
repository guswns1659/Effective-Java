# 아이템 6. 불필요한 객체 생성을 피하라 

p.31 다음 코드는 하지 말아야 할 극단적인 예이니 유심히 한번 살펴보자 

```java
String s = new String("bikini");
```

이 문장은 실행될 때마다 String 인스턴스를 새로 만든다. 이유는 같은 문자열이 있어도 힙 영역에 새로운 인스턴스가 만들어진다. 반면에 "bikini"로만 생성할 경우 String Constant Pool에 있는 같은 문자열을 참조한다. 

![image](https://user-images.githubusercontent.com/55608425/94984082-3f8d1f80-0583-11eb-9bbd-02c0452f1266.png)

p.32 생성 비용이 아주 비싼 객체도 있다. 이런 비싼 객체가 반복해서 필요하다면 캐싱하여 재사용하길 권한다. 

> 코드 6-1 성능을 훨씬 더 끌어올릴 수 있다!
```java
static boolean isRomanNumeral(String s) {
    return s.matchs.("^(?=.");
}
```

p.32 위 방식의 문제는 String.matches 메서드를 사용하는데 있다. 정규표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만 성능이 중요한 상황에서 반복해 사용하기엔 적합하지 않다. 이 메서드가 내부에서 만드는 Pattern 인스턴스는 한번 쓰고 버려져서 곧바로 가비지 컬렉션 대상이 된다. Pattern은 입력받은 정규표현식에 해당하는 유한상태 머신을 만들기 때문에 인스턴스 생성 비용이 높다. 성능을 개선하려면 Pattern 인스턴스를 클래스 초기화 과정에서 직접 생성해 캐싱해두고, 나중에 isRomanNumeral 메서드가 호출될 때마다 이 인스턴스를 재사용한다. 

> 코드 6-2 값비싼 객체를 재사용해 성능을 개선한다.
```java
public class RomanNumerals {
    private static fianl Pattern ROMAN = Pattern.compile("^(?=.");
    
    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches;
    }
}   
```

p.33 두번째 문단. 객체가 불변이라면 재사용해도 안전함이 명백하다. 하지만 훨씬 덜 명확하거나, 심지어 직관에 반대되는 상황도 있다. 어댑터는 실제 작업은 뒷단의 객체에게 위임하고, 자신은 제2의 인터페이스 역할을 해주는 객체다. 어댑터는 뒷단 객체만 관리하면 된다. 즉, 뒷단 객체 외에는 관리할 상태가 없으므로 뒷단 객체 하나당 어댑터 하나씩만 만들어지면 충분하다. 

## 어댑터 패턴 정리 
일상 생활 내 어댑터 사용 예시는 콘센트를 들 수 있다. 한국 표준 플러그는 일본 콘센트에 바로 사용할 수 없어 일본용 어댑터를 끼운 뒤 사용할 수 있다. 이와 같이 어댑터는 상대가 필요로 하는 인터페이스로 특정 객체의 인터페이스를 바꿔준다. 

- Duck 인터페이스와 콘크리트 클래스

```java
public interface Duck {
    public void quack();
    public void fly();
}

public class JackDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("JackQuack");
    }
    
    @Override
    public void fly() {
        System.out.println("Duck, fly!!");
    }
}
```

- Turkey 인터페이스와 콘크리트 클래스

```java
public interface Turkey {}
    public void gobble();
    public void fly();
}

public class JackTurkey implements Turkey {
    @Override 
    public void gobble() {
        System.out.println("JackGobble");
    }
    @Override 
    public void fly() {
        System.out.println("Turkey! fly!!");
    }   
}
```

- Duck 인터페이스를 사용해야할 상황인데 없어서 대신 Turkey를 사용해야 한다면. 위 설명처럼 일본 콘센트를 써야하는데 없어서 한국 콘센트를 써야하는 상황이라면 한국 콘센트에 일본 콘센트용 어댑터를 껴서 사용한다. 즉, Turkey객체에 Duck 인터페이스용 어댑터를 껴서 사용한다. 

```java
 public class TurkeyAdapter implements Duck {

          Turkey turkey;

          public TurkeyAdapter(Turkey turkey) {
                   this.turkey = turkey;
          }

          @Override
          public void quack(){ 
                   turkey.gobble();
          }

          @Override
          public void fly() {
                   turkey.fly();
          }

 }
```

p.33 4번째 문단. 불필요한 객체를 만들어내는 또 다른 예로 오토박싱을 들 수 있다. 

> 코드 6-3 끔직이 느리다. 객체가 만들어지는 위치를 찾았는가? 

```java
private static long sum() {
    Long sum = 0;
    for (long i = 0; i <= Integer.MAX_VALUE; i++) {
        sum += i;
    }
    return sum;
}
```

p.34 2번째 문단. 이번 아이템을 "객체 생성은 비싸니 피해야 한다"로 오해하면 안 된다. 특히나 요즘의 JVM에서는 별다른 일을 하지 않는 작은 객체를 생성하고 회수하는 일이 크게 부담되지 않는다. 프로그램의 명확성, 간결성, 기능을 위해서 객체를 추가로 생성하는 것이라면 일반적으로 좋은 일이다. 

# 이슈에 등록된 질문과 답변

@david215 
>아이템 마지막 부분에 데이터베이스 커넥션 객체를 예시로 들며 객체 풀을 직접 관리하는 것은 안 좋다고 하는데 그 이유를 잘 모르겠네요.

한국어 판은 그 다음 줄에 이런식으로 설명이 있습니다.
>DB 연결 같은경우 생성비용이 비싸서 재사용하는 경우가 있다.

객체 풀은 객체를 필요로 할때 풀에 요청을 하고, 반환하고 일련의 작업을 수행하는 디자인 패턴으로 많은 수의 인스턴스를 생성할때 혹은 무거운 오브젝트를 매번 인스턴스화 할때 성능 향상을 위해서 사용합니다.
문제는 객체 풀이 코드를 헷갈리게 만들고 잘못 설정한 경우 메모리 사용량을 늘리고 성능을 떨어뜨립니다. 게다가 요즘 JVM의 가비지 컬렉터는 상당히 잘 최적화되어서 가벼운 객체용으로 다룰 때는 사용하지 않은게 더 낫다고 하네요. 
