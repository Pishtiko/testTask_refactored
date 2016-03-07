package com.akvelon.secure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="View_1")
public class View_1 {

    @Id
    @Column
    String userName;
    @Column
    int idd;
    @Column
    int avCount;
    @Column
    int avPrice;
    @Column
    int summa;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getAvCount() {
        return avCount;
    }

    public void setAvCount(int avCount) {
        this.avCount = avCount;
    }

    public int getAvPrice() {
        return avPrice;
    }

    public void setAvPrice(int avPrice) {
        this.avPrice = avPrice;
    }

    public int getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }
}
