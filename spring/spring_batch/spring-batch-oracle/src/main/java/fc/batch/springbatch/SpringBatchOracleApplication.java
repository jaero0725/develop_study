package fc.batch.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchOracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchOracleApplication.class, args);
    }

}
