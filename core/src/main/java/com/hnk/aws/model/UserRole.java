package com.hnk.aws.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = UserRole.TABLE_NAME)
public class UserRole extends IdEntity {
    public static final String TABLE_NAME = "TBL_USER_ROLE";

    @Basic
    @Column(name = "role_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
