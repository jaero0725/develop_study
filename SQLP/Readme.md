## 1. 데이터 모델의 이해 및 분석
- 데이터베이스 구성과 처리에 있어서 가장 핵심적인 요소가 바로 데이터 모델이다. <br>
- 데이터 몰데은 건물의 설계도와 같이 전체 DB가 구성되는 요소를 결정한다. <br>
- 데이터 구조의 근간이 되기 떄문에 애플리케이션이 데이터를 이용할 때 효율적으로 제공될 것인지 아니면 비효율적으로 제공될 것인지에 대한 결정은 설계단계의 데이터 모델에서 할 수 밖에 없다.<br> 
- SQL 개발자는 데이터 모델과 SQL구문의 연관성을 위해 엔터티, 속성, 관계, 식별자, 정규화 등 데이터 모델의 기본 지식을 바탕으로 데이터 모델을 이해하고 분석하는 작업을 수행한다.<br>


## 2. SQL 이해 및 활용
- SQL은 DB를 유일하게 엑세스 할 수 있는 언어이다.<br>
- 본 직무는 우선적으로 SQL 문법, 옵티마이저, 인덱스의 기초 원리를 이해하는 단계부터 시작된다.<br>
- 이를 바탕으로 DDL을 통해 테이블의 구조를 생성, 변경, 삭제, 재명명하고 DML을 통해 데이터를 입력 조회 수정 삭제 한다. <br>
- 집합과 집합의 관계를 다양한 JOIN방법을 사용하여 표현하고 주종 관계의 경우 서브쿼리를 사용하는 작업등을 수행한다. <br>

## 3. SQL 튜닝 
- DB성능을 결정짓는 가장 핵심 적인 요소는 애플리케이션에 집중되어 있다.<br>
- SQL을 한번만 수행해도 같은 결과를 얻을 수 있는데 불필요하게 많은 SQL을 수행하거나, 파싱을 많이 일으키거나, 많은 I/O를 일으키도록 구현하는 것이 성능 문제를 유발한다.<br>
- SQL 튜닝은 고성능 SQL, 아키텍처 기반의 DB 튜닝 원리, Lock과 트랜잭션 동시성 제어 기법, 옵티마이저의 세부적인 작동 원리, 인덱스와 조인 튜닝 원리의 이해를 통해 SQL을 튜닝하는 작업 등을 포함한다.<br> 