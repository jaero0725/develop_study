# [1] 과제 - CSV파일 데이터를 읽어 MYSQL DB에 데이터를 저장하는 배치 개발
## 요구사항
```
  Reader 
  - 100개 이상의 person data를 csv파일에서 읽는다.
  
  Processor 
  - allow_duplicate 파라미터로 person.name의 중복 여부 조건을 판단한다.
  - 'allow_duplicate = true' 인 경우 모든 person을 return한다.
  - 'allow_duplicat=false 또는 null'인 경우 person.name이 중복된 데이터는 null로 return 한다.
  - 힌트 : 중복 체크는 'java.util.Map' 사용
  
  Writer 
  - 2개의 ItemWriter를 사용해서 Person Mysql DB에 저장 후 몇 건 저장 됐는지 log를 찍는다.
  - Person 저장 ItemWrite와 log출력 ItemWriter 
  - 힌트 'CompositeItemWriter' 사용 
```
