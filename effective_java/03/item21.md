# 아이템 21. 인터페이스는 구현하는 쪽을 생각해 설계하라

## 인터페이스에 메서드 추가
- 자바 8 전에는 기존 구현체를 깨뜨리지 않고 인터페이스에 메서드를 추가할 방법은 존재하지 않았다.
- 자바 8부터 디폴트 메서드를 통해서 기존 인터페이스에 메서드를 추가할 수 있게 되었다.
- 디폴트 메서드를 선언하면, 그 인터페이스를 구현한 후 디폴트 메서드를 재정의하지 않은 모든 클래스에서 디폴트 구현이 쓰이게 된다.
- 단, 이렇게 디폴트 메서드를 추가한다고해도 기존 구현체들과 매끄럽게 연동된다는 보장은 없다.

<hr> 

## 이미 만들어진 인터페이스에 디폴트 메서드를 추가하는 것은 위험하다.

``` java
default boolean removeIf(Predicate<? super E> filter) {
    Objects.requireNonNull(filter);
    boolean removed = false;
    final Iterator<E> each = iterator();
    while (each.hasNext()) {
        if (filter.test(each.next())) {
            each.remove();
            removed = true;
        }
    }
    return removed;
}
```

- 동기화와 관련된 코드가 전혀 없다.
- Collection를 구현하는 (동기화를 제공하는) SynchronizedCollection 에서 디폴트 메서드 removeIf가 제대로 동작하지 않는다.
- Concurrentmodificationexception 발생할 수 있다.
- 한 스레드가 어떤 Collection을 반복자(iterator)를 이용하여 순회하고 있을때, 다른 한스레드가 해당 Collection에 접근하여 변경을 시도하는 경우이다. 
- 하지만 꼭 멀티스레드 환경에서만 발생 하는것은 아니다. 싱글 스레드 환경에서도 발생할 수 있는데, 
- 위와 같이 어떤 Collection을 순회하고 있는 반복문 안에서, 
- 순회되고 있는 Collection에 대한 변경이 시도 될 때 또한 해당 Exception이 발생 하게 된다.



## 디폴트 메서드 동기화 유지 방법
- 자바에서는 디폴트 메서드가 일으킬 수 있는 문제 예방을 위해 취한 조취중 하나로 인터페이스의 디폴트 메서드를 재정의하고,
다른 메서드에서 디폴트 메서드를 호출하기 전에 필요한 작업을 수행하도록 했다.

[4.4 버전 이후로 동기적으로 구현]

``` java

	/**
    * @since 4.4
    */
    @Override
    public boolean removeIf(final Predicate<? super E> filter) {
        synchronized (lock) {
            return decorated().removeIf(filter);
        }
    }
```

- 4.4 이전 버전에서는 동기적으로 구현되지 않아 removeIf 호출시 default 메서드를 호출하게되어 동기적으로 동작하지 못했다.
- 4.4 이후 버전에서는 Collection.synchronizedCollection이 반환하는 package-private 클래스들은 removeIf를 재정의하고, 이를 호출하는 다른 메서드들은 디폴트 구현을 호출하기 전에 동기화 하도록 했다.

## 상속과 구현의 작동에 적용되는 규칙

  Rule #1: Classes win over interfaces.  If a class in the superclass
  chain has a declaration for the method (concrete or abstract), you're
  done, and defaults are irrelevant.

  Rule #2: More specific interfaces win over less specific ones (where
  specificity means "subtyping").  A default from List wins over a default
  from Collection, regardless of where or how or how many times List and
  Collection enter the inheritance graph.

1) 클래스가 인터페이스보다 우선한다.
2) 구체적인 인터페이스가 덜 구체적인 인터페이스보다 우선한다.

## EX
``` java
public interface MarkerInterface {
    default void hello() {
        System.out.println("hello interface");
    }
}
public class SuperClass {
    private void hello() {
        System.out.println("hello class");
    }
}
public class SubClass extends SuperClass implements MarkerInterface {

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.hello();
    }
}

```

- SubClass는 클래스 SuperClass 상속받는다.
- SubClass는 인터페이스 MarkerInterface 를 구현한다.
- subClass.hello()의 hello()는 어떤 메서드일까? → SuperClass 의 hello() → IllegalAccessError 발생
- 
![image](https://github.com/jaero0725/develop_study/assets/55049159/6f682511-d7fb-4603-ac96-2ad98a84532f)
a
### 디폴트 메서드는 오류를 일으킬 가능성이 있다.
```
디폴트 메서드는 컴파일에 성공하더라도 기존 구현체에 런타임 오류를 일으킬수 있다. 때문에 기존 인터페이스에 디폴트 메서드로 새 메서드를 추가하는일은 피해야한다.

반면, 새로운 인터페이스를 만드는 경우라면 표준적인 메서드 구현을 제공하는데 아주 유용한 수단이며,
 그 인터페이스를 더 쉽게 구현해 활용할 수 있게끔 해준다.
```

### 인터페이스 설계시 주의가 필요하다.
```
디폴트 메서드라는 도구가 생겼지만 인터페이스를 설계할 때는 여전히 주의를 기울일 필요가 있다.
기존 인터페이스에 디폴트 메서를 추가할 시 어떤 위험이 딸려올지 알 수 없기 때문이다.
```

### 인터페이스 릴리즈전에 테스트를 거치자.
```
새로운 인터페이스라면 릴리스 전에 반드시 테스트를 거쳐야한다. 서로 다른 방식으로 최소 세가지는 구현해보는것을 추천한다.
 또한 다양한 클라이언트도 만들어 보는것이 좋다.
이런 테스트 과정을 통해 결함을 찾아내야 한다. 인터페이스를 릴리스 한 후라도 결함을 수정하는게 가능할 경우도 있겠지만, 그 가능성에 기대서는 안된다.
```
