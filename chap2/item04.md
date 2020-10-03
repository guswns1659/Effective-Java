# 아이템 4. 인스턴스화를 막으려거든 private 생성자를 사용하라

p.26 정적 멤버만 담은 유틸리티 클래스는 인스턴스로 만들어 쓰려고 설계한 게 아니다. 
- 유틸리티 클래스는 필요한 메서드만 모아 놓은 객체이기 때문에 클래스의 역할이 아니다. 클래스란 객체의 공통적인 속성을 가지고 객체를 구분하는 틀이기 때문이다. 

p.26 private 생성자를 추가하면 클래스의 인스턴스화를 막을 수 있다. 
- 생성자를 만들지 않으면 컴파일러가 자동으로 기본 생성자를 만들기 때문에 private 생성자를 만들면 인스턴스 만들 방법을 완전히 차단한다.