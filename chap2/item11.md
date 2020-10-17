# 아이템 11. equals를 재정의하려거든 hashCode도 재정의하라 

p.69 2번째 문단. hashCode를 다 구현했다면 이 메서드가 동치인 인스턴스에 대해 똑같은 해시코드를 반환할지 자문해보자. 그리고 여러분의 직관을 검증할 단위 테스트를 작성하자(equals와 hashCode 메서드를 AutoValue로 생성했다면 건너뛰어도 좋다.)

## AutoValue
### 무엇인가? 
[구글에서 지원하는 라이브러리](https://github.com/google/auto/tree/master/value)로 POJO를 생성할 때 필요한 equals(), hashcode(), toString() 메서드를 쉽게 생성해준다. 이렇게 하면 필요한 재정의가 적용되고 수동 코딩으로 발생할 수 있는 잠재적 오류를 방지할 수 있습니다. 

- [AutoValue 코드 생성기를 사용하여 POJO(Plain Old Java Object) 생성](https://cloud.google.com/solutions/e-commerce/patterns/generating-pojos?hl=ko)