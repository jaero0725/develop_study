package fc.spring.batch.springbatch.part03;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DuplicateValidationConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private HashMap<String, Person> personMap = new HashMap<>();

    /*
      <요구사항>

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

     */
    @Bean
    public Job validJob() throws Exception {
        return jobBuilderFactory.get("validJob")
                .incrementer(new RunIdIncrementer())
                .start(this.vaildStep())
                .build();
    }

    @Bean
    public Step vaildStep() throws Exception {
        return stepBuilderFactory.get("vaildStep")
                .<Person, Person>chunk(10)
                .reader(this.personItemReader())
                .processor(this.personItemProcessor(false))
                .writer(this.personJdbcWriter())
                .build();
    }


    // 1. itemReader를 통해 csv파일에서 person data를 읽음
    // FlatFileItemReader을 이용해 csv파일 매핑
    private FlatFileItemReader<Person> personItemReader() throws Exception {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "name", "age", "address");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            int id =fieldSet.readInt("id");
            String name = fieldSet.readString("name");
            String age = fieldSet.readString("age");
            String address =  fieldSet.readString("address");

            return new Person(id, name, age, address);
        });

        FlatFileItemReader<Person> itemReader = new FlatFileItemReaderBuilder<Person>()
                .name("csvFileItemReader")
                .encoding("UTF-8")
                .resource(new ClassPathResource("test02.csv"))
                .linesToSkip(1)
                .lineMapper(lineMapper)
                .build();

        itemReader.afterPropertiesSet();    // 정상적으로 설정됐는지 검증하는 메서드

        return itemReader;
    }

    //2. ItemProcessor - 중복 여부 판단.
    @Bean
    @StepScope
    public ItemProcessor<Person, Person> personItemProcessor(@Value("#{jobParameters[allow_duplicate]}") boolean isAllow) {
            return new ItemProcessor<Person, Person>() {
                @Override
                public Person process(Person item) throws Exception {
                    String name = item.getName();
                    if (personMap.containsKey(name)) {
                        return null;
                    } else {
                        personMap.put(name, item);
                    }
                    return item;
                }
            };
    }

    //3. personJdbcWriter mysql등록 + log 출력
    private JdbcBatchItemWriter<Person> personJdbcWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO Person (id, name, age, address) VALUES (:id, :name, :age, address)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }
}
