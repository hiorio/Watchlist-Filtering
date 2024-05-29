package com.watchlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    private String custName;
    private String birthday;
    private String nation;

    // Getters and setters
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "custName='" + custName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}
