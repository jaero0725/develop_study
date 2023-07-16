# 아이템7. 다 쓴 객체 참조를 해제하라!
- C, C++ 처럼 메모리를 직접 관리해야 하는 언어와 비교해 자바는 가비지 컬렉터를 통한 편리함을 느낄 수 있다.
- 그렇다면 자바 사용 시 JVM은 GC가 메모리 관리를 해주니 메모리 관리에 소홀해도 되는가? -> 그건 아니다.

<hr>

![image](https://github.com/jaero0725/develop_study/assets/55049159/a93eccf5-c873-4986-9170-c0db4fe8b114)

``` java
public class Stack {
	private static final int DEFAULT_INITAL_CAPACITY = 16;

	private Obejct[] elements;
	private int size = 0;
	
	public Stack() {
		elements = new Object[DEFAULT_INITAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++];
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		return elements[--size]; // *
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
}
```
- 여기서 별 문제가 없다고 생각할 수 있지만, pop의 기능은 최근에 들어온 요소가 반환되고 삭제되는 메서드.
- `elements[--size]` 이와 같이 반환을 해놓은다면 실제 값은 삭제되지 않고 인덱스만 한칸씩 이동하는 것으로 메모리 누수가 발생한다.

### TEST 코드

``` java
import org.junit.jupiter.api.Test;

class StackTest {

    @Test
    void stackTest() {
        Stack stack = new Stack();

        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 10; i++) {
            stack.pop();
        }
    }
}
```
- 10개의 값을 push 하고 10개의 값을 pop하는 로직입니다.

![image](https://github.com/jaero0725/develop_study/assets/55049159/4da48987-e6c6-4a77-9a3f-aee67db37ffc)
- 모든 값을 pop 했지만, 실제 elements는 남아있다.
- GC가 해당 요소를 관리하여 제거 하겠지만, 추후 성능저하의 원인이 될 수 도 있다.
- 그렇다면 현재 코드에서 메모리 누수가 발생하는 이유?
  
  바로 다 쓴 참조(obsolete reference)를 여전히 가지고 있다는 것이다.
  즉, elements 배열의 활성 영역 밖의 참조들이 모두 여기에 해당한다는 것

### 해결법
``` java
public class Stack {
	private static final int DEFAULT_INITAL_CAPACITY = 16;

	private Obejct[] elements;
	private int size = 0;
	
	public Stack() {
		elements = new Object[DEFAULT_INITAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++];
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		//return elements[--size]; // *
    // 변경된 코드 
  	Object result = elements[--size];
  	elements[size] = null;  // 해당 참조를 다 쓰면 null(참조 해제)로 초기화 
  	return result;

// null을 사용함으로써 또 다른 이점은 해당 요소가 없는(null 처리한 참조) 메모리 공간을 사용하려고 하면
// 프로그램은 즉시 NPE를 발생시키며 프로그램을 종료할 것이다.
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
}
```
### 그럼 항상 Null처리 하는 것이 좋나? 

- 모든 객체를 null로 만들면 프로그램을 필요 이상으로 지저분하게 만들 뿐이다.
- 객체 참조를 null 처리하는 일은 예외적인 경우여야 한다.
- 즉, 참조 해제의 가장 좋은 방법은 참조를 담은 변수를 유효 범위 밖으로 밀어내는 것이다. //*
- 이부분은 아이템 57. 지역변수의 범위를 최소화하라를 참고하면 알 수 있다.

<hr>

## 메모리 누수의 주범

### 메모리 누수의 첫번째 주범 : Stack처럼 자기 메모리를 직접 관리하는 경우
- 배열로 저장소 풀을 만들어 원소를 관리하면, 활성 영역에 속한 원소들은 사용되고 비활성 영역은 쓰이지 않는데 GC는 이러한 정보를 알수가 없다.
- 따라서 null을 참조함으로써 GC에게 비활성 영역의 부분을 알려줘야 한다.

###  메모리 누수의 두번째 주범 : 캐시

- 객체 참조를 캐시에 넣고 객체를 다 쓴 이후에도 그냥 두는 경우가 있다.
- 해결방법
  1) 캐시 외부에서 키(key)를 참조하는 동안만 엔트리가 살아 있는 캐시가 필요한 경우에는 WeakHashMap을 사용하자.
  2) 엔트리의 유효 기간을 정해두자.
     - 그러나 이 방법은 유효 기간을 계산하는 것이 어렵다.
  3) 쓰지 않는 엔트리를 청소하자
     - ScheduledThreadPoolExecutor와 같은 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 수행하는 방법을 이용하면 된다.
     - LinkedHashMap은 removeEldestEntry 메서드를 사용해 후자의 방식으로 처리한다.
- 더 복잡한 캐시를 만들기 위해서는 java.lang.ref 패키지를 직접 활용하면 된다.

###  메모리 누수의 세번째 주범 : 리스터 혹은 콜백 호출
- 콜백이란 이벤트가 발생하면 특정 메소드를 호출해 알려주는 것입니다.(1개)
- 리스너는 이벤트가 발생하면 연결된 리스너(핸들러)들에게 이벤트를 전달합니다.(n개)
- 클라이언트가 콜백을 등록만 하고 해지하지 않는다면 콜백은 쌓이게 될 것이다.
- 이럴 때 콜백을 약한 참조(weak reference)로 저장하면 GC가 즉시 수거해간다.
- 예를 들어 WeakHashMap에 키로 저장해두면 된다.

<hr>

## 결론
- 메모리 누수를 방지하는 방법은 다쓴 객체 참조를 null로 처리하는 것과 지역변수의 범위를 최소화 하는 방법이다.
- 모든 것을 null로 처리한다고 해서 좋은 것은 아니다. 가장 좋은 방법은 지역 변수의 범위를 최소화 하는 방법이다.
- 메모리 누수의 주범은 자기 메모리를 직접 관리하는 경우, 캐시, 리스너, 콜백이다.
- 자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야 한다.
- 메모리 누수는 철저한 코드리뷰, 힙 프로파일링 도구를 통해 디버깅을 해야 발견할 수 있기 때문에 메모리 누수를 철저히 신경써야 합니다.

<hr> 

#### REF

### 가비지 컬렉터의 동작? 
item 7에 메모리 누수 Memory Leak에 대한 내용이 일부 나오는데 같이 보면 좋을거 같은 링크
https://d2.naver.com/helloworld/329631  <br>
https://d2.naver.com/helloworld/1329  <br>
https://d2.naver.com/helloworld/1326256  <br>

https://jaehun2841.github.io/2019/01/07/effective-java-item7/#WeakHashMap <br>
https://lelecoder.com/20  <br>
https://pro-dev.tistory.com/110  <br>
https://pro-dev.tistory.com/108

