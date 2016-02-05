
package com.akvelon.secure.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name="OrderProduct")
@XmlRootElement( name = "orderproduct" )
@XmlType(propOrder={"idd", "orderId", "productId", "count"})
public class OrderProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idd")
    private int idd;

    @OneToOne
    @JoinColumn(name ="orderId")
    private OrderEntity orderId;
    @Column(name="productId")
    private int productId;
    @Column(name="count")
    private int count;

    @XmlElement
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @XmlElement
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    

    @XmlElement


    public OrderEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderEntity OrderId) {
        this.orderId = OrderId;
    }

    @XmlElement
    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

}
