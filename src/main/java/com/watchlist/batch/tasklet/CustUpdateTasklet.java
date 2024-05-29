package com.watchlist.batch.tasklet;

import com.watchlist.repository.CustRepository;
import com.watchlist.repository.WatchlistRepository;
import com.watchlist.model.Cust;
import com.watchlist.model.Watchlist;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustUpdateTasklet implements Tasklet {

    private final CustRepository custRepository;
    private final WatchlistRepository watchlistRepository;

    public CustUpdateTasklet(CustRepository custRepository, WatchlistRepository watchlistRepository) {
        this.custRepository = custRepository;
        this.watchlistRepository = watchlistRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Watchlist> watchlistEntries = watchlistRepository.findAll();

        // 하나씩 디비 안 찌르고 전체 고객 올려서 자바로 필터링 해도 좋을듯
        for (Watchlist watchlist : watchlistEntries) {
            List<Cust> matchingCusts = custRepository.findByCustNameIgnoreCaseAndBirthdayAndNation(
                    watchlist.getCustName().trim(),
                    watchlist.getBirthday().trim(),
                    watchlist.getNation().trim());

            for (Cust cust : matchingCusts) {
                cust.setWlfYn("Y");
                custRepository.save(cust);
            }
        }

        return RepeatStatus.FINISHED;
    }
}
