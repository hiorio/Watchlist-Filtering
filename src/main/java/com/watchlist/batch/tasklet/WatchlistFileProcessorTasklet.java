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

        // List<Watchlist> items 이게
        // ItemReader 에서 만들어진 놈을 여기에서도 쓰려고 로컬변수 선언 + while 문 돌면서 add 하는 것 같은데 (??)
        // List<Watchlist> items = new ArrayList<>(); <- 이 필드를 가지고 있는 컴포넌트를 하나 만들고
        // ItemReader 랑 여기에서 저 items 를 get 해서 사용하면 싱글톤 지켜짐 + while문 돌 필요 없음
        // + ItemReader.reader() 랑 이터레이터도 필요 없지 않을까 ? ? ? ?

        // 위처럼 고칠 수 있따면. 사실 reader / processor / writer 도 의미 없어 보임
        // tasklet 말고 chunk step 으로 만든다면 그나마 의미 있을 것 같은데 너무 귀찮을 것 같다 . . .,,!!

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
