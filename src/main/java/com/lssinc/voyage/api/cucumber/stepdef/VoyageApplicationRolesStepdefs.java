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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lssinc.voyage.api.cucumber.VoyageApiTestingCucumberApplication;
import com.lssinc.voyage.api.cucumber.domain.AuthenticationJwtToken;
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
import org.springframework.context.ApplicationContext;
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
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
public class VoyageApplicationRolesStepdefs {

    /**
     * .
     * OK message for verifying against the successful response
     */
    public static final String MESSAGE_OK_200 = "<200 OK";
    /**
     * .
     * token begin index
     */
    public static final int TOKEN_BEGIN_INDEX = 25;
    /**
     * .
     * token end index
     */
    public static final int TOKEN_END_INDEX = 1025;
    /**
     * .
     * voyage api status
     */
    public static final String VOYAGE_API_STATUS = "status";
    /**
     *  http 200 ok
     */
    private static final String HTTP_200_OK = null;
    /**
     * stored the index of the role that needs to be deleted
     */
    public static final String DELETE_ROLES_INDEX_FILE = "deleteRolesIndexFile";
    public static final int BEGIN_INDEX_DELETE_ID = 7;
    public static final char END_INDEX_DELETE_ID = ',';
    /**
     * .
     * saves the token response
     */
    private static ResponseEntity responseSaved = null;
    /**
     * .
     * OAUTH2_SUCCESS_MESSAGE is used to verifying steps to successful
     * oAuthToken generation
     */
    private static String OAUTH2_SUCCESS_MESSAGE;
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
    private static ResponseEntity<String>
            responseEntityForUserRolesRequest = null;
    /**.
     *
     */
    private static ResponseEntity<String> responseEntityUserList = null;
    /**
     * .
     * Rest template used to call rest services from Voyage API
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    /**
     * Authentication token of voyage application
     */
    private static AuthenticationJwtToken authenticationJwtToken;
    /**
     * .
     * application context
     */
    @Autowired
    private ApplicationContext context;
    /**.
     *
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String user;

    /**.
     *
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String password;

    /**.
     *
     */
    @Value("${voyagestepdef.oauthtokenurl}")
    private String oAuthTokenUrl;

    /**.
     *
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String clientId;

    /**.
     *
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String clientIdValue;

    /**.
     *
     */
    @Value("${voyagestepdef.clientsercret}")
    private String clientSecret;

    /**.
     *
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String clientSecretValue;

    /**.
     *
     */
    @Value("${voyagestepdef.granttype}")
    private String grantType;

    /**.
     *
     */
    @Value("${voyagestepdef.granttypevalue}")
    private String grantTypeValue;

    /**.
     *
     */
    @Value("${voyagestepdef.granttypevalue}")
    private String accessToken;

    /**.
     *
     */
    @Value("${voyagestepdefinvalidauthtoken.serviceurlforusers}")
    private String serviceUrlForStatus;
    /**
     * .
     */
    @Value("${voyagestepdefinvalidauthtoken.accesstoken}")
    private String invalidAuthTokenAccessToken;
    /**
     * .
     */
    @Value("${voyagestepdefinvalidauthtoken.serviceurlforstatus}")
    private String invalidAuthTokenServiceurlForStatus;
    /**
     * .
     */
    @Value("${voyagestepdefinvalidauthtoken.responsemessage}")
    private String invalidAuthTokenResponseMessage;;
    /**
     * missing parameter error message
     */
    private static String HTTP_400_MISSING_REQUIRED_PARAMETER;

    /**
     * missing parameter error message
     */
    private static String HTTP_401_UNAUTHORIZED;
    private static String HTTP_204_NO_CONTENT;
    private static String MESSAGE_404_UNAUTHORIZED;
    /**
     * Access token used for submitting the rest service request
     */
    private static String validAccessToken;

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

            ObjectMapper mapper = new ObjectMapper();
            String responseBody = response.getBody();
            InputStream stream = new ByteArrayInputStream(responseBody
                    .getBytes(StandardCharsets.UTF_8.name()));
            authenticationJwtToken = mapper.readValue
                    (stream, AuthenticationJwtToken.class);
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
        String serviceUrlForStatus = arg0;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.GET, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage();
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
        Assert.assertTrue(
           responseEntityForUserRolesRequest.getBody().length() > 0);
    }

    @And("^with users url \"([^\"]*)\"$")
    public void withUsersUrl(String arg0) throws Throwable {
        // invalid jwt token
        String serviceUrlForStatus = arg0;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        invalidAuthTokenAccessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.GET, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().trim()
                    .equals(HttpStatus.UNAUTHORIZED
                            .toString()));
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.fail();
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @When("^user requests for user roles \"([^\"]*)\"$")
    public void userRequestsForUserRoles(String arg0) throws Throwable {
        String serviceUrlForStatus = arg0;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.GET, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @Then("^I should obtain the user role by id$")
    public void iShouldObtainTheUserRoleById(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRolesRequest);
    }

    @When("^user requests for adding a new roles \"([^\"]*)\"$")
    public void userRequestsForAddingANewRoles(String arg0) throws Throwable {
        String serviceUrlForStatus = arg0;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String body =  updateRequestBodyForUpdating();
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.POST, entity,
                            String.class);
            responseEntityForUserRolesRequest.getStatusCode();
            Utils.writeIdToFile(DELETE_ROLES_INDEX_FILE,
                    responseEntityForUserRolesRequest
                    .getBody());

        } catch (Exception e) {
            e.printStackTrace();
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
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String toBeDeletedRecord = Utils.readFile(DELETE_ROLES_INDEX_FILE);

        String deleteRecord = toBeDeletedRecord.substring(
                BEGIN_INDEX_DELETE_ID,
                toBeDeletedRecord.indexOf(END_INDEX_DELETE_ID));
        arg0 = arg0.substring(0, arg0.lastIndexOf('/') + 1) +  deleteRecord;
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        try {
            responseEntityForUserRolesRequest = restTemplateBuilder.build()
                    .exchange(arg0, HttpMethod.DELETE, entity,
                            String.class);
            responseEntityForUserRolesRequest.getStatusCode();
            Utils.writeIdToFile(DELETE_ROLES_INDEX_FILE, "");

        } catch (Exception e) {
            e.printStackTrace();
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
}
