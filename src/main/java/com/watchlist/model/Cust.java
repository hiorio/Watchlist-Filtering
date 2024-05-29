package com.watchlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CUST")
@Data
public class Cust {
    @Id
    private String custId;
    private String custName;
    private String birthday;
    private String nation;
    private String wlfYn;
    private String wlfDvCd;
}
