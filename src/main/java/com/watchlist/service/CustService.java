package com.watchlist.service;

import com.watchlist.model.Cust;
import com.watchlist.repository.CustRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustService {
    @Autowired
    private CustRepository custRepository;

    public Cust findByCustId(String custId) {
        // git 테스트..
        return custRepository.findById(custId).orElse(null);
    }

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
