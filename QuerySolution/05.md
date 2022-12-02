## ORACLE JOIN
### 5월 식품들의 총매출 조회하기

<a href="https://school.programmers.co.kr/learn/courses/30/lessons/131117">문제</a>

FOOD_PRODUCT와 FOOD_ORDER 테이블에서 
생산일자가 2022년 5월인 식품들의 식품 ID, 식품 이름, 총매출을 조회
이때 결과는 총매출을 기준으로 내림차순 정렬해주시고 총매출이 같다면 식품 ID를 기준으로 오름차순 정렬해주세요.

- ☑️ 정답
```sql
    SELECT A.PRODUCT_ID, A.PRODUCT_NAME, (PRICE * B.AMOUNT) AS TOTAL_SALES FROM FOOD_PRODUCT A 
    JOIN
     (
     SELECT b.PRODUCT_ID, SUM(b.AMOUNT) as AMOUNT FROM FOOD_ORDER b
     WHERE TO_CHAR(b.produce_date,'YYYYMM') = '202205' 
     GROUP BY b.PRODUCT_ID
     ) B 
     ON A.PRODUCT_ID = B.PRODUCT_ID
     ORDER BY TOTAL_SALES DESC, A.PRODUCT_ID ASC;
```



