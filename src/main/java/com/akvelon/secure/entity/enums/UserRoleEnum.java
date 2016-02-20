package com.akvelon.secure.entity.enums;

import javax.persistence.Entity;
import javax.persistence.Table;


public enum UserRoleEnum {

    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_CUSTOMER,
    ROLE_USER,
    ANONYMOUS;

    UserRoleEnum() {}

}
