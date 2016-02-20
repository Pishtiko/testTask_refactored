package com.akvelon.secure.entity;


import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="StatisticEntity")
public class StatisticEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "userName")
    private User userId;

    @Temporal( value = TemporalType.TIMESTAMP )
//    @org.hibernate.annotations.Generated(value= GenerationTime.INSERT)
    @Basic
    @Column(name="t_timestamp", nullable = false, updatable = false, insertable = false)
    private Date dateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        dateTime = dateTime;
    }
}
