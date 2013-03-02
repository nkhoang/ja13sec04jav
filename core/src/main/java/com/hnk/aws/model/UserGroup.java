package com.hnk.aws.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A user must be in at least one group. A group is used to simplify the process of granting access
 * role to the user. By assigning user to a group, a default set of access roles are also granted to
 * that user.
 *
 * @author hnguyen.
 */
@Entity
@Table(name = UserGroup.TABLE_NAME)
public class UserGroup extends IdEntity {
    public static final String TABLE_NAME = "USER_GROUP";
    @Basic
    @Column(name = "group_name")
    private String name;

    @OneToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_GROUP_ROLE", joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles = new HashSet<UserRole>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
