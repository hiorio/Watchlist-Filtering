package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileReader {
    List<Watchlist> readFile(Resource resource) throws Exception;
}