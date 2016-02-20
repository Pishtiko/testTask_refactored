
package com.akvelon.secure.entity;

import org.springframework.security.access.method.P;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Collection.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_OrderProduct")
@Embeddable
public class OrderProduct implements Serializable {

    public OrderProduct(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private int orderId;
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "productId" )
    private Product productId;
    @Column(name="count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "idd")
    private OrderEntity idd;



    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int OrderId) {
        this.orderId = OrderId;
    }

    public Product  getProductId() { return productId; }
    public void setProductId(Product productId) {  this.productId = productId; }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public OrderEntity getIdd() {
        return idd;
    }
    public void setIdd(OrderEntity idd) {
        this.idd = idd;
    }

}
