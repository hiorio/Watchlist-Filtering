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

    // 메소드 선언 없이 @Getter / @Setter 롬복 사용하고
    // 세터는 지양함!! 데이터가 어디서 언제 변경될 지 몰라서
    // 새로운 객체라면 생성자 통해서 필드값 받아오거나
    // 'set' 말고 'addBirthday', 'formatBirthday' 같이 명명함... (;;)

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
