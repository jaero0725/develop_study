# 아이템7. finalizer와 cleaner 사용을 피하라
- 자바는 접근할 수 없게 된 객체를 회수하는 역할을 가비지 컬렉터가 담당하며 비메모리 자원을 회수하는 용도는 try-with-resources, try-finally로 해결한다.

### finalize 메소드란?

- 자바의 모든 클래스는 최상위 클래스인 Object 클래스의 여러 메서드를 포함하고 있음 
- 객체 소멸자라고 말하는 finalize 메서드도 그 메서드들 중 하나
- 존재이유가 불분명한 메서드
- 자바에서 메모리는 개발자가 직접 관리하지 않는다. 접근할 수 없게 된 객체를 회수하는 역할을 가비지 컬렉터 가 담당하고, 프로그래머 에게는 아무런 작업도 요구하지 않는다.
- "종료자는 사용하면 안 된다. 예측이 불가능하고 대체로 위험하고 일반적으로 필요하지 않다."

#### 권장: GC를 이용해서 메모리 회수하기
``` java
ObjectEx obj4; // ObjectEx 객체와 obj4 레퍼런스
obj4 = new ObjectEx(); // ObjectEx()클래스에 obj4레퍼런스 생성
obj4 = new ObjectEx(); // ObjectEx()클래스에 obj4레퍼런스 재 생성, 따라서 이전 관계는 끊어짐

// 언젠가 GC가 적절한 타이밈에 메모리를 회수해 줌
```


#### 비권장: 소멸자를 호출해서 자원 회수하기
``` java
@Override
	protected void finalize() throws Throwable {
		System.out.println(" -- finalize() method --");
		super.finalize();
  }
```

### 사용하지 말아야 하는 이유
- 자바는 (1) finalizer, (2)cleaner 두 가지 객체 소멸자를 제공한다. 
- 두 경우 모두 기본적으로 사용하지 말아야 한다. 사용하지 말아야 하는 이유는 다음과 같다.

#### (1) 제때 실행되어야 하는 작업은 절대 할 수 없다.

 - finalizer와 cleaner는 즉시 수행된다는 보장이 없다. 
 - 예컨대 파일 닫기를 한다면 시스템이 동시에 열 수 있는 파일 개수에 한계가 있기에 중대한 오류를 일으킬 수 있다.
 - 시스템이 finalier나 cleaner 실행을 게을리해서 파일을 계속 열어 둔다면 새로운 파일을 열지 못해 프로그램 이 실패할 수 있다.

#### (2) 수행 여부 또한 보장하지 않는다.
 -종료 작업을 전혀 수행하지 못한 채 중단될 수도 있다. 
 - 따라서 프로그램 생애주기와 상관없는 상태를 영구적으로 수정하는 작업에서는 사용해서는 안된다.

#### (3) 예외 처리
 - finalizer 동작 중 발생한 예외는 무시되며, 처리할 작업이 남았더라도 그 순간 종료된다.
 - 잡지 못한 예외로 인해 해당 객체는 마무리가 덜   된 상태로 남을 수 있다.

#### (4) 성능 문제
 - 가비지 컬렉터의 효율을 떨어뜨리고 안전망의 설치의 대가로 약 5배 정도 느려진다.

#### (5) 보안 문제
 - finalizer는 생성자나 직렬화 과정에서 예외가 발생한다면 이 생성되다만 객체에서 악의적인 하위 클래스의 finalizer가 수행될 수 있게 된다.
 - 또한 이 finalizer는 정적 필드에 자신의 참조를 할당해 가비지 컬렉터가 수집하지 못하게 막을 수 있다.
 - finalizer를 final로 선언해 해결할 수 있다.

### 정상적으로 자원 반납하는 방식

```
파일이나 스레드 등 종료해야 할 자원을 담고 있는 객체 클래스에서 finalizer나 cleaner를 대신해줄 묘안은 AutoCloseable을 구현해주고,
클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출해주는 방식이 있다.

여기서 close 메서드에서는 이 객체는 더 이상 유효하지 않음을 필드에 기록하고, 다른 메서드는 이 필드를 검사해 객체가 닫힌 후 불렸다면 IllegalStateException을 던지면 된다.

```
### Cleaner, Finalizer가 적절한 경우
- 그렇다면 cleaner와 finalizer는 언제 사용할까? 적절한 경우는 두 가지가 존재하며, 아래와 같다.

