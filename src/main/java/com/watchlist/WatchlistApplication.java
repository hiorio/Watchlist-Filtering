package com.watchlist;

import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class WatchlistApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(WatchlistApplication.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importWatchlistJob;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private JobRepository jobRepository;

    public static void main(String[] args) {
        logger.info("Starting WatchlistApplication...");
        SpringApplication.run(WatchlistApplication.class, args);
        logger.info("WatchlistApplication started successfully.");
    }

    @Override
    public void run(String... args) {
        try {
            logger.info("Running importWatchlistJob...");

            // Delete previous job executions
            List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(importWatchlistJob.getName(), 0, Integer.MAX_VALUE);
            for (JobInstance jobInstance : jobInstances) {
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
                for (JobExecution jobExecution : jobExecutions) {
                    jobExecution.setEndTime(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
                    jobExecution.setStatus(BatchStatus.ABANDONED);
                    jobRepository.update(jobExecution);
                }
            }

            // Run the job
            jobLauncher.run(importWatchlistJob,
                    new JobParametersBuilder().addDate("runDate", new Date())
                            .toJobParameters());
            logger.info("importWatchlistJob completed.");
        } catch (Exception e) {
            logger.error("Failed to run importWatchlistJob", e);
        }
    }
}
