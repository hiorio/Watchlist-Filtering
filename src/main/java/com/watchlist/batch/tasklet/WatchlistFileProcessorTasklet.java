package com.watchlist.batch.tasklet;

import com.watchlist.batch.WatchlistItemProcessor;
import com.watchlist.batch.reader.WatchlistItemReader;
import com.watchlist.batch.writer.WatchlistItemWriter;
import com.watchlist.model.Watchlist;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WatchlistFileProcessorTasklet implements Tasklet {

    private final WatchlistItemReader itemReader;
    private final WatchlistItemProcessor itemProcessor;
    private final WatchlistItemWriter itemWriter;

    public WatchlistFileProcessorTasklet(WatchlistItemReader itemReader, WatchlistItemProcessor itemProcessor, WatchlistItemWriter itemWriter) {
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Watchlist item;
        List<Watchlist> items = new ArrayList<>();
        while ((item = itemReader.read()) != null) {
//            System.out.println("Reading item: " + item);
            item = itemProcessor.process(item);
//            System.out.println("Processed item: " + item);
            items.add(item);
        }
        if (items.isEmpty()) {
            System.out.println("No items to write.");
        } else {
//            System.out.println("Writing items: " + items);
            itemWriter.write(new Chunk<>(items));
        }
        return RepeatStatus.FINISHED;
    }
}
