# 아이템6. 불필요한 객체 생성을 피하라

- 똑같은 기능의 객체를 매번 생성하기보다는 객체 하나를 재사용하는 편이 나을 때가 많다.
- 특히 불변 객체는 언제든 재사용할 수 있다.

#### Q. new 로 String 만드는 것이랑, "" 이렇게 만드는 것(String Literal로 String을 선언)이랑 뭔 차이 일까?

- String 은 불변객체다.
- new 로 만들면 불필요한 객체 생성을 하는 것임

#### Q. 불변객체가 뭐냐? Immutable Object?
- Immutable Object : 불변객체는 재할당은 가능하지만, 한번 할당하면 내부 데이터를 변경할 수 없는 객체
- 즉, 객체에 값을 할당하면 내부 데이터를 변경시킬 수 없다는 것,  대표적인 예로 String, Integer, Boolean 등이 있다.
- String은 String str="a", str="ab" 이런 식으로 사용하기 때문에 값이 변경한다고 생각하여 불변객체가 아닌 것으로 착각하기 쉽다.
- 하지만 이것은 str가 처음에 참조하고 있는 "a"값이 "b"로 변경되는 것이 아니라 "b"라는 새로운 객체를 만들고 그 객체를 str이 참조하게 하는 것!

#### Q. 그럼 왜 String a= "example" 이렇게 만들어야 되나? 

``` java

String s=new String("bikini"); // 따라 하지 말 것!

```

위 문장은 실행될 때마다 String 인스턴스를 새로 만든다.
new 키워드는 이전에 동일한 값이 사용되었는지 여부에 관계없이 항상 새 인스턴스를 생성하도록 한다.

``` java
String s="bikini";
```
=> 이렇게 하면, 새로운 인스턴스를 만드는게 아니고 같은 가상 머신 안이라면 같은 문자열 리터럴을 사용하는 모든 코드가 같은 객체를 재사용함을 보장한다.
=> 자바는 문자열을 직접 할당할 시 문자열 상수 풀 내부에 모든 값을 저장한다.

``` java
String newString1 = new String("abc");
String newString2 = new String("abc");
newString1 == newString2; // false

// new 연산자를 사용하여 String 객체를 생성하면 항상 "힙 메모리"에 새 객체를 생성한다.

String StringLiteral1 = "abc";
String StringLiteral2 = "abc";
StringLiteral1 == StringLiteral2; // true

/*

java의 String은 불변성을 가지고 있다. 불변성은 const로 선언된 변수처럼 한번 선언되면 바뀌지 않는 특성이다.
JVM은 String Pool에 각 문자열 리터럴의 복사본을 하나만 저장하여 문자열에 할당된 메모리 양을 최적화할 수 있다.
=> interning이라고 한다.

예를 들어 String 변수를 만들고 값을 할당하면 JVM이 String Pool에서 동일한 값의 String을 검색한다.
발견되면 Java 컴파일러는 추가 메모리를 할당하지 않고 메모리 주소값을 반환한다.
찾을 수 없으면 풀에 추가하고(interning) 해당 주소값을 반환한다.

*/

// new 키워드로 생성한 String 객체도 intern() 함수를 통해 직접 interning을 해줄 수 있다. 
// intern() 함수를 실행하면 해당 String을 interning하고 주소값을 반환한다.

String StringLiteral = "abc";
String newString = new String("abc");
String internedString = newString.intern();
StringLiteral == internedString // true

// 굳이 이렇게 쓸 이유가 ?

```

### String Literal로 String을 선언하면 문자열 상수 풀(String Contstant Pool)에 저장된다고 하는데은 어디에 있나? 
- Heap 내 작은 캐시..
![image](https://github.com/jaero0725/develop_study/assets/55049159/c7512e01-c636-4361-ab1b-eb5967c8f3ca)

## 결론? 
- 문자열은 메모리를 할당을 최적화할 수 있는 String Constant Pool에 올려서 사용하자.

<hr>

생성자 대신 정적 팩토리 메서드를 제공하는 불변 클래스에서는 정적 패터리 메서드를 사용해 부릴요한 객체 생성을 피할 수 있다.

``` java

Boolean(String) 생성자 대신 Boolean.valueOf(String) 팩터리 메서드를 사용하는 것이 좋다.
- Boolean생성자 java9 Deprecated

```

생성자는 호출할 떄마다 새로운 객체를 만들지만, 팩터리 메서드는 그렇지 않다. 
