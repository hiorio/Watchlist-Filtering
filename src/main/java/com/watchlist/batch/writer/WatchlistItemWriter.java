package com.watchlist.batch.writer;

import com.watchlist.model.Watchlist;
import com.watchlist.service.WatchlistService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class WatchlistItemWriter implements ItemWriter<Watchlist> {

    private final WatchlistService watchlistService;

    public WatchlistItemWriter(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @Override
    public void write(Chunk<? extends Watchlist> items) throws Exception {
        for (Watchlist item : items) {
//            System.out.println("Writing item: " + item);
            watchlistService.saveWatchlist(item);
        }
    }
}
