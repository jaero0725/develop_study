# spring_batch
## spring batch 아키텍쳐

![image](https://user-images.githubusercontent.com/55049159/235078783-0020d21d-388f-44a8-8cd9-7b4bee09ffe6.png)

```
  
  Spring batch는 Job bean이 생성되면 JobLauncher에 의해 Job을 실행한다.
  JobLauncher가 job을 실행하고, Job은 Step을 실행하게 된다.
  JobRepository는 DB또는 Memory에 Spring batch가 실행할 수 있도록 Metadata를 관리하는 Class이다.

  Job은 배치의 실행단위
  Job은 N개의 Step을 실행할 수 있으며, 흐름(Flow)를 관리할 수 있다.
  - Job Flow : A step 실행 후 조건에 따라 B Step 또는 C Step을 실행 설정
  
  Step은 Job의 세부 실행 단위이며, N개가 등록되어 실행된다.
  Step의 실행단위는 크게 2가지로 나눌 수 있다.
  1) Chunk 기반 : 하나의 큰 덩어리를 n개씩 나눠서 실행
  2) Task 기반 : 하나의 작업 기반 
  
  Chunk 기반 Step은 ItemReader, ItemProcessor, ItemWriter가 있다.
  - 여기서 Item은 배치 처리 대상 객체를 의미한다.
  
  ItemReader는 배치 처리 대상 객체를 읽어 ItemProcessor 또는 ItemWriter에게 전달한다.
  - 예를 들면 파일 또는 DB에서 데이터를 읽는다.
  
  ItemProcessor는 input
```
