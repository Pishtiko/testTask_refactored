
package com.akvelon.secure.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="t_orders")
@XmlRootElement( name = "t_order" )
@XmlType(propOrder={"orderId", "userId", "createDate"})
public class OrderEntity implements Serializable {
    
    @XmlElement
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    @XmlElement

    public User getUserId() {
        return userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }
    
    @XmlElement

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Id
    @Column (name = "orderId")
    private int orderId;
    @ManyToOne
    @JoinColumn(name = "userName")
    private User userId;
    @Column(name="CreateDate")
    private Date createDate;

    
}
