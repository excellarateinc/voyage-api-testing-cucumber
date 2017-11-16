
/*
 *
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
 *
 */
package com.lssinc.voyage.api.cucumber.test;

import cucumber.api.PendingException;

/**.
 *
 */
public class VoyageAuthenticationTokenTestingSteps {
    /**
     *  step definitions for voyage authenticaion by using requesting
     *  authenticatoin token from voyage api
     */
    public VoyageAuthenticationTokenTestingSteps() {
        @Given("^a Oauth(\\d+) url \"([^\"]*)\"$")
        public void a_Oauth_url(int arg1, String arg2) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^with token name 'Voyage SUPER'$")
        public void with_token_name_Voyage_SUPER() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^with Client ID 'client-super'$")
        public void with_Client_ID_client_super() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^with Client Secret 'secret'$")
        public void with_Client_Secret_secret() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^with Grant Type 'client_credentials'$")
        public void with_Grant_Type_client_credentials() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @When("^I request the Oauth(\\d+) token form of this url$")
        public void i_request_the_Oauth_token_form_of_this_url(int arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Then("^I should obtain the following JSON message \"([^\"]*)\"$")
        public void i_should_obtain_the_following_JSON_message(String arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^an access_token \"([^\"]*)\"$")
        public void an_access_token(String arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Given("^with \"([^\"]*)\"$")
        public void with(String arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @When("^I request the login through JWT token$")
        public void i_request_the_login_through_JWT_token() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Then("^I should get a failed loggin message \"([^\"]*)\"$")
        public void i_should_get_a_failed_loggin_message(String arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }
}
