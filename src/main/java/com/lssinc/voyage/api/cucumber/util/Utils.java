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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lssinc.voyage.api.cucumber.domain.AuthenticationJwtToken;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

/**.
 *
 * utility class contains common functionality and reusable code
 */
public class Utils {

    /**
     * max random number to insert the user name
     */
    public static final int MAX_RANDOM_NUMBER = 1000;

    /**.
     * builds the request parameter
     * @param propertiesMap contains the properties fro request header
     * @return returns @link{ {@link ResponseEntity}}
     */
    public static HttpEntity
    buildAuthTokenHeadersAndRequestBody(Map propertiesMap)
            throws UnsupportedEncodingException {

        ResponseEntity<String> response = null;

        HttpHeaders headers =
                buildBasicAuthenticationHttpHeader(
                        propertiesMap.get(VoyageConstants.VOYAGE_API_USER)
                                .toString(),
                        propertiesMap.get(VoyageConstants
                                .VOYAGE_API_USER_PASSWORD).toString());
        HttpEntity httpEntity = buildRequestBody(headers, propertiesMap);
        return httpEntity;
    }

    /**
     * @param user
     * @param password
     * @return the {@link HttpHeaders } for authentication token
     */
    public static HttpHeaders
        buildBasicAuthenticationHttpHeader(String user, String password)
            throws UnsupportedEncodingException {

        String notEncoded = user + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded
                .getBytes(StandardCharsets.UTF_8));
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
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_ID)
                        .toString(),
                propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_ID_VALUE)
                        .toString());
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_CLIENT_SECRET)
                        .toString(),
                propertiesMap.get(VoyageConstants
                        .VOYAGE_API_CLIENT_SECRET_VALUE)
                        .toString());
        body.add(propertiesMap.get(VoyageConstants.VOYAGE_API_GRANT_TYPE)
                        .toString(),
                propertiesMap.get(VoyageConstants
                        .VOYAGE_API_GRANT_TYPE_VALUE).toString());
        body.add(propertiesMap.get(VoyageConstants.REQUEST_SCOPE).toString(),
                "");

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
        headers.set(VoyageConstants.AUTHORIZATION_SCHEME, VoyageConstants
                .BEARER_TOKEN + accessToken);
        return headers;
    }

    /**
     * writes the id of the given object at the given file name
     *
     * @param filePath file path of the file to be written
     * @throws IOException
     */
    public static void writeIdToFile(String filePath, String body)
            throws IOException {
        Writer writer = null;
        File file = null;
        try {
            file = new File(filePath);
            writer = new OutputStreamWriter(
                    new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(body);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /**
     * reads the object from a given file object
     * @param fileName
     * @return
     */
    public static String readFile(String fileName) {
        File file = null;
        String readfile = null;
        try {
             file = new File(fileName);
            readfile = FileUtils.readFileToString(new File(fileName),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file = null;
        }
        return readfile;
    }


    /**
     *
     * @return returns random number between 1 to 1000
     */
    public static int getRandomNumber() {
        int min = 1;
        Random random = new Random();
        return min + random.nextInt(MAX_RANDOM_NUMBER);
    }

    /**
     *  returns the AuthenticationJwtToken class
     * @param response
     * @return
     * @throws java.io.IOException
     */
    public static AuthenticationJwtToken getAuthenticationJwtToken
            (ResponseEntity<String> response)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = response.getBody();
        InputStream stream = new ByteArrayInputStream(responseBody
                .getBytes(StandardCharsets.UTF_8.name()));
        AuthenticationJwtToken authenticationJwtToken =
                mapper.readValue (stream, AuthenticationJwtToken.class);
        return authenticationJwtToken;
    }
}
