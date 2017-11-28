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
package com.lssinc.voyage.api.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lssinc.voyage.api.cucumber.domain.AuthenticationJwtToken;
import com.lssinc.voyage.api.cucumber.util.Utils;
import com.lssinc.voyage.api.cucumber.util.VoyageConstants;
import com.sun.glass.ui.Application;
import cucumber.api.PendingException;
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
import org.springframework.http.converter.json
        .AbstractJackson2HttpMessageConverter;
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
public class VoyageApplicationUsersStepdefs {

    /**
     * .
     * OK message for verifying against the successful response
     */
    public static final String OK_200 = "<200 OK";
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
     * .
     * saves the token response
     */
    private static ResponseEntity responseSaved = null;
    /**
     * .
     * oauth2TokenSuccessMessage is used to verifying steps to successful
     * oAuthToken generation
     */
    private static String oauth2TokenSuccessMessage;
    /**
     * .
     * oauth401 is used to verifying step for using invalid bearer token
     */
    private static String oauth401UnAuthorizedMessage;
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
     * .
     * Rest template used to call rest services from Voyage API
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
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
    @Value("${voyagestepdefinvalidauthtken.serviceurlforusers}")
    private String serviceUrlForStatus;

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
            AuthenticationJwtToken readValue = mapper.readValue(stream,
                    AuthenticationJwtToken.class);
            System.out.print("");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            throw e;
        }
        return response;
    }

    @When("^user requests for list of users \"([^\"]*)\"$")
    public void userRequestsFor(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int beginIndex = TOKEN_BEGIN_INDEX;
        int endIndex = TOKEN_END_INDEX;
        String accessToken = responseSaved.toString().substring(beginIndex,
                endIndex);
        String serviceUrlForStatus = arg0;
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(accessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityForUserRequest = restTemplateBuilder.build()
                    .exchange(serviceUrlForStatus, HttpMethod.GET, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            oauth401UnAuthorizedMessage = e.getMessage();
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
    @And("^with users url \"([^\"]*)\"$")
    public void withUsersUrl(String arg0) throws Throwable {
        String token = responseSaved.toString().substring(TOKEN_BEGIN_INDEX, TOKEN_END_INDEX);
        HttpHeaders headers = Utils
                .buildBasicHttpHeadersForBearerAuthentication(token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        try {
            responseEntityUserList = restTemplateBuilder.build()
                    .exchange(arg0, HttpMethod.GET, entity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().contains("404"));
            oauth401UnAuthorizedMessage = e.getMessage();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.fail();
    }

    @Then("^I should obtain the user list$")
    public void iShouldObtainTheUserList(String arg0) throws Throwable {
        Assert.assertNotNull(responseEntityForUserRequest);
    }


}
