# 아이템 2. 생성자에 매개변수가 많다면 빌더를 고려하라

- [https://github.com/java-squid/effective-java/issues/2](https://github.com/java-squid/effective-java/issues/2)

p.16 자바 빈즈 패턴에서는 객체 하나를 만들려면 메서드를 여러 개 호출해야 하고, 객체가 완전히 생성되기 전까지는 일관성(consistency)이 무너진 상태에 놓이게 된다. 

여기서 일관성이란? 객체의 일관성은 클라이언트가 일관된 접근 방식을 이용하여 일관된 접근 방식을 제공하는걸 의미합니다. 일관성이 없으면 접근방식(변수에 직접 사용, 엑세스 함수 사용)에 주의력을 소진하게 되어, 뒤에 있는 버그가 있을 경우에 찾기 힘들어집니다

p.22 생성자나 정적 패터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는 게 더 낫다. 매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다.

## 롬복 빌더와 상속 관계에 대해서

 빌더 패턴에서 상속은 사실 다른 상속관계와 크게 다르지 않습니다. 피자 예제처럼 피자 → NY피자, 칼초네피자로 상속하고 오버라이딩할 메서드는 build()(인스턴스 생성), self()를 구현하면 됩니다. 여기서 self는 시뮬레이트한 셀프 타입 관용구라하여 파이썬, 스칼라에서 있는 self타입을 구현을 해야합니다. Java에선 self타입이 없거든요.
 
 실제 빌더를 구현하지 않고 롬복을 사용할 때는 다릅니다. 롬복에서의 `@Builder` 는 필수매개변수가 없고 선택적 매개변수만 존재합니다. 그 뿐만 아니라 재귀적 타입한정을 이용한 빌더 생성을 하지 않습니다. 최소한의 기능만을 사용을 하겠다는 겁니다. 
 
 롬복을 이용한 빌더 상속은 부모 클래스에서 자식 클래스를 넘겨줄 때는 큰 문제는 없습니다.
 
 ```java
 @Getter
 @AllArgsConstructor
 public class Parent {
     private final String parentName;
     private final int parentAge;
 }
  
 @Getter
 public class Child extends Parent {
     private final String childName;
     private final int childAge;
  
     @Builder
     public Child(String parentName, int parentAge, String childName, int childAge) {
         super(parentName, parentAge);
         this.childName = childName;
         this.childAge = childAge;
     }
 ```
 
 그러나, 만약 부모 클래스에서 빌더를 생성한다면,
 
 ```java
 @Getter
 @AllArgsConstructor
 public class Parent {
     private final String parentName;
     private final int parentAge;
 
 	  @Builder
     public Parent(String parentName, int parentAge) {
         this.parentName = parentName;
         this.parentAge = parentAge;
     }
 }
  
 @Getter
 public class Child extends Parent {
     private final String childName;
     private final int childAge;
  
     @Builder
     public Child(String parentName, int parentAge, String childName, int childAge) {
         super(parentName, parentAge);
         this.childName = childName;
         this.childAge = childAge;
     }
 ```
 
 이런 경우에는 빌더이름이 중복이 되어 컴파일 에러가 발생합니다. 이름이 중복되어 나는 컴파일 에러 같은경우
 자식클래스에서 빌더의 이름을 바꿔주면 됩니다. 클라이언트 코드에선 자식클래스를 생성 시 `builderMethodName` 에서 지정한 빌더를 사용합니다.
 
 ```java
 @Getter
 public class Child extends Parent {
     private final String childName;
     private final int childAge;
     
     @Builder(builderMethodName = "childBuilder")
     public Child(String parentName, int parentAge, String childName, int childAge) {
         super(parentName, parentAge);
         this.childName = childName;
         this.childAge = childAge;
     }
 }
 
 // 클라이언트 코드
 Child child = Child.childbuilder()
   .parentName("Cloud")
   .parentAge(345)
   .childName("Sunny")
   .childAge(29)
   .build();
 ```
 
 만약 자신이 롬복 **1.18버전**을 쓰신다면 `@SuperBuilder` 를 사용하는것도 나쁘지 않습니다. 
 
 `@SuperBuilder`는 부모, 자식클래스 구분없이 빌더를 만들어 클라이언트 코드에 사용이 가능합니다.
 
 ```java
 @Getter
 @SuperBuilder
 public class Parent {
     // same as before...
  
 @Getter
 @SuperBuilder
 public class Child extends Parent {
    // same as before...
 
 // 클라이언트 코드
 Child child = Child.builder()
   .parentName("Cloud")
   .parentAge(345)
   .childName("Sunny")
   .childAge(29)
   .build(); 
 ```
 
 단, `@Builder` 와 같이 쓸 수없기 때문에 주의 해야 합니다.