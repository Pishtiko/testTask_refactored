
package com.akvelon.secure.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 *
 * @author HP-G6
 */
@Entity
@Table(name="t_customer")
@XmlRootElement( name = "customer" )
@XmlType(propOrder={"id", "fullName"})

public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="CustomerId")
    private int id;
    @Column(name="CustomerName")
    private String fullName;
//    @Column(name="AGE")
//    private int age;
//    public Customer(){}
    
    

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
//    @XmlElement
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
    
}
