package fc.srping.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing  //Batch Processing 을 하겠다는 어노테이션
@SpringBootApplication
public class SpringBatchExApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchExApplication.class, args);
    }

}
