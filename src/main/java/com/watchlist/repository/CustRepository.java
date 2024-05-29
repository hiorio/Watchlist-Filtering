package com.watchlist.repository;

import com.watchlist.model.Cust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustRepository extends JpaRepository<Cust, String> {
    List<Cust> findByCustNameIgnoreCaseAndBirthdayAndNation(String custName, String birthday, String nation);
}
