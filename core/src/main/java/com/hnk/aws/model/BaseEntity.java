package com.hnk.aws.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    public static final String CREATED_DATE = "createdDate";
    public static final String UPDATED_DATE = "updatedDate";
    @Basic
    @Column(name = BaseEntity.CREATED_DATE)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdDate;

    @Basic
    @Column(name = BaseEntity.UPDATED_DATE)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updatedDate;

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(DateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    public void prePersis() {
        createdDate = new DateTime();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = new DateTime();
    }
}
