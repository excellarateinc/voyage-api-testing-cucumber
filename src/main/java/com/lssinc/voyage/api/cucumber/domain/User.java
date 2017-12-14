/*
 * Copyright 2017 Lighthouse Software, Inc.   http://www.LighthouseSoftware.com
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.lssinc.voyage.api.cucumber.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * User class
 */
public class User {

    @JsonIgnoreProperties
    int id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String username;

    @Email
    String email;

    @NotBlank
    String password;

    @NotNull
    @JsonProperty(value = "isEnabled")
    Boolean isEnabled = Boolean.TRUE;

    @NotNull
    @JsonProperty(value = "isAccountExpired")
    Boolean isAccountExpired = Boolean.FALSE;

    @NotNull
    @JsonProperty(value = "isAccountLocked")
    Boolean isAccountLocked = Boolean.FALSE;

    @NotNull
    @JsonProperty(value = "isCredentialsExpired")
    Boolean isCredentialsExpired = Boolean.FALSE;

    @NotNull
    @JsonIgnore
    @JsonProperty(value = "isVerifyRequired")
    Boolean isVerifyRequired = Boolean.FALSE;

    @JsonIgnore
    @JsonProperty(value = "failedLoginAttempts")
    Integer failedLoginAttempts;

    /**
     * Force all tokens for this client created on or before this date to be
     * expired even if the original token has not yet expired.
     */
    @JsonIgnore
    Date forceTokensExpiredDate;

    /**
     *
     */
    @JsonIgnore
    Set<Role> roles;
    /**
     *
     */
    Set<UserPhone> phones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getAccountExpired() {
        return isAccountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        isAccountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    public Boolean getCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    public Boolean getVerifyRequired() {
        return isVerifyRequired;
    }

    public void setVerifyRequired(Boolean verifyRequired) {
        isVerifyRequired = verifyRequired;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public Date getForceTokensExpiredDate() {
        return forceTokensExpiredDate;
    }

    public void setForceTokensExpiredDate(Date forceTokensExpiredDate) {
        this.forceTokensExpiredDate = forceTokensExpiredDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<UserPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<UserPhone> phones) {
        this.phones = phones;
    }
}
