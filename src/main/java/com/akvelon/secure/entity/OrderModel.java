
package com.akvelon.secure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

;

/**
 *
 * @author ART
 */
@Entity
@Table(name="T_order")
@XmlRootElement( name = "t_order" )
@XmlType(propOrder={"orderId", "customerId", "createDate"})
public class OrderModel implements Serializable {
    
    @XmlElement
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    @XmlElement
    public int getCustomerId() {
        return customerId;
    }
     
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    @XmlElement
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Id
    @Column(name="OrderId")
    private int orderId;
    @Column(name="CustomerId")
    private int customerId;
    @Column(name="CreateDate")
    private Date createDate;

    
}
