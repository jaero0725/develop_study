# 아이템5. 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.

## Dependency Injection의 개념과 필요성
### 의존성 주입이란?
- Spring 프레임워크의 3가지 핵심 프로그래밍 모델 중 하나가 의존성 주입이다.
- DI는 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴으로, 인터페이스를 사이에 둬서 클래스 레벨에서는 의존관계가 고정되지 않도록 하고 런타임 시에 관계를 종적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 한다.
- 의존성이란 한 객체가 다른 객체를 사용할 때 의존성이 있다고 한다.
  
``` java
    //Store 객체가 Pencil 객체를 사용하는 경우 Store 객체가 Pencil에 의존성이 있다고 표현한다. 
    public class Store {
        private Pencil pencil;
    }
```

- 두 객체 간의 관계를 맺어주는 것이 의존성 주입이라고 하며 생성자 주입, 필드 주입, 수정자 주입 등 다양한 주입 방법이 있다. 

### 의존성 주입이 왜 필요한가?
예를 들어 연필이라는 상품과 1개의 연필을 판매하는 Store 클래스가 있다고 하자.

``` java
public class Store {

    private Pencil pencil;

    public Store() {
        this.pencil = new Pencil();
    }
}
```

- 위와 같은 예시 클래스는 크게 다음과 같은 문제점을 가지고 있다.

    1) 두 클래스가 강하게 결합되어 있음
    2) 객체들 간의 관계가 아니라 클래스 간의 관계가 맺어짐

1) 강한 결합
두 클래스가 현재 강하게 결합되어 있다는 문제점이 있다.
만약 Store에서 Pencil이 아닌 Food와 같은 다른 상품을 판매하려고 전략을 변경한다면, Store생성자에 변경이 필요하다.
아마 this.food = new Food(); 로 코드를 변경해줘야한다.
이러면 코드의 유연성이 떨어지게 된다.
각각의 다른 상품들을 판매하기 위해 생성자만 다르고 나머지는 중복되는 Store클래스로 파생되는 것은 좋지 않다.

2) 객체 들 간의 관계가 아닌 클래스 간의 관계
위의 Store와 Pencil는 객체 들 간의 관계가 아니라 클래스들의 관계가 맺어져 있다는 문제가 있다.
올바른 객체지향적 설계라면 객체들 간의 관계가 맺어져야 한다.
객체들 간의 관계가 맺어진다면 다른 객체의 구현클래스가 전혀 알지 못하더라도 인터페이스를 구현했다면, 인터페이스의 타입으로 사용할 수 있다.

위와 같은 문제점이 발생하는 근본적 이유가 Store에서 불필요하게 어떤 제품을 판매할지에 대한 "관심이 분리되지 않은점" 떄문이다.

Spring에서 DI를 적용하여 해결했는지 알아보겠다.

###  의존성 주입(Dependency Injection)을 통한 문제 해결
위와 같은 문제를 해결하기 위해서는 다형성이 필요하다.

``` java
// 인터페이스 우선 구현
public interface Product {

}

public class Pencil implements Product {

}

```

Store와 Pencil이 강하게 결합되어 있는 부분을 제거하여 외부에서 Product를 주입받도록 하였다 -> Store에서 구현 클래스에 의존하지 않는다!

```  java
public class Store {

    //private Pencil pencil;

    //public Store() {
    //    this.pencil = new Pencil();
    //}

    private Product product;

    public Store(Product product) {
        this.product = product;
    }

}
```
- 이러한 이유로 우리는 Spring이라는 DI 컨테이너를 필요하다.
- Store에서 Product 객체를 주입하기 위해서는 애플리케이션 실행 시점에 필요한 객체(빈)를 생성해야 하며, 의존성이 있는 두 객체를 연결하기 위해 한 객체를 다른 객체로 주입시켜야 한다. 

``` java
public class BeanFactory {

    // 이런 부분은 스프링 엔진에서 알아서 해준다.
    // 특정 위치부터 클래스를 탐색하고, 객체를 만들며 객체들의 관계까지 설정해준다.
    // 어떤 객체를 사용할지에 대한 책임은 Spring 프레임워크가 해주는 것이다.

    public void store() {
        // Bean의 생성
        Product pencil = new Pencil();
    
        // 의존성 주입
        Store store = new Store(pencil);
    }
    
}

```
- 한 객체가 어떤 객체(구현 클래스)에 의존할 것인지는 별도의 관심사이다. 
- Spring은 의존성 주입을 도와주는 DI 컨테이너로써, 강하게 결합된 클래스들을 분리하고, 애플리케이션 실행 시점에 객체 간의 관계를 결정해 줌으로써 결합도를 낮추고 유연성을 확보해준다.
- 이러한 방법은 상속보다 훨씬 유연하다. 단, 한 객체가 다른 객체를 주입받으려면 반드시 DI 컨테이너에 의해 관리되어야 한다는 것이다.
    
    두 객체 간의 관계라는 관심사의 분리
    두 객체 간의 결합도를 낮춤
    객체의 유연성을 높임
    테스트 작성을 용이하게 함
 

