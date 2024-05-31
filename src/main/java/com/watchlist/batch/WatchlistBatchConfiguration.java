package com.watchlist.batch;

import com.watchlist.batch.processor.CustUpdateProcessor;
import com.watchlist.batch.reader.CustUpdateReader;
import com.watchlist.batch.writer.CustUpdateWriter;
import com.watchlist.model.Cust;
import com.watchlist.model.Watchlist;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class WatchlistBatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustUpdateReader custUpdateReader;
    private final CustUpdateProcessor custUpdateProcessor;
    private final CustUpdateWriter custUpdateWriter;

    public WatchlistBatchConfiguration(JobRepository jobRepository,
                                       PlatformTransactionManager transactionManager,
                                       CustUpdateReader custUpdateReader,
                                       CustUpdateProcessor custUpdateProcessor,
                                       CustUpdateWriter custUpdateWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.custUpdateReader = custUpdateReader;
        this.custUpdateProcessor = custUpdateProcessor;
        this.custUpdateWriter = custUpdateWriter;
    }

    @Bean
    public Job importWatchlistJob() {
        return new JobBuilder("importWatchlistJob", jobRepository)
                .start(updateCustStep())
                .build();
    }

    @Bean
    public Step updateCustStep() {
        return new StepBuilder("updateCustStep", jobRepository)
                .<Watchlist, Cust>chunk(10, transactionManager)
                .reader(custUpdateReader.reader())
                .processor(custUpdateProcessor)
                .writer(custUpdateWriter)
                .build();
    }
}
