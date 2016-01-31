package com.akvelon.secure.entity.enums;

import javax.persistence.Entity;
import javax.persistence.Table;


public enum UserRoleEnum {

    ADMIN,
    MANAGER,
    CUSTOMER,
    USER,
    ANONYMOUS;

    UserRoleEnum() {
    }

}
