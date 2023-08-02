# 아이템 26. 로 타입은 사용하지 말라

- JDK5 부터 사용가능해진 제네릭(generic)은 불필요한 형변환 작업을 생략하게 해주고, 사용자 입장에서 한결 편하게 타입추론이 가능하게 해주는 기능이다. 
- 제네릭을 사용하면 컬렉션이 담을 수 있는 타입을 컴파일러에게 알려주게 되기에, 컴파일러는 알아서 형변환 코드를 추가할 수 있게 되고,
- 애초에 컴파일 과정에서부터 잘못된 타입의 객체를 넣지 못하게 차단해서 안전하고 명확한 코드를 작성할 수 있다. 

#### 이런 제네릭의 이점을 최대로 살리고 단점을 최소화하는 방법에 대해 이야기해본다. 

<hr>

### 제네릭 클래스(or 제네릭 인터페이스)
- 클래스와 인터페이스 선언에 타입 매개변수(type parameter)가 쓰인 클래스 혹은 인터페이스 (Ex: List<E>, Set<E>, Map<K, V>)로 이를 통틀어 제네릭 타입(generic type)이라 한다.
- 제네릭 타입은 매개변수화 타입(parameterized type)을 정의하는데 이 타입이 정규 타입 매개변수에 해당하는 실제 타입 매개변수가 된다.  다음 코드를 보자.

``` java
public interface List<E> extends Collection<E> { ... }
...
private final List<String> = ...;

```
- List 인터페이스에서 꺽쇠안의 작성된 E는 정규 타입 매개변수로 일종의 포맷이라 볼 수 있다. 
- 그래서 해당 타입매개변수에 실제(actual)로 작성된 타입 매개변수를 컴파일러에서 알아서 형변환 코드를 추가해줄 수 있다.

### 로 타입(raw type)
- 제네릭 타입을 하나 정의하면 그에 딸린 로 타입(raw type)도 함께 정의된다.
- raw type? 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때를 말함
- ex) List<E>의 raw type은 List 다.
- 로 타입을 쓰는 걸 언어 차원에서 막아두진 않았지만, 절대로 사용하면 안된다.
- 로 타입을 쓰면 제네릭이 안겨주는 안전성과 표현력을 모두 잃게 됨
- 이 기능은 왜 있는걸까? 제네릭이 사용되기 이전 코드와 호환되기 위한 기능.
	- 제네릭은 자바가 나오면서 같이 나온 창립멤버가 아니다! 
	- 즉 길고긴 자바의 역사에서 제네릭이 들어와서 본격적으로 사용된것은 거의 자바 출시부터 10년정도가 걸린 상황인데, 기존의 코드와의 호환성을 위해서는 로 타입도 동작을 해야만 했다.
	-  바로 이 마이그레이션 호환성을 위해서 로 타입을 지원하고 제네릭 구현에는 소거 방식을 사용하기로 했다.

- List 같은 로 타입은 사용해서는 안되지만, List<Object> 처럼 임의 객체를 허용하는 매개변수화는 괜찮다. (ex 2 소스)
  - List는 제네릭 타입에서 완전히 발을 뺀 것이고, List<Object>는 모든 타입을 허용한다는 의사를 컴파일러에 명시한 것
  - List에는 List<String>을 넘길 수 있지만, List<Object>를 받는 메서드에는 넘길 수 없음
  - 제네릭의 하위 타입 규칙 때문. : 즉, List<String>은 로 타입인 List의 하위 타입이지만, List<Object>의 하위타입이 아님
- List<Object> 같은 배개변수화 타입을 사용할 때와 달리 List 같은 로 타입을 사용하면 타입 안정성을 잃게된다.

  
1-1) raw type 예제
``` java
// stamps는 Stamp Instance만 넣을 수 있다고 가정하자.
private final Collection stamps = ...;

// 이렇게 Coin Instance를 넣으면, 오류없이 컴파일되고 실행됨.
stamps.add(new Coin(...)); // unchecked call "경고"를 뱉음

for(Iterator i = stamps.iterator(); i.hasNext() ; ) {
    // ClassCastException 발생(위에 넣은 Coin Instance를 Stamp로 변환하려고 해서)
    Stamp stamp = (Stamp) i.next();	
    
    stamp.cancel();
}

Coin Instance를 넣을 때는 오류가 안나지만, 실제로 꺼내서 사용하면 오류가 발생한다.
오류는 가능한 한 발생 즉시, 이상적으로는 컴파일할 때 발견하는 것이 좋다.

```

1-2) 매개변수화된 컬렉션 타입 (타입 안정성이 확보)
``` java 

// stamps는 Stamp Instance만 넣을 수 있다고 컴파일러에게 알려줌
private final Collection<Stamp> stamps = ...;

// stamps에 Coin Instance를 넣으면 경고가 아닌 컴파일 오류가 발생함
stamps.add(new Coin(...));
```

2-1) raw type 예제(List 로 받는 경우)

