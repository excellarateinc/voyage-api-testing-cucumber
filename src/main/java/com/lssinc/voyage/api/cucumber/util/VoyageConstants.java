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

package com.lssinc.voyage.api.cucumber.util;

/**.
 * Constants class for Voyage API testing
 */
public class VoyageConstants {
    /**.
     * claim id header constant
     */
    public static final String VOYAGE_API_CLIENT_ID = "clientId";
    /**.
     *  claim id header value
     */
    public static final String VOYAGE_API_CLIENT_ID_VALUE = "clientIdValue";
    /**.
     *  client secret header constant
     */
    public static final String VOYAGE_API_CLIENT_SECRET = "clientSecret";
    /**.
     * client secret header value
     */
    public static final String VOYAGE_API_CLIENT_SECRET_VALUE =
            "clientSecretValue";
    /**.
     * grant type header constant
     */
    public static final String VOYAGE_API_GRANT_TYPE = "grantType";
    /**.
     * grant type header value
     */
    public  static final String VOYAGE_API_GRANT_TYPE_VALUE = "grantTypeValue";
    /**.
     * scope header constant
     */
    public static final String REQUEST_SCOPE = "scope";
    /**.
     * user name header constant
     */
    public static final String VOYAGE_API_USER = "user";
    /**.
     * user name header constant
     */
    public static final String VOYAGE_API_USER_NAME = "username";
    /**.
     * password header constant
     */
    public static final String VOYAGE_API_USER_PASSWORD = "password";
    /**.
     * fully qualified OAuth Token url
     */
    public static final String VOYAGE_API_OAUTH_TOKEN_URL = "oAuthTokenUrl";
    /**.
     *  Authorization header constant
     */
    public static final String AUTHORIZATION_SCHEME = "Authorization";
    /**.
     *  Bearer token constant
     */
    public static final String BEARER_TOKEN = "Bearer ";

    /**.
     *  voyage api version
     */
    public static final String VOYAGE_API_VERSION = "api/v1/";
    /**.
     *  forward slash
     */
    public static final String FORWARD_SLASH = "/";
    /**
     * voyage constants permissions api path
     */
    public static final String VOYAGE_API_PERMISSIONS_PATH = "permissions";
    /**
     *  voyage api retrieve record
     */
    public static final String VOYAGE_API_RETRIEVE_RECORD = "1";
    /**
     * voyage constants roles api path
     */
    public static final String VOYAGE_API_ROLES_PATH = "roles";
    /**
     * voyage constants users api path
     */
    public static final String VOYAGE_API_USERS_PATH = "users";
    /**
     * voyage constants status api path
     */
    public static final String VOYAGE_API_STATUS_PATH = "status";
    /**
     * voyage constants profile api path
     */
    public static final String VOYAGE_API_PROFILE_ME_PATH = "profiles/me";
    /**
     * voyage constants accounts api path
     */
    public static final String VOYAGE_API_ACCOUNTS_PATH = "accounts";
}
