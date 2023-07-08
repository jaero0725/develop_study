# 아이템5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

대부분의 클래스는 여러 리소스에 의존한다. 이 책에서는 SpellShecker와 Dictionary를 예로 들고 있따. 
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

        public SpellChecker(Lexion dictionary) {
            this.dictionary = Objects.requireNonNull(dictionar);

        public boolean isValid(String word) { ... }
        public List<String> suggertions(String type) {...}
    }
```

```
불변을 보장하여 (같은 자원을 사용하려는) 여러 클라이언트가 의존 객체들을 안심하고 공유할 수 있기도 한다. 의존 객체 주입은 생성자, 정적 팩터리, 빌더 모두에 똑같이 응용할 수 있다.
이 패턴의 쓸만한 변형으로 생성자에 자원 팩터리를 넘겨주는 방식이 있다. 팩터리란 호출할 때마다 특정 타입의 인스턴스를 반복해서 만들어주는 객체를 말한다.
이 방식을 사용해 클라이언트는 자신이 명시한 타입의 하위 타입이라면 무엇이든 생성할 수 있는 팩터리를 넘길 수 있다.
의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 이지럽게 만들기도 한다.
프레임워크 활용법은 이 책에서 다룰 주제는 아니지만, 이들 프레임워크는 의존 객체를 직접 주입하도록 설계된 API를 알맞게 응용해 사용하고 있음을 언급해둔다.

```

### 결론
클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안된다.
