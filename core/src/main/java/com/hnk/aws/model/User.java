package com.hnk.aws.model;

import com.hnk.aws.model.validator.CheckPassword;
import com.hnk.aws.model.validator.group.UserRegistrationCheck;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"JpaAttributeTypeInspection"})
@Table(name = "TBL_USER")
@Entity
/**
 * @author hnguyen
 */
@Access(value = AccessType.FIELD)
public class User extends BaseEntity implements Serializable, UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false, targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "user")
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    @NotNull(groups = UserRegistrationCheck.class, message = "First name is required.")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull(groups = UserRegistrationCheck.class, message = "Last name is required.")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @CheckPassword(groups = UserRegistrationCheck.class)
    private String password;
    private String email;
    private String phoneNumber;
    @Column(name = "BIRTH_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthDate;
    private String gender;

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);

        /*for (String s : roleNames) {
            authorities.add(new SimpleGrantedAuthority(s));
        }*/

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return account.getEnabled();
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

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
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

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
