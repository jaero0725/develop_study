/**
 job을 실행시키는 클래스가 jobLauncher를 사용하여 job을 실행시키고,
 - 이떄 파라미터 를 넘겨준다. jobParameters, job 2개 요소를 전달받아 실행한다.
 - jobParmeters는 Key, value 쌍으로 이루어져 있다.
 - 그리고 데이터베이스에 이값들이 저장이 된다.
 - 필요시 jobParameters를 참조해서 사용할 수 있다.

 jobParameter는 job을 실행할 떄 함께 포함되어 사용되는 파라미터를 가진 도메인 객체
 하나의 Job에 존재할 수 잇는 여러개의 JobInstance를 구분하기 위한 용도이다.
 jobParameters와 jobInstance는 1:1관계이다. 

 오직 유일한 jobParameterㄱ
 
 # 생성 및 바인딩 법
 1. 어플리케이션 실행시 주입하는법
 2.  코드로 생성하는법
 - JobParametersBuilder를 사용하여 생성한다.
 3. SpEl을 이용하는 법
 - jobParameters에 SpEl을 사용하여 값을 바인딩할 수 있다.
 - @Value("#{jobParameter[requestDate}"), @JobScope, @StepScope 선언은 필수이다.
 
 BATCH_JOB_EXECUTION_PARAM 테이블과 매핑 
 JOB_EXECUTION과 1:M의 관계 
