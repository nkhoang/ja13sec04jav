//
// @Copyright 2013
//
package com.hnk.aws.web.security.google.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to contain Google user information.
 *
 * @author hnguyen
 */
public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
    @JsonProperty("given_name")
    private String giveName;
    @JsonProperty("family_name")
    private String familyName;
    private String picture;
    private String locale;
    private String timezone;
    private String gender;
    @JsonProperty("verified_email")
    private Boolean verifiedEmail;

    public String toString() {
        return "id: " + id
                + "email: " + email
                + "name: " + name
                + "given_name: " + giveName
                + "family_name: " + familyName
                + "picture: " + picture
                + "locale: " + locale
                + "timezone: " + timezone
                + "gender: " + timezone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiveName() {
        return giveName;
    }

    public void setGiveName(String giveName) {
        this.giveName = giveName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(Boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }
}
