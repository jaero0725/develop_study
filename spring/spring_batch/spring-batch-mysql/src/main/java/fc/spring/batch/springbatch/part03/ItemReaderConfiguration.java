package fc.spring.batch.springbatch.part03;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    Custom 한 ItemReader를 만듦.
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ItemReaderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job itemReaderJob() throws Exception {
        return jobBuilderFactory.get("itemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(this.customItemReaderStep())
                .next(this.csvFileStep())
                .next(this.jdbcStep())
                .next(this.jpaStep())
                .build();
    }

    @Bean
    public Step customItemReaderStep() {
        return stepBuilderFactory.get("customItemReaderStep")
                .<Person, Person> chunk(10)
                .reader(new CustomItemReader<>(getItems()))
                .writer(itemWriter())
                .build();
    }
    @Bean
    public Step csvFileStep() throws Exception {
        return stepBuilderFactory.get("csvFileStep")
                .<Person, Person> chunk(10)
                .reader(this.csvFileItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step jdbcStep() throws Exception{
        return stepBuilderFactory.get("jdbcStep")
                .<Person, Person> chunk(10)
                .reader(this.jdbcCursorItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step jpaStep() throws Exception{
        return stepBuilderFactory.get("jpaStep")
                .<Person, Person> chunk(10)
                .reader(this.jpaCursorItemReader())
                .writer(itemWriter())
                .build();
    }

    private JpaCursorItemReader<Person> jpaCursorItemReader() throws Exception {
        JpaCursorItemReader <Person> itemReader=  new JpaCursorItemReaderBuilder<Person>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select p from person p")
                .build();

        itemReader.afterPropertiesSet();    // 정상적으로 설정됐는지 검증하는 메서드
        return itemReader;
    }

    private JdbcCursorItemReader<Person> jdbcCursorItemReader() throws Exception {
        JdbcCursorItemReader <Person> itemReader=  new JdbcCursorItemReaderBuilder<Person>()
                .name("jdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("select id, name, age, address from person")
                .rowMapper((rs, rowNum) -> new Person(rs.getInt(1),rs.getString(2),
                        rs.getString(3),rs.getString(4)))
                .build();

        itemReader.afterPropertiesSet();    // 정상적으로 설정됐는지 검증하는 메서드
        return itemReader;
    }
    private FlatFileItemReader<Person> csvFileItemReader() throws Exception {

        // csv -> Pesron 객체로 매핑
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();// 데이터 읽을 수 있는 설정 -> line mapper 객체 생성
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("id", "name", "age", "address");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            int id =fieldSet.readInt("id");
            String name = fieldSet.readString("name");
            String age = fieldSet.readString("age");
            String address =  fieldSet.readString("address");

            return new Person(id,name,age,address);
        });

        FlatFileItemReader<Person> itemReader = new FlatFileItemReaderBuilder<Person>()
                .name("csvFileItemReader")
                .encoding("UTF-8")
                .resource(new ClassPathResource("test.csv"))       //resources 밑에 파일을 읽을 수 있는 클래스
                .linesToSkip(1) // 첫 row는 필드명, skip한다는 뜻.
                .lineMapper(lineMapper) //line 한 줄씩 읽도록
                .build();

        itemReader.afterPropertiesSet();    // 정상적으로 설정됐는지 검증하는 메서드

        /*
            이 기종간의 통신시 DB 데이터를 받을 수 없어, 이렇게 사용하는 경우가 많다.
         */
        return itemReader;
    }


    private ItemWriter< Person> itemWriter() {
        return items -> log.info(items.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", ")));
    }

    private List <Person> getItems() {
        List<Person> items = new ArrayList<>();

        for(int i = 0 ;i <10; i++){
            items.add(new Person(i+1, "test name" + i, "test age", "test address"));
        }

        return items;
    }

}