#### (1) 자원의 소유자가 close 메서드를 호출하지 않는 것에 대한 대비망
- 즉시 호출된다고 보장은 없지만 자원 회수를 늦게라도 해주므로 안전망 역할이다.
- 예를 들어, FileInputStream, FileOutputStream, ThreadPoolExecutor가 존재한다.

#### (2) 네이티브 피어와 연결된 객체에서의 사용
- native peer란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다.
- 네이티브 피어는 자바 객체가 아니여서 가비지 컬렉터는 그 존재를 알지 못한다.
- 따라서 자바 피어를 회수할 때 네이티브 피어도 회수하지 못해서 cleaner나 finalizer가 나서서 처리할 수 있다.

<br>

- 다음은 cleaner를 이용해 안전망으로 사용하는 예제이다.
``` java

public class Room implements AutoCloseable{
    private static final Cleaner cleaner = Cleaner.create();


    //Room을 참조하면 순환으로 참조하기에 가비지 컬렉터의 대상이 되지 않으므로 Room을 참조해서는 안된다.
    //청소가 필요한 자원, cleaner가 청소할 때 수거할 자원을 가진다.
    private static class State implements Runnable{
        int numJunkPiles; // 수거대

        public State(final int numJunkPiles) {
            this.numJunkPiles = numJunkPiles;
        }
        //close나 cleaner메소드가 호출한다.
        @Override
        public void run() {
            System.out.println("방청소");
            numJunkPiles = 0;
        }
    }

    private final State state; //방 상태

    private final Cleaner.Cleanable cleanable; //수거 대상이 된다면 방을 청소한다.

    public Room(final int numJunkFiles) {
        this.state = new State(numJunkFiles);
        cleanable = cleaner.register(this, state); 
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }
}

```


```
# Chat gpt
Java에서 finalizer와 cleaner는 객체의 소멸을 다루는 메커니즘입니다. 이들은 메모리 관리와 관련이 있으며, 객체가 더 이상 필요하지 않을 때 자원을 해제하는 데 사용됩니다.
하지만 이 두 가지 메커니즘은 목적과 동작 방식에서 차이가 있습니다.

finalizer: Object 클래스에 정의된 finalize() 메서드를 재정의하여 사용하는 메커니즘입니다.
 finalize() 메서드는 객체가 가비지 컬렉터에 의해 수집되기 전에 호출됩니다.
 객체가 소멸될 때 어떤 작업을 수행하도록 finalize() 메서드를 재정의할 수 있습니다.
 예를 들어, 파일이나 네트워크 연결과 같은 리소스를 해제하는 작업을 수행할 수 있습니다.
 하지만 finalizer는 예측할 수 없는 실행 시점과 순서로 호출되므로, 신뢰성과 성능 측면에서 문제가 있을 수 있습니다.
 또한 finalize() 메서드가 실행되기 전까지 객체가 계속 메모리를 점유하고 있어서 가비지 컬렉터가 느려질 수 있습니다.

 Java 9부터는 finalize() 메서드가 deprecated되었습니다.

cleaner: Java 9에서 도입된 java.lang.ref.Cleaner 클래스를 사용하는 메커니즘입니다.
 Cleaner 클래스는 객체 소멸 시 호출될 수 있는 clean() 메서드를 정의합니다.
cleaner는 명시적으로 생성되며, 객체의 소멸 시 clean() 메서드를 호출하는 역할을 담당합니다.
cleaner는 명시적으로 호출되거나 객체가 가비지 컬렉터에 의해 수집될 때 호출됩니다.
cleaner를 사용하면 finalize() 메서드와 달리 명시적으로 객체의 자원을 해제할 수 있으며, 순서를 제어할 수 있습니다.
 이는 더 안전하고 예측 가능한 객체 소멸을 가능하게 합니다.

일반적으로 cleaner를 사용하는 것이 finalizer보다 더 권장되는 방법입니다.
cleaner는 명시적으로 객체의 자원을 해제할 수 있으며, 제어할 수 있는 시점과 순서를 가지고 있습니다.
이는 메모리 관리와 리소스 해제에 더욱 유연성을 제공합니다. 그러나 cleaner를 사용할 때에도 주의가 필요하며, 적절한 사용 방법과 패턴을 따라야 합니다.

```

<hr>

#### REF
[finalizer와 cleaner 사용을 피하라](https://pro-dev.tistory.com/111)
