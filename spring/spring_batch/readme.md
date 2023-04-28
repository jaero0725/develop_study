# Spring Batch
## :books: Spring batch Architecture
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
  1) Chunk 기반 : 하나의 큰 덩어리를 n개씩 나눠서 실행 (10000개의 data를 한번 1000개씩 10번 나눠서 처리)
  2) Task 기반 : 하나의 작업 기반 
  
  # Chunk 기반 Step은 ItemReader, ItemProcessor, ItemWriter가 있다.
  - 여기서 Item은 배치 처리 대상 객체를 의미한다.
  
  ItemReader는 배치 처리 대상 객체를 읽어 ItemProcessor 또는 ItemWriter에게 전달한다.
  - 예를 들면 파일 또는 DB에서 데이터를 읽는다.
  
  ItemProcessor는 input객체를 output객체로 filtering 또는 Processing 해 ItemWriter에게 전달한다.
  ItemProcessor는 필수가 아닌다. 
  - 예를 들면, ItemReader에서 읽은 데이터를 수정 또는 ItemWriter 대상인지 filtering 한다.
  - ItemProcessor는 optional 하다.
  - ItemProcessor가 하는 일을 ItemReader 또는 ItemWriter가 대신할 수 있따.
  
  ItemWriter는 배치 처리 대상 객체를 처리한다.
  - DB update를 하거나 처리 대상 사용자에게 알림을 보낸다. 
  
```

## Spring batch metatable
![image](https://user-images.githubusercontent.com/55049159/235088181-9abdb129-981b-4be1-9729-d5963d360ae7.png)

```
  배치 실행을 위한 메타 데이터가 저장되는 테이블 
  
  ~ JOB 
  BATCH_JOB_INSTANCE
  - Job이 실행되며 생성되는 최상위 계층의 테이블
  - job_name과 job_key를 기준으로 하나의 row가 생성되며, 같은 job_name과 job_key가 저장될 수 없다.
  - job_key는 BATCH_JOB_EXECUTION_PARAMS에 저장되는 Parameter를 나열해 암호화해 저장한다. 
 
  BATCH_JOB_EXECUTION 
  - Job이 실행되는 시작/종료시간, job 상태 등을 관리
  
  BATCH_JOB_EXECTUION_PARAMS
  - Job을 실행하기 위해 주입된 parameter 정보 저장
  
  BATCH_JOB_EXECTUION_CONTEXT
  - Job이 실행되며 공유해야할 데이터를 직렬화해 저장
  
  ~ STEP
  BATCH_STEP_EXECUTION
  - Step이 실행되는 동안 필요한 데이터 또는 실행된 결과 저장
  
  BATCH_STEP_EXECUTION_CONTEXT
  - Step이 실행되며 공유해야할 데이터를 직렬화해 저장
  

  
```

![image](https://user-images.githubusercontent.com/55049159/235090670-562daab0-3365-45a5-aed7-f663f27a609d.png)

```
  spring-batch-core/org.springframework/batch/core/* 에 위치
   
  schema.sql설정
  - schema-**.sql의 실행 구분은 DB종류별로 script가 구분
  - spring.batch.initialize-schema config로 구분한다.
  - ALWAYS(항상 실행) , EMBEDDED(내장 DB일 때만 실행/DEFAULT), NEVER(항상 실행안함)로 관리하다.
```
