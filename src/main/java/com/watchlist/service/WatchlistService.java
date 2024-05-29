package com.watchlist.service;

import com.watchlist.model.Watchlist;
import com.watchlist.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public void saveWatchlist(Watchlist watchlist) {
        System.out.println("Saving watchlist: " + watchlist);
        watchlistRepository.save(watchlist);
    }
}
