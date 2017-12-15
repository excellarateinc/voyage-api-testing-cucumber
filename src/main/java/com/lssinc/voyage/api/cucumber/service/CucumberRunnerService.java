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
package com.lssinc.voyage.api.cucumber.service;

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.lssinc.voyage.api.cucumber.CucumberRunner;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**.
 *  cucumber runner service runs the specific cucumber integration test
 */
@Service
public class CucumberRunnerService {
    /**.
     * application context
     */
    @Autowired
    private ApplicationContext context;

    /**.
     *  voyage authentication token runner, it runs integrated test cases
     *  related to authentication and authorization
     * @throws Exception if unable to generate a token
     */
    public void voyageApiAuthenticationRunner() throws Exception {

        try {
            Computer computer = new Computer();
            JUnitCore jUnitCore = new JUnitCore();
            jUnitCore.run(computer, CucumberRunner.class);
        } catch (Exception e) {
            throw e;
        }

    }

    /**.
     * generated the cucumber reports
     */
    public void getCucumberReports() throws Exception {
        try {
            String cucumberOutputFolder =
            context.getEnvironment().getProperty(""
                    + "cucumberproperties.outputdirectory");
            String cucumberOutputName =
                    context.getEnvironment().getProperty(""
                            + "cucumberproperties.outputname");
            String cucumberSourceFile =
                    context.getEnvironment().getProperty(""
                            + "cucumberproperties.sourcefile");
            CucumberDetailedResults results = new CucumberDetailedResults();
            results.setOutputDirectory(cucumberOutputFolder);
            results.setOutputName(cucumberOutputName);
            results.setSourceFile(cucumberSourceFile);
            results.execute(true);

        } catch (Exception e) {
            throw e;
        }
    }
}
