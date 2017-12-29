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
import com.lssinc.voyage.api.cucumber.domain.Role;
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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
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
public class VoyageApplicationRolesStepdefs {
    /**
     * stored the index of the role that needs to be deleted
     */
    public static final String DELETE_ROLES_INDEX_FILE = "deleteRolesIndexFile";
    /**
     * end index to find the inserted/ to be deleted record id
     */
    public static final char END_INDEX_DELETE_ID = ',';
    /**
     * starting index to find the inserted/ to be deleted record id
     */
    public static final char BEGIN_INDEX_OF_COLON = ':';
    /**
     * .
     * saves the token response
     */
    private ResponseEntity responseSaved = null;
    /**
     * .
     *  used to verifying step for using invalid bearer token
     */
    private static String HTTP_401_UNAUTHORIZED_MESSAGE;
    /**
     * .
     * stores the response entity for user request test case, it will be used
     * in the next test case
     */
    private ResponseEntity<String>
            responseEntityForUserRolesRequest = null;
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
    /**
     * .
     */
    @Value("${voyagestepdefinvalidauthtoken.accesstoken}")
    private String invalidAuthTokenAccessToken;
    /**.
     * voyage api server url
     */
    @Value("${voyageapi.serverurl}")
    private String serverUrl;
    /**.
     * voyage api server api version
     */
    @Value("${voyageapi.serverapiversion}")
    private String serverApiVersion;
    /**.
     * voyage api oauth token path
     */
    @Value("${voyageapi.oauthpath}")
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
     * response {@link Role} class
     */
    private static List<Role> roles;
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


    @Given("^user has a valid jwt token$")
    public void userHasAValidJwtToken() throws Throwable {
       Assert.assertNotNull(authenticationJwtToken);
        Assert.assertNotNull(authenticationJwtToken.getAccess_token());
    }

    @When("^user requests for list of user roles \"([^\"]*)\"$")
    public void userRequestsForListOfUserRoles(String arg0) throws Throwable {
        String serviceUrlForRoles = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_ROLES_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForRoles, HttpMethod.GET, entity,
                            String.class);
            roles = Utils.getUserRoles(responseEntityForUserRolesRequest);
            Assert.assertNotNull(roles);
        } catch (RestClientException e) {
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage().trim();
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    private String updateRequestBodyForUpdating() {

        String body = "{\n"
               + "    \"name\": \"Super2 User2\",\n"
               +  "    \"authority\": \"role.super\"\n"
               +  "}";
        return body;
    }


    @Given("^user has a valid jwt token for roles$")
    public void userHasAValidJwtTokenForRoles() throws Throwable {
        responseSaved = getAuthToken();
        Assert.assertNotNull(responseSaved);
        Assert.assertNotNull(authenticationJwtToken.getAccess_token());
    }

    @Then("^I should obtain the user roles$")
    public void iShouldObtainTheUserRoleList(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRolesRequest);
        Assert.assertNotNull(roles.get(0).getName());
    }

    @And("^with users url \"([^\"]*)\"$")
    public void withUsersUrl(String arg0) throws Throwable {
        // invalid jwt token
        String serviceUrlForRoles = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_ROLES_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        invalidAuthTokenAccessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForRoles, HttpMethod.GET, entity,
                            String.class);
        } catch (RestClientException e) {
            Assert.assertTrue(e.getMessage().trim()
                    .equals(HttpStatus.UNAUTHORIZED
                            .toString()));
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage().trim();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.fail();
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @When("^user requests for user roles \"([^\"]*)\"$")
    public void userRequestsForUserRoles(String arg0) throws Throwable {
        String serviceUrlForRoles = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_ROLES_PATH + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_RETRIEVE_RECORD;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForRoles, HttpMethod.GET, entity,
                            String.class);
            roles = Arrays.asList(Utils.getUserRole
                    (responseEntityForUserRolesRequest));
            Assert.assertNotNull(roles);
        } catch (RestClientException e) {
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @Then("^I should obtain the user role by id$")
    public void iShouldObtainTheUserRoleById(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRolesRequest);
        Assert.assertNotNull(roles.get(0).getName());
    }

    @When("^user requests for adding a new roles \"([^\"]*)\"$")
    public void userRequestsForAddingANewRoles(String arg0) throws Throwable {
        String serviceUrlForRoles = serverUrl + VoyageConstants.FORWARD_SLASH
                + serverApiVersion + VoyageConstants.VOYAGE_API_ROLES_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String body =  updateRequestBodyForUpdating();
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForRoles, HttpMethod.POST, entity,
                            String.class);
            responseEntityForUserRolesRequest.getStatusCode();
            Utils.writeIdToFile(DELETE_ROLES_INDEX_FILE,
                    responseEntityForUserRolesRequest
                    .getBody());

        } catch (RestClientException e) {
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @Then("^I get the new role id in response$")
    public void iGetTheNewRoleIdInResponse(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRolesRequest);
        Assert.assertTrue(responseEntityForUserRolesRequest.getStatusCode()
                .toString().equals(HttpStatus.CREATED.toString()));
    }

    @When("^user requests for deleting a roles \"([^\"]*)\"$")
    public void userRequestsForDeletingARoles(String arg0) throws Throwable {
        String serviceUrlForRoles = serverUrl + VoyageConstants.FORWARD_SLASH
                + serverApiVersion
                + VoyageConstants.VOYAGE_API_ROLES_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String toBeDeletedRecord = Utils.readFile(DELETE_ROLES_INDEX_FILE);

        String deleteRecord = toBeDeletedRecord.substring(
                toBeDeletedRecord.indexOf(BEGIN_INDEX_OF_COLON) + 1,
                toBeDeletedRecord.indexOf(END_INDEX_DELETE_ID));
        serviceUrlForRoles += VoyageConstants.FORWARD_SLASH + deleteRecord;
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForRoles, HttpMethod.DELETE, entity,
                            String.class);
            responseEntityForUserRolesRequest.getStatusCode();
            Utils.writeIdToFile(DELETE_ROLES_INDEX_FILE, "");

        } catch (RestClientException e) {
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @Then("^I get a role cannot be deleted response$")
    public void iGetARoleIsDeletedResponse() throws Throwable {
        Assert.assertNotNull(responseEntityForUserRolesRequest);
        Assert.assertTrue(responseEntityForUserRolesRequest.getStatusCode()
                .toString().equals(HttpStatus.NO_CONTENT.toString()));
    }

    @Then("^I should get a failed message for roles \"([^\"]*)\"$")
    public void iShouldGetAFailedMessageForRoles(String arg0) throws Throwable {
        Assert.assertTrue(HTTP_401_UNAUTHORIZED_MESSAGE.equals(
                HttpStatus.UNAUTHORIZED.toString()));
    }

    @When("^I request the login through JWT token for permission$")
    public void iRequestTheLoginThroughJWTTokenForPermission() throws
                                                               Throwable {
        Assert.assertTrue(HTTP_401_UNAUTHORIZED_MESSAGE.equals(
                HttpStatus.UNAUTHORIZED.toString()));
    }
}
