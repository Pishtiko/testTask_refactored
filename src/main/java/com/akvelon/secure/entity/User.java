package com.akvelon.secure.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="users")
@XmlRootElement( name = "product" )
@XmlType(propOrder={"login", "password"})
public class User {

    @Id
    private String user_name;
    private String user_pass;

    public User(String login, String password) {
        this.user_name = login;
        this.user_pass = password;
    }

    public User() {

    }

    public String getLogin() {
        return user_name;
    }

    public void setLogin(String login) {
        this.user_name = login;
    }

    public String getPassword() {
        return user_pass;
    }

    public void setPassword(String password) {
        this.user_pass = password;
    }


}
