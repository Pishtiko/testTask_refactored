
package com.akvelon.secure.entity;

import com.akvelon.secure.entity.enums.OrderStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="t_order")
public class OrderEntity implements Serializable {


    public OrderEntity(){}
//    public OrderEntity(UserCart cart){
//        this.orderId = cart.orderId;
//        this.userId = cart.userName;
//    }



    @Id
    @Column(name="idd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idd;

//    @OneToOne//(cascade=CascadeType.ALL)
//    @JoinColumn(name = "orderId")
//    private OrderProduct orderId;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User userId;

//    @Id
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CreateDate", nullable = false, updatable = false, insertable = false)
    private Date createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "OrderStatus")
    private OrderStatus status;


    public int getIdd() { return idd; }
    public void setIdd(int idd) {  this.idd = idd; }


    public User getUserId() {
        return userId;
    }
    public void setUserId(User user) {
        this.userId = user;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) {  this.status = status;  }


    
}
