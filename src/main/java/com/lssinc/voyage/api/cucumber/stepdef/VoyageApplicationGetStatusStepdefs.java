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
import com.lssinc.voyage.api.cucumber.domain.Status;
import com.lssinc.voyage.api.cucumber.util.Utils;
import com.lssinc.voyage.api.cucumber.util.VoyageConstants;
import com.sun.glass.ui.Application;
import cucumber.api.java.en.And;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
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
public class VoyageApplicationGetStatusStepdefs {
    /**
     * .
     * saves the token response
     */
    private static ResponseEntity responseToken = null;
    /**
     * .
     * oauth401 is used to verifying step for using invalid bearer token
     */
    private static String HTTP_401_UNAUTHORIZED_MESSAGE;
    /**
     * .
     * stores the response entity for user request test case, it will be used
     * in the next test case
     */
    private ResponseEntity<String> responseEntityForUserRequest = null;
    /**
     * Authentication token of voyage application
     */
    private static AuthenticationJwtToken authenticationJwtToken;
    /**
     * client id property loaded from properties file
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
     * client id property loaded from properties file
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
     *  invalid access token property loaded from properties file
     */
    @Value("${voyagestepdefinvalidauthtoken.accesstoken}")
    private String invalidAuthTokenAccessToken;
    /**.
     * voyage api server url
     */
    @Value("${voyageapi.serverurl}")
    private String serverUrl;
    /**.
     * voyage api version
     */
    @Value("${voyageapi.apiversion}")
    private String apiVersion;
    /**.
     * voyage api oauth token path
     */
    @Value("${voyageapi.oauthpath}")
    private String serverOauthPath;
    /**.
     * rest template used to call rest services from Voyage API
     * @return RestTemplate
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    /**
     * Oauth2 token request url
     */
    private static String oAuthTokenUrl;
    /**
     * response status class
     */
    private static Status status;
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
     * @return response entity of successful generated token
     */
    private ResponseEntity getAuthToken() throws Exception {
        ResponseEntity<String> response = null;
        try {
            // constructing property map for constructing rest webservice url
            Map<String, String> propertiesMap = new HashMap<>();
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

            response =
                    restTemplate.exchange(oAuthTokenUrl, HttpMethod.POST,
                            httpEntity,
                            String.class);
            authenticationJwtToken = Utils.getAuthenticationJwtToken(response);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            throw e;
        }
        return response;
    }


    @Given("^user has a valid authentication token$")
    public void userHasAValidAuthenticationToken() throws Throwable {

        try {
            responseToken = getAuthToken();
        } catch (Exception e) {
            Assert.fail();
            throw e;
        }
        Assert.assertNotNull(responseToken);
        Assert.assertTrue(responseToken.getStatusCode().toString().equals
                (HttpStatus.OK.toString()));
    }

    @When("^user requests for \"([^\"]*)\"$")
    public void userRequestsFor(String arg0) throws Throwable {
        String serviceUrlForStatus = serverUrl + VoyageConstants
                .FORWARD_SLASH + apiVersion
                + VoyageConstants.VOYAGE_API_STATUS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            responseEntityForUserRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.GET, entity,
                            String.class);
            status = Utils.getStatus(responseEntityForUserRequest);
        } catch (RestClientException e) {
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage().trim();
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRequest);
    }

    @Then("^I should obtain the following$")
    public void iShouldObtainTheFollowing(String arg0) throws Throwable {
        Assert.assertNotNull(status);
        Assert.assertNotNull(status.getStatus());
        Assert.assertTrue(responseEntityForUserRequest.getStatusCode()
                .toString().trim().equals(HttpStatus.OK.toString()));
    }

    @And("^with url for status \"([^\"]*)\"$")
    public void withUrlForStatus(String arg0) throws Throwable {
        Assert.assertNotNull(arg0);
    }

    @When("^I request the login through JWT token for status$")
    public void iRequestTheLoginThroughJWTTokenForStatus() throws Throwable {
        String serviceUrlForStatus = serverUrl + VoyageConstants
                .FORWARD_SLASH + apiVersion
                + VoyageConstants.VOYAGE_API_STATUS_PATH;
        HttpHeaders headers =
                Utils.buildBasicHttpHeadersForBearerAuthentication(
                        invalidAuthTokenAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            responseEntityForUserRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus,
                            HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            Assert.assertTrue(e.getMessage().trim().equals(HttpStatus
                    .UNAUTHORIZED.toString()));
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage().trim();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.fail();
    }

    @Then("^I should get a failed login message \"([^\"]*)\"$")
    public void iShouldGetAFailedLoginMessage(String arg0) throws Throwable {
        Assert.assertTrue(HTTP_401_UNAUTHORIZED_MESSAGE
                .equals(HttpStatus.UNAUTHORIZED.toString()));
    }
}
