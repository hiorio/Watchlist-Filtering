package com.watchlist.batch;

import com.watchlist.batch.tasklet.CustUpdateTasklet;
import com.watchlist.batch.tasklet.DatabaseSetupTasklet;
import com.watchlist.batch.tasklet.WatchlistFileProcessorTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.step.tasklet.TaskletStep;

@Configuration
public class WatchlistBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DatabaseSetupTasklet databaseSetupTasklet;
    private final WatchlistFileProcessorTasklet watchlistFileProcessorTasklet;
    private final CustUpdateTasklet custUpdateTasklet;

    public WatchlistBatchConfiguration(JobRepository jobRepository,
                                       PlatformTransactionManager transactionManager,
                                       DatabaseSetupTasklet databaseSetupTasklet,
                                       WatchlistFileProcessorTasklet watchlistFileProcessorTasklet,
                                       CustUpdateTasklet custUpdateTasklet) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.databaseSetupTasklet = databaseSetupTasklet;
        this.watchlistFileProcessorTasklet = watchlistFileProcessorTasklet;
        this.custUpdateTasklet = custUpdateTasklet;
    }

    @Bean
    public Job importWatchlistJob() {
        SimpleJob job = new SimpleJob("importWatchlistJob");
        job.setJobRepository(jobRepository);
        job.addStep(databaseSetupStep());
        job.addStep(processWatchlistStep());
        job.addStep(updateCustStep());
        return job;
    }

    @Bean
    public Step databaseSetupStep() {
        TaskletStep step = new StepBuilder("databaseSetupStep", jobRepository)
                .tasklet(databaseSetupTasklet, transactionManager)
                .build();
        return step;
    }

    @Bean
    public Step processWatchlistStep() {
        TaskletStep step = new StepBuilder("processWatchlistStep", jobRepository)
                .tasklet(watchlistFileProcessorTasklet, transactionManager)
                .build();
        return step;
    }

    @Bean
    public Step updateCustStep() {
        TaskletStep step = new StepBuilder("updateCustStep", jobRepository)
                .tasklet(custUpdateTasklet, transactionManager)
                .build();
        return step;
    }
}