``` java
public static void main(String[] args) {
    List<String> strings = new ArrayList<>();
   
    unsafeAdd(strings, Integer.valueOf(42));
    
    // 컴파일은 정상작동하지만, ClassCastException이 발생(Integer를 String으로 변환하려함)
    String s = strings.get(0);	// 컴파일러가 자동으로 형변환 코드를 넣어줌
}

// raw type인 List 이용
private static void unsafeAdd(List list, Object o) {
    list.add(o);
}
```

2-2) List<Object> 로 받는 경우

``` java
public static void main(String[] args) {
    List<String> strings = new ArrayList<>();
   
    unsafeAdd(strings, Integer.valueOf(42));
    
    // 컴파일조차 안 됨
    String s = strings.get(0);	// 컴파일러가 자동으로 형변환 코드를 넣어줌
}

// raw type인 List 이용
private static void unsafeAdd(List<Object> list, Object o) {
    list.add(o);
}
```

``` java
public class Foods {
    private final List store = new ArrayList();

    public Foods() { }

    public void add(Object obj) {
        store.add(obj);
    }

    public void print(){
        for (Iterator it = store.iterator(); it.hasNext();) {
            Food food = (Food) it.next(); // ClassCastException 발생
            System.out.println("food = " + food);
        }
    }
}
...

public class FoodApp {
    public static void main(String[] args) {
        Foods foods = new Foods();

        foods.add(new Weapon("도끼", 10));
        foods.add(new Food("피자", LocalDateTime.of(2021, Month.JUNE, 25,17,30), 1000));

        foods.print();
    }
}

// ###################################### 
// 다음 코드는 제네릭을 이용해 리팩터링을 한 소스다.
// ######################################

public class Foods {
    private final List<Food> store = new ArrayList();

    public Foods() { }

    public void add(Food obj) {
        store.add(obj);
    }

    public void print(){
        for (Iterator<Food> it = store.iterator(); it.hasNext();) {
            Food food = it.next();
            System.out.println("food = " + food);
        }
    }
}

```


### 대안책 : 비한정적 와일드카드 타입
- 매개변수화 타입이 ?인 제네릭 타입
- 제네릭 타입을 쓰고는 싶지만 실제 타입 매개변수가 무엇인지 신경쓰고싶지 않을 때, 로 타입을 사용할게 아니라 물음표를 사용하면 어떤 타입도 담을 수 있는 범용적인 매개변수화 타입이 된다. 

``` java

public class UnboundWildcardApp {
    public static void main(String[] args) {
        HashSet<Integer> s1 = new HashSet<>() {{
            add(1);
            add(3);
            add(4);
        }};
        HashSet<Integer> s2 = new HashSet<>() {{
            add(1);
            add(4);
            add(5);
            add(6);
        }};

        long count = numElementInCommon(s2, s1);
        System.out.println("count = " + count);


    }

    static long numElementInCommon(Set<?> s1, Set<?> s2) {
        return s1.stream()
                .filter(obj-> s2.contains(obj))
                .count();
    }
}

```

#### 비한정적 와일드카드 타입은 로 타입에 비교해서 안전하다. 
- 아무 원소나 넣을 수 있어 타입 불변식을 훼손할 수 있는 로 타입 컬렉션에 비교해서 비한정적 와일드카드 타입에는 null외의 어떤 원소도 넣을 수 없다.
- 그래서 컬렉션의 타입 불변식을 훼손하지 못하게 막았다. 
- 만약 이런 부분이 요구사항화 상충하여 해당 제약이 없어야 한다면 제네릭 메서드나 한정적 와일드 카드를 사용하면 된다.

#### 로 타입을 사용해야 하는 경우
- 로 타입을 써야 하는경우도 있다.  바로 class 리터럴에는 로 타입을 써야 하는데, 자바 명세에는 class 리터럴에 매개변수화 타입을 사용하지 못하게 했다. (배열과 기본 타입은 허용)
  
```
  허용되는 경우
    List.class
    String[].class
    int.class
  허용이 안되는 경우
    List<String>.class
    List<?>.class
```

- 또 다른 경우는 instanceof 연산자를 사용할 경우인데 런타임시 제네릭 타입정보는 지워지기 때문에 instanceof 연산자는 비한정적 와일드카드 타입 이외의 매개변수화 타입에는 적용이 불가능하다.
- 또한 로 타입이나 비한정적 와일드카드 타입이나 instanceof는 동일하게 동작한다. 그렇기 때문에 불필요한 코드 작성(<?>)을 하지 않고 로 타입으로 쓰는게 낫다.

``` java

if(o instanceof Set) {
		Set<?> s = (Set<?>) o;
		...
}

```
⇒ instanceof에서는 로 타입을 사용해서 Set인지 확인을 했다면 내부 코드에서는 Set<?>으로 형변환을 해주자.

## 정리
- raw type을 사용하면 런타임에 예외가 발생할 수 있으니 사용하지 말자
- raw type은 제네릭이 도입되기 전 코드와의 호환성을 위해 제공된다.
- Set<?>는 모종의 타입 객체만 저장할 수 있는 와일드카드 타입
- 이들의 raw type인 Set은 제네릭 타입 시스템에 속하지 않는다.
- Set<Object>, Set<?>는 안전하지만, raw type인 Set은 안전하지 않다.
