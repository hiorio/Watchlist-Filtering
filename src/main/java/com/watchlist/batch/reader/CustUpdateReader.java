package com.watchlist.batch.reader;

import com.watchlist.model.Watchlist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustUpdateReader {

    private static final Logger logger = LoggerFactory.getLogger(CustUpdateReader.class);

    @Autowired
    private TxtFileReader txtFileReader;

    @Autowired
    private XlsxFileReader xlsxFileReader;

    @Bean
    public ItemReader<Watchlist> reader() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:watchlist/*");
            List<Watchlist> watchlist = new ArrayList<>();
            logger.info("Found {} files in watchlist directory.", resources.length);
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                logger.info("Processing file: {}", filename);
                if (filename != null && filename.endsWith(".txt")) {
                    watchlist.addAll(txtFileReader.readFile(resource));
                } else if (filename != null && filename.endsWith(".xlsx")) {
                    watchlist.addAll(xlsxFileReader.readFile(resource));
                }
            }
            logger.info("Total watchlist items read: {}", watchlist.size());
            return new ListItemReader<>(watchlist);
        } catch (Exception e) {
            logger.error("Failed to read files", e);
            throw new RuntimeException("Failed to read files", e);
        }
    }
}
