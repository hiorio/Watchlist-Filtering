package com.watchlist.batch.processor;

import com.watchlist.model.Cust;
import com.watchlist.model.Watchlist;
import com.watchlist.repository.CustRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustUpdateProcessor implements ItemProcessor<Watchlist, Cust> {
    private static final Logger logger = LoggerFactory.getLogger(CustUpdateProcessor.class);
    private final CustRepository custRepository;

    public CustUpdateProcessor(CustRepository custRepository) {
        this.custRepository = custRepository;
    }

    @Override
    public Cust process(Watchlist watchlist) {
        logger.info("Processing watchlist item: {}", watchlist);
        List<Cust> matchingCusts = custRepository.findByCustNameIgnoreCaseAndBirthdayAndNation(
                watchlist.getCustName().trim(),
                watchlist.getBirthday().trim(),
                watchlist.getNation().trim());

        if (!matchingCusts.isEmpty()) {
            Cust cust = matchingCusts.get(0);
            cust.setWlfYn("Y");
            cust.setWlfDvCd("00");
            logger.info("Processed cust item: {}", cust);
            return cust;
        }

        return null;
    }
}
