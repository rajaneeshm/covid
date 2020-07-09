package com.evalution.covid.batch;

import com.evalution.covid.dto.CovidCaseDTO;
import com.evalution.covid.entity.CovidCase;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.Map;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private CovidCaseProcessor covidCaseProcessor;
    @Autowired
    private CovidCaseSkipListner covidCaseSkipListner;
    @Autowired
    private CovidCaseWriteListener covidCaseWriteListener;


    @Bean
    @StepScope
    public JsonItemReader<CovidCaseDTO> jsonCovidCaseReader(@Value("#{jobParameters}") Map jobParameters) {
        return new JsonItemReaderBuilder<CovidCaseDTO>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(CovidCaseDTO.class))
                .resource(new ClassPathResource(((String) jobParameters.get("file"))))
                .name("CovidCaseJsonItemReader").build();
    }

    @Bean
    public JpaItemWriter covidCaseWriter() {
        JpaItemWriter writer = new JpaItemWriter();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public Step step1(JpaItemWriter<CovidCase> writer) {

        return stepBuilderFactory.get("step1")
                .<CovidCaseDTO, CovidCase>chunk(100)
                .reader(jsonCovidCaseReader(null))
                .processor(covidCaseProcessor)
                .writer(writer)
                .listener(covidCaseWriteListener)
                .faultTolerant().skipLimit(100)
                .skip(ConstraintViolationException.class)
                .skip(Exception.class)
                .listener(covidCaseSkipListner)
                .build();
    }

    @Bean
    public Job covidCaseImportJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("covidCaseImportJob").
                incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1).end().build();
    }

}