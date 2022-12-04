### Query 작성 팁

#### 쿼리 성능 향상을 위한 부분

1. 불필요한 NVl 함수 사용 x
- NULL value에 대한 오류를 방지하기 위하여 불필요한 NVL 함수를 사용하여 DBMS의 부하 유발함. 

```sql
# (1) NVL() : 100 + NVL() : 100 + SUM() : 1 = 201번 
 SELECT SUM(ISNULL(COL1, 0) + ISNULL(COL2, 0)) FROM TABLE_A;
# (2) SUM() : 1 + SUM() : 1+NVL() : 2 = 4번
 SELECT ISNULL(SUM(COL1),0) + ISNULL(SUM(COL2, 0) FROM TABLE_A;
```

> (1)의 경우 함수사용 횟수는 NVL() : 100 + NVL() : 100 + SUM() : 1 = 201번 <br>
> (2)의 경우 함수사용 횟수는 SUM() : 1 + SUM() : 1+NVL() : 2 = 4번 <br>
> <strong> (1) 보다는 (2)가 효율적이다.</strong> <br>
> * SUM() 함수의 경우 NULL Value는 계산시 포함하지 않으므로 오류가 발생하지 않음. 

2. 컬럼 명시적으로 작성 ( "*" 지양)
3. WHERE 는 긍정형 조건을 사용
4. OR 사용 가급적 제한
5. <=, >= 보다는 BETWEEN을 사용함
6. NOT의 사용 피하고, NOT은 매우 복잡한 조건에서 선택적으로 사용하는게 좋음. 

