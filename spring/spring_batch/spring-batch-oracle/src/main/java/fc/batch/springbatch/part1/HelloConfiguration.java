package fc.batch.springbatch.part1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HelloConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public HelloConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    // Sring Batch에서 Job은 배치의 실행 단위
    // JobBuilderFactory는 Bean으로 생성되어 있어, 생성자 주입으로 받을 수 있음
    // Job은 실행단위를 구분할 수 있는 incrementer와 job의 name step을 설정
    // RunIdIncrementer는 Job이 실행될때 마다 parameter id를 자동으로 생성해주는 Class이다.
    // job name은 helloJob => name은 spring batch를 실행 할 수 있는 Key
    // start => 최초로 실핼 메서드
    @Bean
    public Job helloJob(){
        log.info("##### [JOB 1] helloJob start");
        return jobBuilderFactory.get("helloJob")
                .incrementer(new RunIdIncrementer()) // RunId라는 Sequencial하게 insert?
                .start(this.helloStep())
                .build();
    }

    /*
        StepExecution : BATCH_STEP_EXECUTION
        ExectuionContext : BATH_STEP_EXECUTON_CONTEXT

     */
    // step은 Job의 실행단위 한개의 job은 N개의 step을 갖는다.
    // step name
    // tasklet => step의 실행단위 (task 기반)
    @Bean
    public Step helloStep(){
        return stepBuilderFactory.get("helloStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("##### [STEP 1] RUN hello spring batch");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    // 어떤 Job을 실행할지에 대한 설정이 필요.
    // env : --spring.batch.job.names=helloJob
}
