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
| 오라클 파티션테이블은 하나의 큰 테이블을 물리적으로 나눠놓은 것입니다.
| 물리적으로 나눠놨지만, 논리적으로는 하나의 테이블로 간주됩니다.
| 왼쪽 그림처럼 Sales 테이블에 1월데이터부터 5월데이터까지 하나의 통테이블에 몰아서 넣을수 있습니다.
| 하지만, 파티션테이블을 사용하게되면 오른쪽 그림처럼 월마다 다른 세그먼트에 Sales 데이터를 나눠서 넣을 수 있습니다.
| 이렇게 나눠서 넣어놔도 사용자는 1월~5월치 데이터가 마치 하나의 Sales 테이블에 들어있는 것처럼 사용할 수 있습니다.
| 오라클에서는 Object, Segment 라는 개념을 사용합니다. 저장공간을 가지는 개념이 Segment 에 해당합니다.
| 즉, 위 파티션된 Sales 테이블에서는 5개의 Segment 에 데이터가 나뉘어 들어가게 됩니다.
| 각각 세그먼트가 다르기 때문에 1월데이터만 압축해서 보관하거나, 5월데이터만 좀더 빠른 디스크에 저장할 수 있습니다.
```

