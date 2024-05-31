package com.watchlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "watchlist")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {

    @Id
    private String custName;
    private String birthday;
    private String nation;
}
