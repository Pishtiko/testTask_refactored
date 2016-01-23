
package com.akvelon.secure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name="OrderProduct")
@XmlRootElement( name = "orderproduct" )
@XmlType(propOrder={"orderId", "productId", "count"})
public class OrderProduct implements Serializable {
    @Id
    @Column(name="orderId")
    private int orderId;
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
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int OrderId) {
        this.orderId = OrderId;
    }


}
