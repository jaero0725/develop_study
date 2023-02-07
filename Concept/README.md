## 📋 Oracle 개념 정리 
### 물리적 저장 구조와 인덱스
- 고정길이 / 가변길이(블록 내 저장방식) : Index를 사용해서 위치를 저장(offset 위치저장)
- Clustering : 검색속도 향상 / 자주검색되는 필드를 기준
- 인덱스 : 파일 내 레코드 위치블 발리 찾기 위함, 인덱스가 없으면 순차 검색 해야함

1) 검색 필드로 사전정렬(간단한 방법) => 검색필드 종류가 많아지면 별도의 검색을 위한 자료구조를 생성한다. 
2) 이진 검색(Binary Search)

```sql
(1) student 테이블 레코드 10000개, dept_id가 '920' 500개? => 500개 레코드만 검사 

SELECT * FROM STUDENT 
WHERE DEPT_ID ='920' AND ADDRESS LIKE '서울%';

(2) 
SELECT COUNT(*) FROM STUDENT S, DEPARTMENT D
WHERE 
  S.DEPT_ID = D.DEPT_ID
  AND ADDRESS = '서울';

=> 인덱스가 없을 경우 모든 레코드 순차 검색 100000 * 1000
=> student dept_id 필드에 인덱스가 있을 경우 department 테이블 레코드수만큼 검색 

```

## 📋 Partition Table 
### Partition
![image](https://user-images.githubusercontent.com/55049159/217259224-b6656c55-7b15-4fd8-8d08-e53abbdef084.png)

``` shell

 오라클 파티션테이블은 하나의 큰 테이블을 물리적으로 나눠놓은 것입니다.
 물리적으로 나눠놨지만, 논리적으로는 하나의 테이블로 간주됩니다.
 왼쪽 그림처럼 Sales 테이블에 1월데이터부터 5월데이터까지 하나의 통테이블에 몰아서 넣을수 있습니다.
 하지만, 파티션테이블을 사용하게되면 오른쪽 그림처럼 월마다 다른 세그먼트에 Sales 데이터를 나눠서 넣을 수 있습니다.
 이렇게 나눠서 넣어놔도 사용자는 1월~5월치 데이터가 마치 하나의 Sales 테이블에 들어있는 것처럼 사용할 수 있습니다.
 오라클에서는 Object, Segment 라는 개념을 사용합니다. 저장공간을 가지는 개념이 Segment 에 해당합니다.
 즉, 위 파티션된 Sales 테이블에서는 5개의 Segment 에 데이터가 나뉘어 들어가게 됩니다.
 각각 세그먼트가 다르기 때문에 1월데이터만 압축해서 보관하거나, 5월데이터만 좀더 빠른 디스크에 저장할 수 있습니다.
 
 # 목적
 1) 관리의 편의성
 1월 데이터만 삭제할때, 파티션이 없는 경우 전체 데이터를 읽어 건건히 DELETE 를 해준다.
 하지만, 파티션을 월별로 생성한 경우(Range Partition) Truncate, Drop을 해주면 빠르게 작업을 처리할 수 있다.
 관리가 편해진다.
 
 2) 성능
 인덱스를 사용안해서 Full scan 할경우 예를 들어 5월 4일 을 찾는다 할 경우 파티션 없으면 전체 데이터 조회하므로 느려진다. 
 하지만 파티션 테이블에서는 5월 만 조회하여 찾기 떄문에 빠르게 작업을 할 수 있다. 
 
 Hash 파티션은 Range, List 파티션과 다르게 성능 보다는 Dist I/O 분산 효과를 노린다고 한다. 
 
 # 파티션 Key 
 파티션을 나눌때 기준이 되는 키컬럼(Key Column)을 파티션키라고 부릅니다. 당연히 테이블에 있는 컬럼들 중에서 선택됩니다.
 파티션키로는 Lob을 제외한 Number, Date, Varchar 타입이 모두 가능하고, 여러개의 컬럼으로 구성할 수도 있습니다.
 
 # 파티션을 사용하는 경우?, 언제 파티션 테이블을 사용해야되나? 
 1)  2GB 이상인 테이블
 - 최소한 크기가 2GB 이상의 테이블의 경우 파티션을 사용하는 것을 고려해보기
 
 2) 이력 데이터 
 - 로그 데이터 History 데이터일 경우 파티션을 사용해 보는 것을 고려.. 지속적으로 계속 늘어나는 데이터면서 과거 데이터가 변경이 없는 경우
 
 3) 테이블의 데이터를 여러종류의 스토리지에 나눠서 저장할 필요가 있는 경우
 - 자주 쓰는 데이터는 고가의 빠른 스토리지
 - 잘 안쓰는 데이터는 저가의 느린 스토리지
 
 # 파티션의 종류 
 1) Range Partition
 - Range Partition 은 범위로 구분되는 파티션 테이블 입니다.
 - 범위(Range)에는 숫자, 날짜, 문자가 모두 가능합니다.
 
  - 이런식으로 생성
   create table SALES (
    sales_no       number,
    sale_year      number,
    sale_month     number,
    sale_day       number,
    customer_name  varchar2(30),
    birth_date     date,
    price          number
  )
  partition by range (sales_no)
  (
    partition SALES_P1 values less than (3),
    partition SALES_P2 values less than (5),
    partition SALES_P3 values less than (maxvalue)
  );
  
  - 나는 이렇게 사용함 
  CREATE TABLE ZEROCO.TB_ZERO2179S(
    PRC_DT  CHAR(8) NOT NULL
    ...
  )
  TABLE SPACE TSD_ZEROCO01
  NOLOGGING
  PARTITON BY RANGE(PRC_DT)
  (
    PARTITON TSD_Z202201 VALUES LESS THAN ('202201')
    TABLESPACE TSD_ZERCO01
    STORAGE
    (
      INITIAL 64K
      NEXT 1M
    ),
    ...
  );
  CREATE INDEX ...
  (
  ...
  ) LOCAL
  ...
  
 2) List Partition
 3) Hash Partition
 
```

