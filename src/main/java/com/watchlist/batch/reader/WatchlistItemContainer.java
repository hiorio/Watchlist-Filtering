package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WatchlistItemContainer {
    private final List<Watchlist> items = new ArrayList<>();

    public List<Watchlist> getItems() {
        return items;
    }

    public void addItem(Watchlist item) {
        items.add(item);
    }

    public void addItems(List<Watchlist> items) {
        this.items.addAll(items);
    }

    public int getSize() {
        return items.size();
    }
}
