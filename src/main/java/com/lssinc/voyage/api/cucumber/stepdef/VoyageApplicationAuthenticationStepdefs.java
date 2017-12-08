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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
public class VoyageApplicationAuthenticationStepdefs {

    /**
     * .
     * OK message for verifying against the successful response
     */
    public static final String OK_200 = "<200 OK";
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
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String user;
    /**
     * .
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String password;
    /**
     * .
     */
    @Value("${voyagestepdef.oauthtokenurl}")
    private String oAuthTokenUrl;
    /**
     * .
     */
    @Value("${voyagestepdef.clientid}")
    private String clientId;
    /**
     * .
     */
    @Value("${voyagestepdef.clientidvalue}")
    private String clientIdValue;
    /**
     * .
     */
    @Value("${voyagestepdef.clientsercret}")
    private String clientSecret;
    /**
     * .
     */
    @Value("${voyagestepdef.clientsecretvalue}")
    private String clientSecretValue;
    /**
     * .
     */
    @Value("${voyagestepdef.granttype}")
    private String grantType;
    /**
     * .
     */
    @Value("${voyagestepdef.granttypevalue}")
    private String grantTypeValue;
    /**
     * .
     */
    @Value("${voyagestepdef.tokenname}")
    private String tokenName;
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
    private String invalidAuthTokenResponseMessage;
    /**
     * .
     * Rest template used to call rest services from Voyage API
     */
    @Autowired
    /**.
     *
     * @return RestTemplate
     */

    private RestTemplateBuilder restTemplateBuilder;
    /**
     * .
     */
    @Autowired
    private ApplicationContext context;
    /**
     * .
     */
    @Autowired
    private Environment environment;

    /**
     * .
     *
     * @return RestTemplate
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Given("^a Oauth(\\d+) url \"([^\"]*)\"$")
    public void a_Oauth_url(int arg1, String arg2) throws Throwable {
        URI url = null;
        try {
            url = new URI(arg2);
        } catch (Exception e) {
            Assert.fail();
            throw e;
        }
        Assert.assertNotNull(url);
    }

    @And("^with token name \"([^\"]*)\"$")
    public void withTokenName(String arg0) throws Throwable {
        Assert.assertEquals(tokenName, arg0);
    }

    @And("^with Client ID \"([^\"]*)\"$")
    public void withClientID(String arg0) throws Throwable {
        Assert.assertEquals(clientIdValue, arg0);
    }

    @Given("^with Client Secret 'secret'$")
    public void with_Client_Secret_secret() throws Throwable {
        Assert.assertNotNull(clientSecret);
    }

    @Given("^with Grant Type 'client_credentials'$")
    public void with_Grant_Type_client_credentials() throws Throwable {
        Assert.assertNotNull(grantType);
    }

    @When("^I request the 'Oauth(\\d+)' token form of this url$")
    public void iRequestTheOauthTokenFormOfThisUrl(int arg0) throws Throwable {
        ResponseEntity response = null;
        try {
            response = getAuthToken();
        } catch (Exception e) {
            Assert.fail();
            throw e;
        }
        Assert.assertNotNull(response);
        Assert.assertTrue(response.toString().startsWith(OK_200));
        oauth2TokenSuccessMessage = HttpStatus.OK.toString();
    }

    /**
     * @return response entity of successful generated token
     */
    private ResponseEntity getAuthToken() throws Exception {
        ResponseEntity<String> response = null;
        try {

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

            response =
                    restTemplate.exchange(oAuthTokenUrl, HttpMethod.POST,
                            httpEntity,
                            String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
            throw e;
        }
        return response;
    }

    @Then("^I should obtain the following JSON message \"([^\"]*)\"$")
    public void i_should_obtain_the_following_JSON_message(String arg1)
            throws Throwable {
        Assert.assertEquals(oauth2TokenSuccessMessage, HttpStatus.OK
                .toString());
    }

    @Given("^an access_token \"([^\"]*)\"$")
    public void an_access_token(String arg1) throws Throwable {
        Assert.assertEquals(invalidAuthTokenAccessToken, arg1);
    }

    @Given("^with \"([^\"]*)\"$")
    public void with(String arg1) throws Throwable {
        Assert.assertEquals(invalidAuthTokenServiceurlForStatus, arg1);
    }

    @When("^I request the login through JWT token$")
    public void i_request_the_login_through_JWT_token() throws Throwable {
        HttpHeaders headers =
                Utils.buildBasicHttpHeadersForBearerAuthentication(
                        invalidAuthTokenAccessToken);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplateBuilder.build()
                    .exchange(invalidAuthTokenServiceurlForStatus,
                            HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(e.getMessage().trim().equals(HttpStatus
                    .UNAUTHORIZED.toString()));
            oauth401UnAuthorizedMessage = e.getMessage();
            // not throwing the exception as its a negative testcase
            return;
        }
        Assert.fail();
    }

    @Then("^I should get a failed login message \"([^\"]*)\"$")
    public void iShouldGetAFailedLoginMessage(String arg0) throws Throwable {
        Assert.assertTrue(oauth401UnAuthorizedMessage.startsWith("401"));
    }

    @And("^with Client Secret \"([^\"]*)\"$")
    public void withClientSecret(String arg0) throws Throwable {
        Assert.assertEquals(clientSecretValue, arg0);
    }

    @And("^with Grant Type \"([^\"]*)\"$")
    public void withGrantType(String arg0) throws Throwable {
        Assert.assertEquals(grantTypeValue, arg0);
    }

}