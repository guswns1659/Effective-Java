# 아이템 10. equals는 일반 규약을 지켜 재정의하라

p.59. 리스코프 치환 원칙(LisKov substitution principle)에 따르면 어떤 타입에 있어 중요한 속성이라면 그 하위 타입에서도 마찬가지로 중요하다. 따라서 그 타입의 모든 메서드가 하위 타입에서도 똑같이 잘 동작해야 한다. 이는 앞서의 Point의 하위 클래스는 정의상 여전히 Point이므로 어디서든 Point로써 활용될 수 있어야 한다를 격식있게 표현한 말이다. 

p.63 float와 double을 제외한 기본 타입 필드는 == 연산자로 비교하고, 참조 타입 필드는 각각의 equals 메서드로, float와 double 필드는 각각 정적 메서드인 Float.compare(float, float)와 Double.compare(double, double)로 비교한다. 

p.66 핵심 정리. 꼭 필요한 경우가 아니면 equals를 재정의하지 말자. 많은 경우에 Object의 equals가 여러분이 원하는 비교를 정확하게 수행해준다.
