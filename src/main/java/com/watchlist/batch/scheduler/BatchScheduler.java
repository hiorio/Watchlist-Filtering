package com.watchlist.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Component
public class BatchScheduler {
    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private final JobLauncher jobLauncher;
    private final Job importWatchlistJob;

    public BatchScheduler(JobLauncher jobLauncher, Job importWatchlistJob) {
        this.jobLauncher = jobLauncher;
        this.importWatchlistJob = importWatchlistJob;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void runBatchJob() {
        try {
            logger.info("Running batch job...");
            jobLauncher.run(importWatchlistJob, new JobParametersBuilder()
                    .addDate("startAt", new Date())
                    .toJobParameters());
            logger.info("Batch job completed successfully.");
        } catch (Exception e) {
            logger.error("Batch job failed", e);
        }
    }
}
