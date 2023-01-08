## 2023.01.15 
### DBA에게 배운 내용
- 페이징 처리 시, 인라인뷰 내부에 쓸데없는 서브쿼리는 밖으로 빼버리는게 효율적
- Merge 문은 select -> update or insert 보다 io를 줄여주므로 효율적이다. 
- IO 줄이기 위해 insert,  update 시 값을 array로 받아서 한번에 처리하는 방식이 단건씩 하는 것 보다 효율적이다. 항상 고려해야 됨
- 항상 DB IO를 줄이는 방식을 생각한다. 
- Index 컬럼 변경안되도록, 특히 SUBSTR 보다는 LIKE문을 사용 
- hint문은 많이 알고 있으면 좋을 듯
- Parallel Processing => 시스템 자원을 많이 쓰이니 협의를 제대로 하고 써야됨, 마지막 수단.
- 다양한 HINT - 엑세스 방식(이번에는 INDEX hint문을 사용), 조인순서, ... 

```sql
select /*+ parallel(8) */ ..
     from TB_NG_IE_USER;
     
/*+ ORDERED INDEX(A "인덱스네임") USE_NL(B) */     
```

=> 90만건에서도 timeout나는 쿼리 -> 0.8초안으로 변경 완료함 

### Query 작성 팁
#### 쿼리 성능 향상을 위한 부분

1. 불필요한 NVl 함수 사용 x
2. 컬럼 명시적으로 작성 ( "*" 지양)
3. WHERE 는 긍정형 조건을 사용
4. OR 사용 가급적 제한
5. <=, >= 보다는 BETWEEN을 사용함
6. NOT의 사용 피하고, NOT은 매우 복잡한 조건에서 선택적으로 사용하는게 좋음. 

