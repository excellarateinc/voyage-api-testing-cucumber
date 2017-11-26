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

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**.
 * Cucumber Runner will run the cucumber integration tests, it adds as a
 * hooks to control the test case execution
 */
@Component
@RunWith(Cucumber.class)
@CucumberOptions(format = {"pretty", "json:src/main/resources/static/cucumber"
        + ".json", "html:src/main/resources/static"},
        features = "src/test/resources/features",
        glue = "com.lssinc.voyage.api.cucumber",
        dryRun = false)
@PropertySource(value = {"classpath:application.yml"})
public class CucumberRunner {
    public CucumberRunner() {

    }
}