- 하지만 의존 관계를 주입할 객체를 계속해서 생성하고 소멸한다면, 아무리 GC가 성능이 좋아졌다고 하더라도 부담이 된다.
- 그래서 Spring에서는 Bean들을 기본적으로 싱글톤(Singleton)으로 관리한다.
  
<hr>

### Spring 프레임워크에서는 생성자 주입을 사용하는 것을 권장한다.
#### 1. 객체의 불변성 확보
- 생성자 주입은 생성자 호출 시점에 1회 호출되는 것이 보장이 되어 수정의 가능성을 닫아 둘 수 있다.
  <br>
#### 2. 테스트 코드의 작성
- 생성자 주입을 사용하면 컴파일 시점에 객체를 주입받아 테스트 코드를 작성할 수 있으며, 주입하는 객체가 누락된 경우 컴파일 시점에 오류를 발견할 수 있다.
- 심지어 우리가 테스트를 위해 만든 Test객체를 생성자로 넣어 편리함을 얻을 수도 있다.
  <br>
#### 3. final 키워드 작성 및 Lombok과의 결합
- 필드 객체에 final 키워드를 사용할 수 있으며, 컴파일 시점에 누락된 의존성을 확인할 수 있다.
- final 키워드를 붙이면 Lombok과 결합되어 코드를 간결하게 작성할 수 있다.
- @Autowired를 생략할 수 있도록 도와주고 있으며, 해당 생성자를 Lombok으로 구현
``` java
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemberService memberService;

    public void register(String name) {
        userRepository.add(name);
    }

}
```
  <br>
  
#### 4. 순환 참조 에러 방지
- 생성자 주입을 사용하면 애플리케이션 구동 시점(객체의 생성 시점)에 순환 참조 에러를 예방할 수 있다.
``` java
@Service
public class UserService {

    @Autowired
    private MemberService memberService;
    
    @Override
    public void register(String name) {
        memberService.add(name);
    }

}
```

``` java
@Service
public class MemberService {

    @Autowired
    private UserService userService;

    public void add(String name){
        userService.register(name);
    }

}
```

- UserSerivce가 이미 MemberService에 의존하고 있는데, MemberService 역시 UserService에 의존하고 있다.
- 서로를 계속 호출할 것이고, 메모리에 함수의 CallStack이 계속 쌓여 StackOverflow 에러가 발생

``` java
Caused by: java.lang.StackOverflowError: null
	at com.mang.example.user.MemberService.add(MemberService.java:20) ~[main/:na]
	at com.mang.example.user.UserService.register(UserService.java:14) ~[main/:na]
	at com.mang.example.user.MemberService.add(MemberService.java:20) ~[main/:na]
	at com.mang.example.user.UserService.register(UserService.java:14) ~[main/:na]
```
- 생성자 주입을 이용하면 이러한 순환 참조 문제를 방지할 수 있다.
- 애플리케이션 구동 시점(객체의 생성 시점)에 에러가 발생하기 때문이다. 그러한 이유는 Bean에 등록하기 위해 객체를 생성하는 과정에서 다음과 같이 순환 참조가 발생하기 때문이다.

``` java
new UserService(new MemberService(new UserService(new MemberService()...)))
 ```

    @Autowired를 이용한 필드 주입에서 이러한 문제가 애플리케이션 구동 시점에 에러가 발생하지 않는 이유는
    빈의 생성과 조립(@Autowired) 시점이 분리되어 있기 때문이다.
    생성자 주입은 객체의 생성과 조립(의존관계 주입)이 동시에 실행되다 보니위와 같은 에러를 사전에 잡을 수 있다. 
    하지만 @Autowired는 모든 객체의 생성이 완료된 후에 조립(의존관계 주입)이 처리된다. 
    그러다 보니 위와 같이 호출이 되고 나서야 순환 이슈를 확인할 수 있는 것이다.

* 스프링부트 26부터 순환 참조가 기본적으로 허용 안함. 필드 주입을 받아도 순환 참조가 발생함.
![image](https://github.com/jaero0725/develop_study/assets/55049159/90ed8cce-4941-4106-af6c-bd96f268df4d)

<a href="https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.6-Release-Notes#circular-references-prohibited-by-default">공식문서</a>

