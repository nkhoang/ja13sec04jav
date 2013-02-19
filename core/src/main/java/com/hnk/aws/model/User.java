package com.hnk.aws.model;

import com.hnk.aws.model.validator.CheckPassword;
import com.hnk.aws.model.validator.group.UserRegistrationCheck;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings({"JpaAttributeTypeInspection"})
@Entity
/**
 * @author hnguyen
 */
public class User extends BaseEntity implements Serializable, UserDetails {
    @Override
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Basic
    @NotNull(groups = UserRegistrationCheck.class, message = "First name is required.")
    private String firstName;
    @Basic
    @NotNull(groups = UserRegistrationCheck.class, message = "Last name is required.")
    private String lastName;
    @Basic
    private String middleName;
    @Basic
    @NotNull(groups = UserRegistrationCheck.class, message = "Username is required.")
    private String username;
    @Basic
    @CheckPassword(groups = UserRegistrationCheck.class)
    private String password;
    @Basic
    private String email;
    @Basic
    private String phoneNumber;
    @Basic
    private Date birthDate;
    @Basic
    private String gender;
    @Basic
    private String personalId;
    @Basic
    private String personalIdType;
    @Basic
    private String issuePlace;
    @Basic
    private Date issueDate;
    @Basic
    private float customerValue;

    @ElementCollection
    private Set<String> roleNames;

    // Spring required properties
    @Basic
    private boolean enabled;

    @Transient
    private boolean accountExpired;
    @Transient
    private boolean accountLocked;
    @Transient
    private boolean credentialExpired;

    public enum PersonalIdType {
        CIVIL,
        VISA
    }

    public enum CustomerGender {
        MALE,
        FEMALE
    }

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);

        for (String s : roleNames) {
            authorities.add(new SimpleGrantedAuthority(s));
        }

        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Transient
    public boolean isAccountNonExpired() {
        return !accountLocked;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialExpired() {
        return credentialExpired;
    }

    public void setCredentialExpired(boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public float getCustomerValue() {
        return customerValue;
    }

    public void setCustomerValue(float customerValue) {
        this.customerValue = customerValue;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalIdType() {
        return personalIdType;
    }

    public void setPersonalIdType(String personalIdType) {
        this.personalIdType = personalIdType;
    }
}
