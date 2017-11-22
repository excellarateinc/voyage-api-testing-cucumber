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
package com.lssinc.voyage.api.cucumber.util.Util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Base64;
import java.util.Map;

/**.
 *
 * utility class contains common functionality and reusable code
 */
public class Utils {


    /**
     * builds the request parameter
     * @param propertiesMap contains the properties fro request header
     * @return returns @link{ {@link ResponseEntity}}
     */
    public static HttpEntity
    buildAuthTokenHeadersAndRequestBody(Map propertiesMap) {

        ResponseEntity<String> response = null;

        HttpHeaders headers =
                buildBasicAuthenticationHttpHeader(
                        propertiesMap.get(VoyageConstants.VOYAGE_API_USER).toString(),
                        propertiesMap.get(VoyageConstants.VOYAGE_API_USER_PASSWORD).toString());
        HttpEntity httpEntity = buildRequestBody(headers, propertiesMap);
        return httpEntity;
    }

    /**
     * @param user
     * @param password
     * @return the {@link HttpHeaders } for authentication token
     */
    public static HttpHeaders
        buildBasicAuthenticationHttpHeader(String user, String password) {

        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded
                .getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /**
     *
     * @param headers http header, it will be returned after composing headers
     *        {@link HttpHeaders}
     * @param propertiesMap contains the properties from request body
     * @return composed http header {@link HttpHeaders}
     */
    static HttpEntity buildRequestBody(HttpHeaders headers,
                                       Map propertiesMap) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String,
                String>();
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_ID).toString(),
                propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_ID_VALUE).toString());
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_SECRET).toString(),
                propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_SECRET_VALUE)
                .toString());
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_GRANT_TYPE).toString(),
                propertiesMap.get(VoyageConstants.VOYAGE_API_GRANT_TYPE_VALUE).toString());
        body.add(propertiesMap.get(VoyageConstants.REQUEST_SCOPE).toString(), "");

        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

        return httpEntity;
    }

    /**
     *
     * @param accessToken
     * @return returns the http headers required for authentication
     * {@link HttpHeaders}
     */
    public static HttpHeaders buildBasicHttpHeadersForBearerAuthentication(
            String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(VoyageConstants.AUTHORIZATION_SCHEME, VoyageConstants.BEARER_TOKEN + accessToken);
        return headers;
    }
}
