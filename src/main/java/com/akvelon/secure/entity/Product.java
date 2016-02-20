
package com.akvelon.secure.entity;

import org.hibernate.search.annotations.Indexed;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@Entity
@Table(name="t_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ProductId")
    private int productId;
    @Column(name="ProductName")
    private String productName;
    @Column(name="Price")
    private int price;
    @Column (name = "Description")
    private String description;


    public Product(){}
    

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() { return description;  }
    public void setDescription(String description) {  this.description = description; }
/*

    @Override
    public String toString() {
        return String.format(
                "productId: %s\nproductName: %s\nprice: %s",
                productId, productName, price);
    }
*/

    
    
}
