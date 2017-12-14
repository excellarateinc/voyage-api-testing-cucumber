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
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * user phone class
 */
public class UserPhone {

    int id;

    String phoneType;

    @NotBlank
    String phoneNumber;

    @JsonIgnore
    String verifyCode;

    @NotNull
    @JsonIgnore
    Boolean isValidated = Boolean.FALSE;

    @JsonIgnore
    Date verifyCodeExpiresOn;

    @JsonIgnore
    User user;

    @JsonIgnore
    String isVerifyCodeExpired;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }

    public Date getVerifyCodeExpiresOn() {
        return verifyCodeExpiresOn;
    }

    public void setVerifyCodeExpiresOn(Date verifyCodeExpiresOn) {
        this.verifyCodeExpiresOn = verifyCodeExpiresOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIsVerifyCodeExpired() {
        return isVerifyCodeExpired;
    }

    public void setIsVerifyCodeExpired(String isVerifyCodeExpired) {
        this.isVerifyCodeExpired = isVerifyCodeExpired;
    }
}
