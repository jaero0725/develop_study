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

## 인덱스 선두 컬럼이 조건절에 있어야 하는 이유? 

인덱스를 [소속팀 + 사원명 + 연령] 순으로 구성했을 경우 아래 조건절에 대해 인덱스를 정상적으로 Range Scan 할 수 있을까?

``` sql

SELECT * FROM 사원
WHERE 사원명 = '홍길동'

```

```
인덱스를 저렇게 구성한 뜻은, 
소속팀 순으로 정렬 -> 같으면 사원순으로 정렬 -> 사원명 같으면 연령 순으로 정렬 한다는 뜻이다.

이름이 같은 사원이더라도 소속팀이 다르면 서로 떨어진다. 
따라서 위의 조건을 만족하는 데이터는 리프 블록 전 구간에 흩어진다. 

이 조건으로 검색하면, 인덱스 스캔 시작점을 찾을 수 없고, 어디서 멈춰야 할지도 알 수 없다. 
따라서 인덱스 FUll scan 해야 한다. 

결론적으로 인덱스 Range Scan 하기 위한 가장 첫 번쨰 조건은 인덱스 선두 컬럼이 가공되지 않은 상태로 조건절에 있어야 한다는 것이다. 
선두 컬럼이 가공되지 않은 상태로 조건절에 있으면 인덱스 Range Scan은 무조건 가능하다. 

하지만 인덱스를 Range Scan한다고 해서 항상 성능이 좋은 것은 아니다. 

```

## 실행계획을 보고 인덱스를 잘 탄다고 튜닝을 끝내면 되나? 

```
인덱스를 정말 잘 타는지는 인덱스 리프 블록에서 스캔하는 양을 따져봐야 한다. 

하루에 100만건이 넘는 데이터가 들어오는 테이블에 주문일자와 상품번호를 Like 중간값 검색한다고 하면 데이터는 100만건 이상씩 스캔할 것이다. 
그렇다면 인덱스를 잘 탄다고 말하기 어려울 것이다. 

```

## 인덱스를 이용한 Order By 작업 생략

```
PK 인덱스를 사용하며여 결과집합이 순서대로 출려되면, 
SQL에 ORDER BY가 있어도 정렬 연산이 수행되지 않는다.
정렬 연산을 생략할 수 있게 인덱스가 구성되어 있다면 SORD ORDER BY 연산 단계가 생략된다. 
내림차순일때도 Range Scan Descending으로 수행된다.

```

 ## 인덱스 확장 기능 사용법
 ### Index Range Scan
 ![image](https://user-images.githubusercontent.com/55049159/233653181-7113edb9-48ed-484f-9e84-cf5366c9225d.png)
```
 B*Tree 인덱스의 가장 일반적이고 정상적인 형태의 액세스 방식이다.
 인덱스 루트에서 리프 블록까지 수직적으로 탐색한 후 ‘필요한 범위만’ 스캔한다.
 성능은 인덱스 스캔 범위, 테이블 액세스 횟수를 얼마나 줄일 수 있는냐로 결정된다.
```
### Index Full Scan
![image](https://user-images.githubusercontent.com/55049159/233653239-10648e2c-a641-4c83-8bd0-8b462e1b0303.png)
```
수직적 탐색없이 인덱스 리프 블록을 처음부터 끝까지 수평적으로 탐색하는 방식이다.
```

### Index Unique Scan
![image](https://user-images.githubusercontent.com/55049159/233653318-36dd1aee-fafd-482a-a623-79ef97de4217.png)

```
수직적 탐색으로만 데이터를 찾는 스캔 방식으로서, Unique 인덱스를 ‘=’ 조건으로 탐색하는 경우에 작동한다.
```

### Index Fast Full Scan
```
말 그대로 Index Fast Scan은 Index Full Scan보다 빠르다. 
Index Fast Full Scan이 Index Full Scan보다 빠른 이유는, 논리적인 인덱스 트리 구조를 무시하고 인덱스 세그먼트 전체를 Multiblock I/O 방식으로 스캔하기 때문이다.
```
![image](https://user-images.githubusercontent.com/55049159/233653482-203b1dc0-371d-4a2f-ae3f-a14db2d09c24.png)

### Index Range Scan Descending
```
Index Range Scan과 기본적으로 동일한 스캔 방식이며, 인덱스를 뒤에서부터 앞쪽으로 스캔하기 때문에 내림차순으로 정렬된 결과집합을 얻는다는 점만 다르다.
```
![image](https://user-images.githubusercontent.com/55049159/233653497-c15431e1-945d-47dd-ba5a-1fc2d6399186.png)

