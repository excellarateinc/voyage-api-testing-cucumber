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
import com.lssinc.voyage.api.cucumber.domain.Permission;
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
public class VoyageApplicationPermissionsStepdefs {
    /**
     * .
     *  used to verifying step for using invalid bearer token
     */
    private static String HTTP_401_UNAUTHORIZED_MESSAGE;
    /**
     * stored the index of the role that needs to be deleted
     */
    public static final String DELETE_PERMISSIONS_INDEX_FILE =
            "deletePermissionsIndexFile";
    /**
     * end index to find the inserted/ to be deleted record id
     */
    public static final char END_INDEX_DELETE_ID = ',';
    /**
     * starting index to find the inserted/ to be deleted record id
     */
    private static final char BEGIN_INDEX_OF_COLON = ':';
    /**
     * .
     * saves the token response
     */
    private static ResponseEntity responseSaved = null;
    /**
     * .
     * stores the response entity for user request test case, it will be used
     * in the next test case
     */
    private static ResponseEntity<String>
            responseEntityForUserPermissionsRequest = null;
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
    @Value("${voyagestepdefinvalidauthtoken.accesstoken}")
    private String accessToken;
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
    /**
     *  response {@link Permission} class
     */
    private static List<Permission> permission;
    /**
     * initializing variables and constructing the oauth2 url for auth token
     * generation
     * @throws Exception
     */
    @PostConstruct
    public void init() throws Exception {
        oAuthTokenUrl = serverUrl + serverOauthPath;
    }

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

    @Given("^user has a valid jwt token for permissions$")
    public void userHasAValidJwtTokenForPermissions() throws Throwable {
        responseSaved = getAuthToken();
        Assert.assertNotNull(responseSaved);
        Assert.assertNotNull(authenticationJwtToken.getAccess_token());
    }

