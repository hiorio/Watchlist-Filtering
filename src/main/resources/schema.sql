CREATE DATABASE IF NOT EXISTS watchlist;
USE watchlist;

CREATE TABLE IF NOT EXISTS CUST (
                                    CUST_ID VARCHAR(10) PRIMARY KEY,
    CUST_NAME VARCHAR(50),
    BIRTHDAY VARCHAR(20),
    NATION VARCHAR(2),
    WLF_YN CHAR(1),
    WLF_DV_CD CHAR(2)
    );

CREATE TABLE IF NOT EXISTS WATCHLIST (
                                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         CUST_NAME VARCHAR(50),
    BIRTHDAY VARCHAR(20),
    NATION VARCHAR(2)
    );