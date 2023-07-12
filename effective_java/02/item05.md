# 아이템5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

## Dependency Injection의 개념과 필요성
### 의존성 주입이란?
- Spring 프레임워크의 3가지 핵심 프로그래밍 모델 중 하나가 의존성 주입이다.
- DI는 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴으로, 인터페이스를 사이에 둬서 클래스 레벨에서는 의존관계가 고정되지 않도록 하고 런타임 시에 관계를 종적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 한다.

- 의존성이란 한 객체가 다른 객체를 사용할 때 의존성이 있다고 한다.
```
    //Store 객체가 Pencil 객체를 사용하는 경우 Store 객체가 Pencil에 의존성이 있다고 표현한다. 
    public class Store {
        private Pencil pencil;
    }
```
- 두 객체 간의 관계를 맺어주는 것이 의존성 주입이라고 하며 생성자 주입, 필드 주입, 수정자 주입 등 다양한 주입 방법이 있다. 


- 객체 지향으로 하기위해 객체들은 독립적이고 자신의 상태를 책임지기 때문에 다른 객체에서 값을 꺼내 사용하기보다는 직접 그 객체에게 메시지를 보내 처리한다.
- 그래서 대상 객체에 메세지를 보내기 위해 대상 객체를 내포하고 있다.

``` java
class A {
    private B b;
}

A에서 B 로 흐름이 이어지니, A -> B로 의존서이 있다고 표현한다.
여기서는 의존성을 관계로 맺을 때 직접 객체를 명시하지 말고 Dependency Injection을 사용하라고 한다.
사실 스프링을 써봤다면 당연히 알테지만 왜 써야하는지 효과는 뭔지 한번 집고 넘어가보겠다.

class A {
    private final B b = new B();

    // Dependency Injection
    private final C c;
    public A(C c) { this.c = c;}
}

직접 명시할 경우 B가 아닌 다른 객체를 테스트 해보고 싶을 때마다 A라는 클래스를 테스트 할때마다 수정해야 한다.
또한 fianl 지시자를 지우고 다른 메서드에서 b 라는 객체를 다른 객체로 바꾸더라도 멀티 쓰레드 환경에서는 적합하지 않다.
객체를 다른 객체로 바꾸면 동작을 보장하지 못한다.

싱글톤 객체와 유틸 클래스는 한 번 로드가 되면 수정이 불가능하거나, static한 변수를 사용하기 떄문에 위와 같이 직접 명시를 사용한다.

아래 방법 처럼 DI를 사용하면, 연관된 객체가 여러개인지에 상관 없이 추상 객체 하나만 주입 받으면 문제 없이 잘 동작한다.
또한 불변을 보장하여 여러 클라이언트가 의존 객체를 안심하고 공유할 수 있다.
주입 당시 다른 객체들을 삽입할 수 있으므로 테스트에도 용이하다.

```

<hr>

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
[ 생성자 주입을 사용해야 하는 이유 ]
- 객체의 불변성 확보
- 테스트 코드의 작성
- final 키워드 작성 및 Lombok과의 결합
- 스프링에 비침투적인 코드 작성
- 순환 참조 에러 방지

=> 하지만 의존 관계를 주입할 객체를 계속해서 생성하고 소멸한다면 아무리 GC가 성능이 좋아졌다고 하더라도 부담이 된다. 그래서 Spring에서는 Bean들을 기본적으로 싱글톤(Singleton)으로 관리함.

### 결론
- Spring 쓰면 의존성주입 알고쓰자
- 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안된다.
