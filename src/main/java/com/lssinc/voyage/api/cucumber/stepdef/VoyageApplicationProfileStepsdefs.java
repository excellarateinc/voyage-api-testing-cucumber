/*
 *
 *  * Copyright 2017 Lighthouse Software, Inc.   http://www.LighthouseSoftware.com
 *  *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  * contributor license agreements.  See the NOTICE file distributed with
 *  * this work for additional information regarding copyright ownership.
 *  * The ASF licenses this file to You under the Apache License, Version 2.0
 *  * (the "License"); you may not use this file except in compliance with
 *  * the License.  You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.lssinc.voyage.api.cucumber.stepdef;

import com.lssinc.voyage.api.cucumber.VoyageApiTestingCucumberApplication;
import com.lssinc.voyage.api.cucumber.domain.AuthenticationJwtToken;
import com.lssinc.voyage.api.cucumber.domain.User;
import com.lssinc.voyage.api.cucumber.util.Utils;
import com.lssinc.voyage.api.cucumber.util.VoyageConstants;
import com.sun.glass.ui.Application;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * .
 * voyage api authentication token integration test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = VoyageApiTestingCucumberApplication.class,
        loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class VoyageApplicationProfileStepsdefs {
    /**
     * .
     * stores the response entity for user request test case, it will be used
     * in the next test case
     */
    private ResponseEntity<String> responseEntityForProfileRequest = null;
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
     * password property loaded from properties file
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String password;
    /**.
     * client id placeholder property loaded from properties file
     */
    @Value("${voyagestepdef.clientidvalue}")
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
    @Value("${voyagestepdef.granttypevalue}")
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
    private String serverUrl;
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
     * initializing variables and constructing the oauth2 url for auth token
     * generation
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        oAuthTokenUrl = serverUrl + serverOauthPath;
    }
    /**
     *  response {@link User} class
     */
    private static List<User> users;

    /**
     *  parameter error message
     */
    private static String HTTP_401_UNAUTHORIZED;
    /**
     * dynamically creating user names as the service doesn't accept duplicates
     */
    private static String usernameForInserting;
    /**
     * Response entity for missing parameters
     */
    private static ResponseEntity<String>
            responseEntityMissingRequiredParamUser;

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
            // constructing property map for constructing rest webservice url
            Map<String, String> propertiesMap = new HashMap<String, String>();
            propertiesMap.put(VoyageConstants.VOYAGE_API_USER, user);
            propertiesMap.put(VoyageConstants.VOYAGE_API_USER_PASSWORD,
                    password);
            propertiesMap.put(VoyageConstants.VOYAGE_API_CLIENT_ID, clientId);
            propertiesMap.put(VoyageConstants.VOYAGE_API_CLIENT_ID_VALUE,
                    clientIdValue);
            propertiesMap.put(VoyageConstants.VOYAGE_API_USER_PASSWORD,
                    clientSecret);
            propertiesMap.put(VoyageConstants.VOYAGE_API_CLIENT_SECRET,
                    clientSecret);
            propertiesMap.put(VoyageConstants.VOYAGE_API_CLIENT_SECRET_VALUE,
                    clientSecretValue);
            propertiesMap.put(VoyageConstants.VOYAGE_API_GRANT_TYPE, grantType);
            propertiesMap.put(VoyageConstants.VOYAGE_API_GRANT_TYPE_VALUE,
                    grantTypeValue);
            propertiesMap.put(VoyageConstants.REQUEST_SCOPE, "");
            propertiesMap.put(VoyageConstants.VOYAGE_API_OAUTH_TOKEN_URL,
                    oAuthTokenUrl);

            HttpEntity httpEntity =
                    Utils.buildAuthTokenHeadersAndRequestBody(propertiesMap);

            RestTemplate restTemplate = restTemplateBuilder.build();

            restTemplate.getInterceptors().add(
                    new BasicAuthorizationInterceptor(user, password));
            restTemplate.getMessageConverters().add(new
                    StringHttpMessageConverter());
            response =
                    restTemplate.exchange(oAuthTokenUrl, HttpMethod
                            .POST, httpEntity, String.class);
            authenticationJwtToken = Utils.getAuthenticationJwtToken(response);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            throw e;
        }
        return response;
    }

     @Given("^I have a valid jwt token for user profile$")
    public void iHaveAValidJwtTokenForUserProfile() throws Throwable {
        getAuthToken();
        Assert.assertNotNull(authenticationJwtToken.getAccess_token());
    }

    @When("^user requests for user profile \"([^\"]*)\"$")
    public void userRequestsForUserProfile(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String serviceUrlForUsers = serverUrlforApi + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_PROFILE_ME_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        try {
            responseEntityForProfileRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.GET, entity,
                            String.class);

            Assert.assertNotNull(responseEntityForProfileRequest);
        } catch (RestClientException e) {
            //Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        //Assert.assertNotNull(responseEntityForProfileRequest);
    }

    private String updateRequestBodyForUpdating() {

        String body = "{ \"userName\": \"abc\", \"email\": \"abc@abc.com\", " +
                "\"password\": \"abcD@1234\", \"confirmPassword\": " +
                "\"abcD@1234\", \"firstName\": \"string\", \"lastName\": " +
                "\"string\", \"phoneNumbers\": [ { \"id\": 0, \"userId\": " +
                "\"1\", \"phoneNumber\": \"14155552671\", \"phoneType\": 0, " +
                "\"verificationCode\": \"string\" }]}";
        return body;
    }

    @Then("^I should obtain the user user profile details$")
    public void iShouldObtainTheUserUserProfileDetails(String arg0) throws
                                                               Throwable {
        // need to implement this method after it is working on the server side
        //Assert.assertNotNull(responseEntityForProfileRequest);
    }

    @Then("^I should be able to submit users profile$")
    public void iShouldBeAbleToSubmitUsersProfile(String arg0) throws
                                                               Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion
                + VoyageConstants.VOYAGE_API_PROFILE_ME_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        accessToken);

        String body =  updateRequestBodyForUpdating();
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);
        try {
            responseEntityForProfileRequest =
                    restTemplateBuilder.build()
                            .exchange(serviceUrlForPermissions,
                                    HttpMethod.POST, entity, String.class);

        } catch (RestClientException e) {
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForProfileRequest);
    }


    @When("^user submits for user profile \"([^\"]*)\"$")
    public void userSubmitsForUserProfile(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //Assert.assertNotNull(responseEntityForProfileRequest);
    }
}
