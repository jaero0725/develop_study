package fc.spring.batch.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchMysqlApplication.class, args);
    }

}
