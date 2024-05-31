package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TxtFileReader implements FileReader {

    private static final Logger logger = LoggerFactory.getLogger(TxtFileReader.class);

    @Override
    public List<Watchlist> readFile(Resource resource) throws Exception {
        List<Watchlist> watchlist = new ArrayList<>();
        InputStream is = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("\\|\\|\\|");
            if (tokens.length == 3) {
                watchlist.add(new Watchlist(tokens[0].trim(), formatDate(tokens[1].trim()), tokens[2].trim()));
            } else {
                logger.warn("Skipping line due to incorrect format: {}", line);
            }
        }
        logger.info("Total items read from txt file {}: {}", resource.getFilename(), watchlist.size());
        return watchlist;
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
