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
package com.lssinc.voyage.api.cucumber.contoller;

import com.lssinc.voyage.api.cucumber.CucumberRunner;
import com.lssinc.voyage.api.cucumber.voyage.api.cucumber.service
        .CucumberRunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**.
 * Voyage API Testing Controller runs the specific integration tests
 */
@RestController
@RequestMapping("/api/v1")
@EnableWebMvc
public class VoyageApiTestingController {

    /**.
     * cucumber runner
     */
    @Autowired
    private CucumberRunner runner;

    /**.
     * Rest template used to call rest services from Voyage API
     */
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    /**.
     *  invokes the specific cucumber service
     */
    @Autowired
    private CucumberRunnerService serviceRunner;
    /**.
     * cucumber reports url
     */
    @Value("${cucumberproperties.aggregatereport}")
    private String reportsUrl;

    /**
     * @return ResponseEntity - json response
     * @api {get} /automation/run voyage automation for
     * runIntegrationTestCasesForVoyageAPI
     * @apiVersion 1.0.0
     * @apiName runIntegrationTestCasesForVoyageAPI
     * @apiGroup authentication
     * @apiDescription triggers cucumber test cases
     * @apiExample {html} Example body:
     * {
     * }
     * @apiHeader (Response Headers) {html} status of the cucumber test cases
     * @apiHeaderExample {json} Voyage API Authentication Token Test
     * HTTP/1.1 201: Created
     * {
     * "Location": "https://my-app/api/v1/automation/run"
     * }
     **/
    @RequestMapping(value = "/automation/run", produces = {
            "application/xml", "text/html", "text/xml" })
    @ResponseBody
    public ResponseEntity runIntegrationTestCasesForVoyageAPI() {
        try {
            serviceRunner.voyageApiAuthenticationRunner();
            serviceRunner.getCucumberReports();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            //set response entity
            HttpEntity<Object> entity = new HttpEntity<Object>(headers);
            ResponseEntity<String> responseEntity =
                    restTemplateBuilder.build().exchange(reportsUrl, HttpMethod.GET,
                            entity, String.class);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            String message = (e.getMessage() == null)
                    ? HttpStatus.INTERNAL_SERVER_ERROR.toString() : e
                    .getMessage();
            ResponseEntity entity = new ResponseEntity(message, HttpStatus
                    .INTERNAL_SERVER_ERROR);
            return entity;
        }
    }

   /**.
    *
    * @return
    */
    @RequestMapping("/{path:[^\\.]+}*")
    public String forward() {
        return "forward:/index.html";
    }

}
