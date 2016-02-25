package com.akvelon.secure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_users")
public class User implements Serializable{

    public User(){}
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Id
    @Column(name = "userName")
    private String login;
    @Column(name = "password")

    private String password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
