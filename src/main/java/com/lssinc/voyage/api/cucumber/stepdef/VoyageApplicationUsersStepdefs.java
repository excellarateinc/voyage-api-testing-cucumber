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
import com.lssinc.voyage.api.cucumber.domain.User;
import com.lssinc.voyage.api.cucumber.util.Utils;
import com.lssinc.voyage.api.cucumber.util.VoyageConstants;
import com.sun.glass.ui.Application;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
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
public class VoyageApplicationUsersStepdefs {
    /**
     * file to store the to be deleted index
     */
    public static final String DELETE_USER_FILE_INDEX = "deleteUserFileIndex"
            + ".txt";
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
    private static ResponseEntity<String> responseEntityForUserRequest = null;
    /**.
     *
     */
    private static ResponseEntity<String> responseEntityUserList = null;
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
    @Value("${voyageapi.serverurl}")
    private String serverUrl;
    /**.
     * voyage api server api version property loaded from properties file
     */
    @Value("${voyageapi.serverapiversion}")
    private String serverApiVersion;
    /**.
     * voyage api oauth token path property loaded from properties file
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

    @When("^user requests for list of users \"([^\"]*)\"$")
    public void userRequestsFor(String arg0) throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_USERS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.GET, entity,
                            String.class);

            users = Utils.getUsers(responseEntityForUserRequest);
            Assert.assertNotNull(users);
        } catch (RestClientException e) {
            Assert.fail();
            // not throwing the exception as its a negative testcase
        }
        Assert.assertNotNull(responseEntityForUserRequest);
    }

    @Given("^I have a valid jwt token$")
    public void iHaveAValidJwtToken() throws Throwable {
        responseSaved = getAuthToken();
        Assert.assertNotNull(responseSaved);
    }

    @And("^with users url \"([^\"]*)\"  for listing users$")
    public void withUsersUrlForListingUsers(String arg0) throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_USERS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.GET, entity,
                            String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().trim().equals(HttpStatus
                    .UNAUTHORIZED.toString()));
            HTTP_401_UNAUTHORIZED = e.getMessage().trim();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.fail();
    }

    @Then("^I should obtain the user list$")
    public void iShouldObtainTheUserList(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRequest);
        Assert.assertNotNull(users.get(0).getId());
    }

    @When("^user requests for \"([^\"]*)\" creating user$")
    public void userRequestsForCreatingUser(String arg0) throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_USERS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        int randomNumber = Utils.getRandomNumber();
        usernameForInserting = "FirstName" + randomNumber + "@app.com";
        String body =  updateRequestBodyFor();
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.POST, httpEntity,
                            String.class);
            Utils.writeIdToFile(DELETE_USER_FILE_INDEX, responseEntityUserList
                    .getBody());
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.assertNotNull(responseEntityUserList);
        Assert.assertTrue(responseEntityUserList.getStatusCode().toString()
                .equals(HttpStatus.CREATED.toString()));
    }

    /**.
     * @return returns the json test user request object
     */
    private String updateRequestBodyFor() {
        JSONObject request = null;
        try {
           String jsonString = "{  \n"
                   + "\t\"firstName\":\"FirstName4\",\n"
                   + "\t\"lastName\":\"LastName4\",\n"
                   + "\t\"username\":" + "\""  + usernameForInserting + "\",\n"
                   + "\t\"email\":" + "\"" + usernameForInserting + "\",\n"
                   + "\t\"password\":\"my-secure-password\",\n"
                   + "\t\"phones\":[  \n"
                   + "\t\t{  \n"
                   + "\t\t \"phoneType\":\"MOBILE\",\n"
                   + "\t\t \"phoneNumber\":\"6518886021\"\n"
                   + "\t\t}\n"
                   + "\t]\n"
                   + "}";

            request = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request.toString();
    }

    /**.
     * @return returns the json test user request object with missing
     * required parameter
     */
    private String updateRequestBodyForWithMissingRequiredParameters() {
        JSONObject request = null;
        try {

            String jsonString = "{\"firstName\":\"FirstName1\","
                    + "\"lastName\":\"LastName1\","
                    + "\"password\":\"my-secure-password\","
                    + "\"phones\":[{\"phoneType\":\"MOBILE\","
                    + "\"phoneNumber\":null\"}],"
                    + "\"email\":\"FirstName4@app.com\","
                    + "\"username\":usernameForInserting}";

            request = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request.toString();
    }

    /**.
     * @return returns the json test user request object for updating data
     */
    private String updateRequestBodyForUpdating() {
        JSONObject request = null;
        try {
            String jsonString = "{\"id\":1,\"firstName\":\"Super"
                    + "\",\"lastName\":\"User\","
                    + "\"username\":\"super\","
                    + "\"email\":\"support@LighthouseSoftware.com\","
                    + "\"password\":\"$2a$10$.Qa2l9VysOeG5M8HhgUbQ"
                    + ".h8KlTBLdMY/slPwMtL/I5OYibYUFQle\","
                    + "\"isEnabled\":true,\"isAccountExpired\":false,"
                    + "\"isAccountLocked\":false,"
                    + "\"isCredentialsExpired\":false,"
                    + "\"phones\":[{\"id\":1,"
                    + "\"phoneType\":\"MOBILE\","
                    + "\"phoneNumber\":\"16518886021\"},{\"id\":2,"
                    + "\"phoneType\":\"OFFICE\","
                    + "\"phoneNumber\":\"16518886022\"},{\"id\":3,"
                    + "\"phoneType\":\"HOME\","
                    + "\"phoneNumber\":\"16518886023\"},{\"id\":4,"
                    + "\"phoneType\":\"OTHER\","
                    + "\"phoneNumber\":\"16518886024\"}]}";

            request = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request.toString();
    }

    @Then("^I should obtain the following for creating user$")
    public void iShouldObtainTheFollowingForCreatingUser(String arg0)
            throws Throwable {
        Assert.assertTrue(responseEntityUserList.getBody() != null);
        Assert.assertTrue(responseEntityUserList.getStatusCode().toString()
                .equals(HttpStatus.CREATED.toString()));
    }


    @When("^user requests for \"([^\"]*)\" creating user with missing "
           + "required parameter$")
    public void
    userRequestsForCreatingUserWithMissingRequiredParameter(String arg0)
            throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_USERS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        String body =  updateRequestBodyForWithMissingRequiredParameters();
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityMissingRequiredParamUser = restTemplateBuilder
                    .build().exchange(serviceUrlForUsers, HttpMethod.POST,
                            httpEntity, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            responseEntityMissingRequiredParamUser = new
                    ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            Assert.assertTrue(e.getMessage().toString().trim()
                    .equals(HttpStatus.BAD_REQUEST.toString()));
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.fail();
    }

    @Then("^I should obtain the following for creating user with missing "
           + "required parameter$")
    public void
    iShouldObtainTheFollowingForCreatingUserWithMissingRequiredParameter
            (String arg0) throws Throwable {
        Assert.assertTrue(responseEntityMissingRequiredParamUser
                .getStatusCode().toString()
                .equals(HttpStatus.BAD_REQUEST.toString()));
    }

    @When("^user requests for \"([^\"]*)\" deleting user$")
    public void userRequestsForDeletingUser(String arg0) throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants
                .FORWARD_SLASH + serverApiVersion + VoyageConstants
                .VOYAGE_API_USERS_PATH;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(
                        authenticationJwtToken.getAccess_token());
        String toBeDeletedRecord = Utils.readFile(DELETE_USER_FILE_INDEX);

        String deleteRecord = toBeDeletedRecord.substring(
                toBeDeletedRecord.indexOf(BEGIN_INDEX_OF_COLON) + 1,
                toBeDeletedRecord.indexOf(END_INDEX_DELETE_ID));
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);


        serviceUrlForUsers += VoyageConstants.FORWARD_SLASH
                + deleteRecord;
        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.DELETE,
                            httpEntity, String.class, deleteRecord);
            Utils.writeIdToFile(DELETE_USER_FILE_INDEX, "");
            HttpStatus status = responseEntityUserList.getStatusCode();
            Assert.assertTrue(status == HttpStatus.NO_CONTENT);
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
            return;
        }
    }



    @Then("^I should obtain the deleted record id in response$")
    public void iShouldObtainTheDeletedRecordIdInResponse(String arg0)
            throws Throwable {
        Assert.assertTrue(responseEntityUserList.getStatusCode()
                .toString().equals(HttpStatus.NO_CONTENT.toString()));
    }

    @When("^user requests for \"([^\"]*)\" user details by id$")
    public void userRequestsForUserDetailsById(String arg0) throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants.FORWARD_SLASH
                + serverApiVersion
                + VoyageConstants.VOYAGE_API_USERS_PATH
                + VoyageConstants.FORWARD_SLASH
                + VoyageConstants.VOYAGE_API_RETRIEVE_RECORD;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.GET, httpEntity,
                            String.class);
            Assert.assertTrue(HttpStatus.OK.toString().equals(
                    responseEntityUserList.getStatusCode().toString().trim()));
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.fail();
            return;
        }
    }

    @Then("^I should obtain user details in response$")
    public void iShouldObtainUserDetailsInResponse(String arg0)
            throws Throwable {
        Assert.assertTrue(HttpStatus.OK.toString().equals(
                responseEntityUserList.getStatusCode().toString().trim()));
    }

    @When("^user requests for updating \"([^\"]*)\" user details by id$")
    public void userRequestsForUpdatingUserDetailsById(String arg0)
            throws Throwable {
        String serviceUrlForUsers = serverUrl + VoyageConstants.FORWARD_SLASH
                + serverApiVersion
                + VoyageConstants.VOYAGE_API_USERS_PATH
                + VoyageConstants.FORWARD_SLASH
                + VoyageConstants.VOYAGE_API_RETRIEVE_RECORD;
        HttpHeaders headers =
                Utils.buildBasicHttpHeadersForBearerAuthentication
                        (authenticationJwtToken.getAccess_token());
        String body =  updateRequestBodyForUpdating();
        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(serviceUrlForUsers, HttpMethod.PUT, httpEntity,
                            String.class);
            users = Utils.getUsers(responseEntityForUserRequest);
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.fail();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.assertNotNull(responseEntityUserList);
    }

    @Then("^I should get the updated user details in response$")
    public void iShouldGetTheUpdatedUserDetailsInResponse(String arg0) throws
                                                               Throwable {
        Assert.assertTrue(responseEntityUserList.getStatusCode()
                == HttpStatus.OK);
        Assert.assertNotNull(users.get(0).getUsername());
    }

    @When("^I request the login through JWT token for users$")
    public void iRequestTheLoginThroughJWTTokenForUsers() throws Throwable {
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication
                        (accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(oAuthTokenUrl, HttpMethod.GET, httpEntity,
                            String.class);
            Assert.assertTrue(HttpStatus.UNAUTHORIZED.toString().equals(
                    responseEntityUserList.getStatusCode().toString().trim()));
        } catch (RestClientException e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().trim().equals(HttpStatus
                    .UNAUTHORIZED.toString()));
            HTTP_401_UNAUTHORIZED = e.getMessage().trim();
            return;
        }
        Assert.fail();
    }

    @Then("^I should get a failure message \"([^\"]*)\"$")
    public void iShouldGetAFaileureMessage(String arg0) throws Throwable {
        Assert.assertTrue(HTTP_401_UNAUTHORIZED
                .equals(HttpStatus.UNAUTHORIZED.toString()));
    }
}
