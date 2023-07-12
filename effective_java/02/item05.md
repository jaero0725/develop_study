# 아이템5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

대부분의 클래스는 여러 리소스에 의존한다. 이 책에서는 SpellShecker와 Dictionary를 예로 들고 있다.. 
SpellCheck가 Dictionary를 사용하고, 이를 의존하는 리소스 또는 의존성이라고 부른다. 이때 아래와 같이 SpellChecker를 구현하는 경우가 있다.

### 부적절한 구현
``` java
    public class SpellChecker {
        privte static final Lexion dictionary = ...;

        private SpellChecker() {} // 객체 생성 방지
        public static SpellChecher INSTANCE = new SpellChecker(...); //static으로 생성

        public boolean isValid(String word) { ... }
        public List<String> suggertions(String type) {...}
    }
```

### 적절한 구현
```java
public class SpellChecker {
        privte static final Lexion dictionary = ...;

        // 의존성 주입
        public SpellChecker(Lexion dictionary) {
            this.dictionary = Objects.requireNonNull(dictionar);

        public boolean isValid(String word) { ... }
        public List<String> suggertions(String type) {...}
    }
```

- 불변을 보장하여 (같은 자원을 사용하려는) 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있기도 한다. 의존 객체 주입은 생성자, 정적 팩터리, 빌더 모두에 똑같이 응용할 수 있다.
- 이 패턴의 쓸만한 변형으로 생성자에 자원 팩터리를 넘겨주는 방식이 있다. 팩터리란 호출할 때마다 특정 타입의 인스턴스를 반복해서 만들어주는 객체를 말한다.
- 이 방식을 사용해 클라이언트는 자신이 명시한 타입의 하위 타입이라면 무엇이든 생성할 수 있는 팩터리를 넘길 수 있다.
- 의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 이지럽게 만들기도 한다.
- 프레임워크 활용법은 이 책에서 다룰 주제는 아니지만, 이들 프레임워크는 의존 객체를 직접 주입하도록 설계된 API를 알맞게 응용해 사용하고 있음을 언급해둔다.

### 의존성 주입의 장점
- 코드의 재사용성, 유연성이 높아진다. 하나의 작업만 수행하는 작은 객체는 많은 상황에서 재결합하고 재사용하기가 쉽기 때문이다.
- 객체간 결합도가 낮기 때문에 한 클래스를 수정했을 때 다른 클래스도 수정해야 하는 상황을 막아준다
- 유지보수가 쉬우며 테스트가 용이해진다
- 확장성을 가진다

=> 하지만 의존 관계를 주입할 객체를 계속해서 생성하고 소멸한다면 아무리 GC가 성능이 좋아졌다고 하더라도 부담이 된다. 그래서 Spring에서는 Bean들을 기본적으로 싱글톤(Singleton)으로 관리함.

### 결론
- Spring 쓰면 의존성주입 알고쓰자
- 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안된다.
