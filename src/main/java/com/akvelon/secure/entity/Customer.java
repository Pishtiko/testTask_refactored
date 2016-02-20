
package com.akvelon.secure.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="t_customer")

public class Customer implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name="userName")
    private User userEmail;
    @Column(name="fullName")
    private String fullName;
//    @Column(name="AGE")
//    private int age;
//    public Customer(){}
    
    


    public User getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(User id) {
        this.userEmail = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
}
