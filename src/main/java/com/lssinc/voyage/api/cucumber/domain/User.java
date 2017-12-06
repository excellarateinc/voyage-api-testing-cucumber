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
    Boolean isEnabled = Boolean.TRUE;

    @NotNull
    Boolean isAccountExpired = Boolean.FALSE;

    @NotNull
    Boolean isAccountLocked = Boolean.FALSE;

    @NotNull
    Boolean isCredentialsExpired = Boolean.FALSE;

    @NotNull
    @JsonIgnore
    Boolean isVerifyRequired = Boolean.FALSE;

    @JsonIgnore
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

}
