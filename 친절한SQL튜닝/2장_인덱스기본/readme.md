# 인덱스 기본

인덱스에 대한 기본 구조와 탐색 원리조차 모르는 상태에서 인덱스를 설꼐하고 SQL을 개발하면 성능이 좋을리 없다. <br>
인덱스 탐색 과정은 "수직적 탐색"과 "수평적 탐색" 두 단계로 이루어 진다. 

<hr>

## 인덱스 튜닝의 두 가지 핵심요소는?

```

1. 인덱스 스캔 효율화 튜닝
- 인덱스 스캔 과정에서 발생하는 비효율을 줄이는 것 

2. 랜덤 엑세스 최소화 튜닝 (이게 더 중요함)
- 테이블 엑세스 횟수를 줄이는 것 
- 성능에 미치는 영향이 크다. 

=> SQL튜닝은 랜덤 I/O와의 전쟁이다. 

```

## 인덱스

- 수직적 탐색 : 인덱스 스캔 시작지점을 찾는 과정
- 수평적 탐색 : 데이터를 찾는 과정


## 인덱스 컬럼을 가공하면 인덱스를 정상적으로 사용(Range Scan) 할 수 없는 이유는?

```
인덱스 컬럼을 가공했을 때 인덱스를 정상적으로 사용할 수 없는 이유는 인덱스 스캔 시작점을 찾을 수 없기 때문이다. 
인덱스 스캔 시작지점과 종료 시점이 있는데 컬럼이 변경된다면 인덱스 Range Scan 하지 못한다. 

예를들어

where substr(생년월일, 5, 2) = '05'
where nvl(주문수량, 0) < 100
where 업체명 like '%대한%'
where (전화번호 = :tel_no OR 고객명 = :cust_nm)

모두 Range Scan 할 수 없다. 
```

``` sql
SELECT * FROM 고객
WHERE 고객명 = :cust_nm
UNION ALL
SELECT * FROM 고객
WHERER 전화번호 = :tel_no
AND (고객명 <> :cust_nm OR 고객명 IS NULL)

```
OR 조건식을 SQL 옵티마이저가 위와 같은 형태로 변환 할 수 있는데 이를 'OR Expansion' 이라고 한다.<br> 
user_concat힌트로 유도할 수 있다. <br> 
OR Expansion을 유도할 때 Index Range Scan을 유도 할 수 있다. <br>

``` sql
SELECT /*+ USE CONCAT */ 
FROM 고객
WHERE (전화번호 = :tel_no OR 고객명 =:cust_nm)
```

IN 조건절일 경우도 Index Range Scan 하지 못한다. <br>
WHERE 전화번호 IN (:tel_no1, :tel_no2) <br>
이 경우에도 불가능하다. <br>
IN 조건은 OR 조건을 표현하는 다른 방식일 뿐이다. <br>
SQL을 아래와 같이 UNION ALL방식으로 작성하면, 각 브랜치 별로 인덱스 스캔시작점을 찾을 수 있다.<br>

```sql

SELECT * FROM 고객
WHERE 전화번호 = :tel_no1
UNION ALL
SELECT * FROM 고객
WHERER 전화번호 = :tel_no2

```

```
'인덱스를 정상적으로 사용한다'는 표현은 리프 블록에서 스캔 시작점을 찾아 거기서부터 스캔하다가 중간에 멈추는 것을 의미한다.
인덱스 컬럼이 가공되면 Index Range Scan하지 못하게 된다. 
단, OR 또는 IN 조건절은 옵티마이저의 쿼리 변환 기능을 통해 INDEX RNAGE SCAN으로 처리 되기도 한다.  

```
