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

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**.
 * Cucumber Runner will run the cucumber integration tests, it acts as a
 * hook to control the test case execution
 */
@Component
@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "src/main/resources/static/cucumber.json",
        retryCount = CucumberRunner.CUCUMBER_EXTENDED_RETRY_COUNT,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        coverageReport = true,
        jsonUsageReport = "src/main/resources/static/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        excludeCoverageTags = {"@flaky" },
        includeCoverageTags = {"@passed" },
        outputFolder = "cucumber-results")
@CucumberOptions(format = {"pretty",
        "json:src/main/resources/static/cucumber.json",
        "html:src/main/resources/static",
        "junit:src/main/resources/static/cucumber.xml",
        "usage:src/main/resources/static/cucumber-usage.json"},
        features = "src/test/resources/features",
        glue = "com.lssinc.voyage.api.cucumber",
        dryRun = false)
@PropertySource(value = {"classpath:application.yml"})
public class CucumberRunner {

    /**.
     * cucumber retry count
     */
    public static final int CUCUMBER_EXTENDED_RETRY_COUNT = 3;

    public CucumberRunner() {

    }
}

