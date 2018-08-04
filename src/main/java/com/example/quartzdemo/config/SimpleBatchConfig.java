package com.example.quartzdemo.config;


import com.example.quartzdemo.jobs.AnotherSimpleBatchJob;
import com.example.quartzdemo.jobs.SimpleBatchJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SimpleBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step stepOne() {
        return stepBuilderFactory
                .get("stepOne")
                .tasklet(new SimpleBatchJob())
                .build();
    }

    @Bean
    public Step stepTwo() {
        return stepBuilderFactory
                .get("stepTwo")
                .tasklet(new AnotherSimpleBatchJob())
                .build();
    }

    @Bean
    public Job job1() {
        return jobBuilderFactory
                .get("job1")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

}
