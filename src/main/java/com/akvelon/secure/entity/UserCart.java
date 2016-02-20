package com.akvelon.secure.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_UserCart")
public class UserCart implements Serializable {

    public UserCart(User userName, OrderEntity order){
        this.userName = userName;
        this.orderId = order;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "userName")
    User userName;
    @OneToOne

    @JoinColumn(name = "orderId")
    OrderEntity orderId;

    public UserCart() {
    }

    public OrderEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderEntity orderId) {
        this.orderId = orderId;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }



}