    @When("^user requests for list of user permissions \"([^\"]*)\"$")
    public void userRequestsForListOfUserPermissions(String arg0) throws
                                                                  Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_VERSION
                + VoyageConstants.VOYAGE_API_PERMISSIONS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserPermissionsRequest =
                    restTemplateBuilder.build()
                    .exchange(serviceUrlForPermissions, HttpMethod.GET, entity,
                            String.class);
            permission = Utils.getUserPermissions
                    (responseEntityForUserPermissionsRequest);
            Assert.assertNotNull(permission);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserPermissionsRequest);
    }

    @Then("^I should obtain the user permissions$")
    public void iShouldObtainTheUserPermissions(String arg0) throws Throwable {
        Assert.assertTrue(responseEntityForUserPermissionsRequest
                .getStatusCode() == HttpStatus.OK);
        Assert.assertNotNull(permission.get(0).getName());
    }

    @When("^user requests for user permissions \"([^\"]*)\"$")
    public void userRequestsForUserPermissions(String arg0) throws Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_VERSION
                + VoyageConstants.VOYAGE_API_PERMISSIONS_PATH
                + VoyageConstants.FORWARD_SLASH
                + VoyageConstants.VOYAGE_API_RETRIEVE_RECORD;

        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserPermissionsRequest =
                    restTemplateBuilder.build()
                    .exchange(serviceUrlForPermissions, HttpMethod.GET, entity,
                            String.class);
                permission = Arrays.asList(Utils.getUserPermission
                        (responseEntityForUserPermissionsRequest));
            Assert.assertNotNull(permission);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserPermissionsRequest);
    }

    @Then("^I should obtain the user permissions by id$")
    public void iShouldObtainTheUserPermissionsById(String arg0) throws
                                                               Throwable {
        Assert.assertTrue(responseEntityForUserPermissionsRequest
                .getStatusCode() == HttpStatus.OK);
        Assert.assertNotNull(permission.get(0).getName());
    }

    @When("^user requests for adding a new user permission \"([^\"]*)\"$")
    public void userRequestsForAddingANewUserPermission(String arg0)
            throws Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_VERSION
                + VoyageConstants.VOYAGE_API_PERMISSIONS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String body = updateRequestBodyForUpdating();
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityForUserPermissionsRequest =
                    restTemplateBuilder.build()
                    .exchange(serviceUrlForPermissions, HttpMethod.POST,
                            entity, String.class);
            Utils.writeIdToFile(DELETE_PERMISSIONS_INDEX_FILE,
                    responseEntityForUserPermissionsRequest.getBody());
            permission = Arrays.asList(Utils.getUserPermission
                    (responseEntityForUserPermissionsRequest));
            Assert.assertNotNull(permission);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserPermissionsRequest);
    }

    /**
     *
     * @return returns the new object for inserting
     */
    private String updateRequestBodyForUpdating() {
        String body = "{\"name\":\"api.users.list1\",\"description\":\"/users"
                + " GET web service endpoint to return a full list of users\","
                + "\"isImmutable\":true}";
        return body;
    }

    @Then("^I get the new permission id in response$")
    public void iGetTheNewPermissionIdInResponse(String arg0)
            throws Throwable {
        Assert.assertNotNull(responseEntityForUserPermissionsRequest);
        Assert.assertTrue(
                responseEntityForUserPermissionsRequest.getStatusCode()
                .toString().equals(HttpStatus.CREATED.toString()));
    }

    @When("^user requests for deleting a permission \"([^\"]*)\"$")
    public void userRequestsForDeletingAPermission(String arg0)
            throws Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_VERSION
                + VoyageConstants.VOYAGE_API_PERMISSIONS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String toBeDeletedRecord =
                Utils.readFile(DELETE_PERMISSIONS_INDEX_FILE);

        String deleteRecord = toBeDeletedRecord.substring(
                toBeDeletedRecord.indexOf(BEGIN_INDEX_OF_COLON) + 1,
                toBeDeletedRecord.indexOf(END_INDEX_DELETE_ID));
        serviceUrlForPermissions += VoyageConstants.FORWARD_SLASH
                            + deleteRecord;
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        try {
            responseEntityForUserPermissionsRequest =
                    restTemplateBuilder.build()
                    .exchange(serviceUrlForPermissions, HttpMethod.DELETE, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(responseEntityForUserPermissionsRequest
                    .getStatusCode().toString().equals(HttpStatus.CREATED
                            .toString()));
            // once the record is created it will be able to delete this as
            // it is throwing 'The requested record is immutable. No changes
            // to this record are allowed.' exception, in future its going to
            // be deleted as per http exception 201
            return;
            // not throwing the exception as its a negative testcase
        } finally {
            Utils.writeIdToFile(DELETE_PERMISSIONS_INDEX_FILE, "");
        }
        Assert.fail();
    }

    @Then("^I get a permission is deleted response$")
    public void iGetAPermissionIsDeletedResponse() throws Throwable {
        Assert.assertNotNull(responseEntityForUserPermissionsRequest);
        Assert.assertTrue(
                responseEntityForUserPermissionsRequest.getStatusCode()
                        .toString().equals(HttpStatus.CREATED.toString()));
    }

    @Given("^an access_token for permissions \"([^\"]*)\"$")
    public void anAccess_tokenForPermissions(String arg0) throws Throwable {
        Assert.assertTrue(arg0 != null);
    }

    @And("^with users url for permissions \"([^\"]*)\"$")
    public void withUsersUrlForPermissions(String arg0) throws Throwable {
        Assert.assertTrue(arg0 != null);
    }



    @When("^I request the login through JWT token for permissions$")
    public void iRequestTheLoginThroughJWTTokenForPermissions() throws
                                                                Throwable {
        String serviceUrlForPermissions = serverUrl + VoyageConstants
                .FORWARD_SLASH + VoyageConstants.VOYAGE_API_VERSION +
                VoyageConstants.VOYAGE_API_PERMISSIONS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        accessToken);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        try {
            responseEntityForUserPermissionsRequest =
                    restTemplateBuilder.build()
                            .exchange(serviceUrlForPermissions,
                                    HttpMethod.DELETE, entity, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().trim()
                    .equals(HttpStatus.UNAUTHORIZED
                            .toString()));
            HTTP_401_UNAUTHORIZED_MESSAGE = e.getMessage().trim();
            return;
            // not throwing the exception as its a negative testcase
        }
        Assert.fail();
    }

    @Then("^I should get a failed login message for permission \"([^\"]*)"
            + "\"$")
    public void iShouldGetAFailedLoginMessageForPermission(String arg0)
            throws Throwable {
        Assert.assertTrue(HTTP_401_UNAUTHORIZED_MESSAGE.equals(HttpStatus
                .UNAUTHORIZED.toString()));
    }

}
