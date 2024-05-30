package com.watchlist.batch;

import com.watchlist.model.Watchlist;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WatchlistItemProcessor implements ItemProcessor<Watchlist, Watchlist> {

    @Override
    public Watchlist process(Watchlist item) throws Exception {
        // 이미 파일 읽어올때 formatDate 했는데 왜 또 하나욤 ? ?
        item.setBirthday(formatDate(item.getBirthday()));
        return item;
    }

    private String formatDate(String date) throws ParseException {
        SimpleDateFormat[] dateFormats = new SimpleDateFormat[]{
                new SimpleDateFormat("yyyyMMdd"),
                new SimpleDateFormat("yyMMdd"),
                new SimpleDateFormat("yyyy.MM.dd"),
                new SimpleDateFormat("yy.MM.dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("yy/MM/dd"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yy-MM-dd")
        };

        for (SimpleDateFormat dateFormat : dateFormats) {
            try {
                Date parsedDate = dateFormat.parse(date);
                return new SimpleDateFormat("yyMMdd").format(parsedDate);
            } catch (ParseException ignored) {
            }
        }
        throw new ParseException("Unparseable date: \"" + date + "\"", 0);
    }
}
