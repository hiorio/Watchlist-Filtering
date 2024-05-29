package com.watchlist.service;

import com.watchlist.model.Cust;
import com.watchlist.repository.CustRepository;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustService {
    @Autowired // 얘두
    private CustRepository custRepository;

    public Cust findByCustId(String custId) {

        // 없으면 널 말구 예외처리
//        Cust cust = custRepository.findById(custId)
//            .orElseThrow(() -> {
//                log.error("{} 가 존재하지 않음", custId);
//                throw new NoSuchElementException();
//            });
//
//        return cust.getWlfYn();

        return custRepository.findById(custId).orElse(null);
    }

    // 얘는 몰까 ? ?
    public void updateWlfYn(String custName, String birthday, String nation) {
        custRepository.findAll().forEach(cust -> {
            if (cust.getCustName().equalsIgnoreCase(custName.trim()) &&
                    cust.getBirthday().equals(birthday.trim()) &&
                    cust.getNation().equalsIgnoreCase(nation.trim())) {
                cust.setWlfYn("Y");
                custRepository.save(cust);
            }
        });
    }
}
