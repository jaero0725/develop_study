```
 테스트가 Mock 프레임워크 없이 테스트가 불가능한데? 
 숨겨진 의존성은 테스트 하기 힘들다.

 의존성 주입은 모든 문제를 해결할 수 없다는 점.

 ClockHolder 에 의존

 의존성 주입과, 의존성 역전 - 다형성의 원리를 이용해서
 Port P 
```

## Port Aport 
대부분의 소프트웨어 문제는 의존성 역전으로 해결이 가능하다.


## Testability
```
얼마나 쉽게 input을 변경하고, output을 쉽게 검증할 수 있는가?

호출자는 모르는 입력이 존재한다.
호출자는 모르는 숨겨진 인풋?
- 캡슐화가 꺠짐.
- PowerMockito

하드 코딩된 외부 시스템과 연동이 되어 있는 경우.

외부에서 결과를 테스트 할 수 없는 경우?

```

##

```
# Builder 패턴
- 생성자가 많아질때 해결할 수 잇는 유연한 해결책
- @Builder
- 버그가 있을 수 있음 (파라미터 누락)
- 장점?
 - 테스트 가독성 높여줌.
 - 문법적으로 지저분한 부분을 대부분 가려줌.
 
# 엔티티
- 도메인 엔티티, 영속성 객체, DB엔티티...
- 엔티티는 DB와 딱히 상관없는 개념. 도메인 엔티티와 DB엔티티와 다르다.

도메인 엔티티(Domain Entity)
- Class
- 비지니스 영역을 해결하는 모델

영속성객체(Persistent object)
- ORM 매핑

DB 엔티티 (DB Entitiy)
- Table
- RDB에 저장되는 객체

* RDB에 종속되지 않게 하려면?


private Method는 테스트하지 않아도 된다.
-> 하고싶다면 설계를 개선해라

final 메서드를 stub해야되는 상황
-> 설계가 잘못된 상황이다.

DRY - 코드 중복을 줄여 
DAMP  - 서술적이고 의미 있는 문구
-> 테스트 할떄는 DRY가 아니라 DAMP

테스트에 논리 를 넣지 말자
-> for, if + - 을 넣지말자.
-> 간단한 놀리 로직으로 인해 예측하지 못하는 버그가 생길 수 잇음. 직관적이고 바로 이해될 수 있도록 짜는게 중요하다.

```

# 회귀버그를 냅두면서 만드는 테스트 코드 
