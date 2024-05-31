package com.watchlist.service;

import com.watchlist.model.Cust;
import com.watchlist.repository.CustRepository;
import org.springframework.stereotype.Service;

@Service
public class CustService {

    private final CustRepository custRepository;

    public CustService(CustRepository custRepository) {
        this.custRepository = custRepository;
    }

    public Cust findByCustId(String custId) {
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
