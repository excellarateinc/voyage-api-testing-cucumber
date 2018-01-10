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

package com.lssinc.voyage.api.cucumber.stepdef;

import com.lssinc.voyage.api.cucumber.VoyageApiTestingCucumberApplication;
import com.lssinc.voyage.api.cucumber.domain.AuthenticationJwtToken;
import com.lssinc.voyage.api.cucumber.util.Utils;
import com.lssinc.voyage.api.cucumber.util.VoyageConstants;
import com.sun.glass.ui.Application;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * .
 * voyage accounts api integration test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VoyageApiTestingCucumberApplication.class,
        loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class VoyageApplicationAccountsStepdefs implements En {
    /**
     * .
     * stores the response entity for user request test case, it will be used
     * in the next test case
     */
    private ResponseEntity<String> responseEntityForAccountsRequest = null;
    /**
     * Authentication token of voyage application
     */
    private static AuthenticationJwtToken authenticationJwtToken;
    /**.
     * user property loaded from properties file
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String user;
    /**.
     * user property loaded from properties file
     */
    @Value("${voyageapi.dotnet.username}")
    private String userName;
    /**.
     * password property loaded from properties file
     */
    @Value("${voyageapi.dotnet.password}")
    private String password;
    /**.
     * client id placeholder property loaded from properties file
     */
    @Value("${voyagestepdef.clientid}")
    private String clientId;

    /**.
     * client id value property loaded from properties file
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String clientIdValue;

    /**.
     * client secret placeholder property loaded from properties file
     */
    @Value("${voyagestepdef.clientsercret}")
    private String clientSecret;

    /**.
     * client secret property loaded from properties file
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String clientSecretValue;

    /**.
     * grant type placeholder property loaded from properties file
     */
    @Value("${voyagestepdef.granttype}")
    private String grantType;

    /**.
     * grant type property loaded from properties file
     */
    @Value("${voyageapi.dotnet.granttypevalue}")
    private String grantTypeValue;

    /**.
     * invalid access token property loaded from properties files
     */
    @Value("${voyagestepdefinvalidauthtoken.accesstoken}")
    private String accessToken;
    /**.
     * voyage api server url property loaded from properties file
     */
    @Value("${voyageapi.dotnet.serverurl}")
    private String authorizationdotnetUrl;
    /**.
     * voyage api server url property loaded from properties file
     */
    @Value("${voyageapi.dotnet.serverurlforapi}")
    private String serverUrlforApi;
    /**.
     * voyage api server api version property loaded from properties file
     */
    @Value("${voyageapi.serverapiversion}")
    private String serverApiVersion;
    /**.
     * voyage api oauth token path property loaded from properties file
     */
    @Value("${voyageapi.dotnet.oauthpath}")
    private String serverOauthPath;
    /**
     * Oauth2 token request url
     */
    private static String oAuthTokenUrl;
    /**.
     * rest template used to call rest services from Voyage API
     * @return RestTemplate
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    /**
     *  voyage api authorization url
     */
    @Value("${voyageapi.dotnet.authorizationurl}")
    private String authorizationUrl;
    /**
     *  voyage api authorization path
     */
    @Value("${voyageapi.dotnet.clientsuper}")
    private String clientSuper;
    /**
     *  voyage api authorization path
     */
    @Value("${voyageapi.dotnet.authorizationpath}")
    private String authorizationPath;
    /**
     *  voyage api redirect url
     */
    @Value("${voyageapi.dotnet.redirecturl}")
    private String redirectUrl;
    /**
     *  voyage api redirect url parameter
     */
    @Value("${voyageapi.dotnet.responsetype}")
    private String responseType;
    /**
     *submitform
     */
    @Value("${voyageapi.dotnet.submitvoyageauthorizationform}")
    private String submitVoyageAuthorizationForm;
    /**.
     * voyage api server url
     */
    @Value("${voyageapi.dotnet.serverurl}")
    private String serverUrl;
    /**
     * initializing variables and constructing the oauth2 url for auth token
     * generation
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        oAuthTokenUrl = serverUrl + serverOauthPath;
    }
    /**.
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**.
     * @return response entity of successful generated token
     */
    private ResponseEntity getAuthToken() throws Exception {
        ResponseEntity<String> response = null;
        try {
            MultiValueMap<String, String> propertiesMap = getParametersMap();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<?> httpEntity = new HttpEntity<Object>(propertiesMap,
                    headers);

            RestTemplate restTemplate = restTemplateBuilder.build();
            restTemplate.getMessageConverters().add(new
                    StringHttpMessageConverter());
            response =
                    restTemplate.exchange(oAuthTokenUrl, HttpMethod
                            .POST, httpEntity, String.class);
            headers.setContentType(MediaType.TEXT_HTML);
            authenticationJwtToken = Utils.getAuthenticationJwtToken
                    (response);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            throw e;
        }
        return response;
    }

    /**
     * returns the parameters required for the web service call
     * @return
     */
    private MultiValueMap<String, String> getParametersMap() {
        // constructing property map for constructing parameters
        MultiValueMap<String, String> propertiesMap =
                new LinkedMultiValueMap<>();
        propertiesMap.add(grantType, grantTypeValue);
        propertiesMap.add(VoyageConstants.VOYAGE_API_USER_NAME, userName);
        propertiesMap.add(VoyageConstants.VOYAGE_API_USER_PASSWORD,
                password);
        propertiesMap.add(VoyageConstants.REQUEST_SCOPE, "");
        propertiesMap.add(clientId, clientIdValue);
        propertiesMap.add(clientSecret, clientSecretValue);
        return propertiesMap;
    }


    @Given("^I have a valid jwt token for account submission$")
    public void iHaveAValidJwtTokenForAccountSubmission() throws Throwable {
        responseEntityForAccountsRequest = getAuthToken();
        Assert.assertNotNull(responseEntityForAccountsRequest);
        Assert.assertNotNull(authenticationJwtToken.getAccess_token());

    }

    @When("^user requests for account submission \"([^\"]*)\"$")
    public void userRequestsForAccountSubmission(String arg0) throws Throwable {
        String serviceUrlForPermissions = serverUrlforApi + VoyageConstants
                .FORWARD_SLASH + serverApiVersion
                + VoyageConstants.VOYAGE_API_ACCOUNTS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        accessToken);
        int randomNumber = Utils.getRandomNumber();
        String usernameForInserting = "FirstName" + randomNumber + "@app.com";
        String body =  updateRequestBodyForUpdating(usernameForInserting);
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        try {
            responseEntityForAccountsRequest =
                    restTemplateBuilder.build()
                            .exchange(serviceUrlForPermissions,
                                    HttpMethod.POST, entity, String.class);

        } catch (RestClientException e) {
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForAccountsRequest);
    }

    /**
     * returns the json request for inserting an account
     * @param usernameForInserting
     * @return
     */
    private String updateRequestBodyForUpdating(String usernameForInserting) {
        return "{\n"
               + "  \"userName\": " + "\""  + usernameForInserting + "\",\n"
               + "  \"email\": " + "\""  + usernameForInserting + "\",\n"
               + "  \"password\": \"abcD@1234\",\n"
               + "  \"confirmPassword\": \"abcD@1234\",\n"
               + "  \"firstName\": \"string\",\n"
               + "  \"lastName\": \"string\",\n"
               + "  \"phoneNumbers\": [\n"
               + "    {\n"
               + "      \"id\": 0,\n"
               + "      \"userId\": \"1\",\n"
               + "      \"phoneNumber\": \"14155552671\",\n"
               + "      \"phoneType\": 0,\n"
               + "      \"verificationCode\": \"string\"\n"
               + "    }\n"
               + "  ]\n"
               + "}";
    }

    @Then("^I should submit account information$")
    public void iShouldSubmitAccountInformation(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForAccountsRequest);
        Assert.assertTrue(HttpStatus.CREATED
                          == responseEntityForAccountsRequest.getStatusCode());
    }
}
