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
