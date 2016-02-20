package com.akvelon.secure.entity;


import com.akvelon.secure.entity.enums.UserRoleEnum;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@Entity
@Table(name="t_user_roles")
public class UserRole implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name="userName")
    private User userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleName")
    private UserRoleEnum roleName;

    public UserRoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(UserRoleEnum roleName) {
        this.roleName = roleName;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }
}
