package com.watchlist.batch.writer;

import com.watchlist.model.Cust;
import com.watchlist.repository.CustRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustUpdateWriter implements ItemWriter<Cust> {

    private static final Logger logger = LoggerFactory.getLogger(CustUpdateWriter.class);
    private final CustRepository custRepository;

    public CustUpdateWriter(CustRepository custRepository) {
        this.custRepository = custRepository;
    }

    @Override
    public void write(Chunk<? extends Cust> items) {
        if (items.isEmpty()) {
            logger.info("No items to write.");
        } else {
            logger.info("Writing {} items.", items.size());
            for (Cust item : items) {
                logger.info("Saving item: {}", item);
            }
            custRepository.saveAll(items);
            logger.info("Items saved successfully.");
        }
    }
}
