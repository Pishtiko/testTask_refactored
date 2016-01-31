package com.akvelon.secure.entity;


import com.akvelon.secure.entity.enums.UserRoleEnum;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@Entity
@Table(name="user_roles")
@XmlRootElement( name = "user_roles" )
@XmlType(propOrder={"userName", "roleName"})
public class UserRole {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private UserRoleEnum roleName;

    public UserRoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(UserRoleEnum roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
