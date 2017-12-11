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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**.
 *
 * utility class contains common functionality and reusable code
 */
public class Utils {


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
        try {
            java.io.Writer wr = new FileWriter(filePath);
            wr.write(body);
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the object from a given file object
     * @param filename
     * @return
     */
    public static String readFile(String filename){
        List<String> lines = null;
        String line = null;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            lines = new ArrayList<String>();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.toString(lines.toArray(new String[lines.size()]));
    }
}
