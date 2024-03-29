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
package com.lssinc.voyage.api.cucumber.test;

import org.junit.Test;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import com.lssinc.voyage.api.cucumber.CucumberRunner;

/**.
 *
 * Voyage api testing class
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
public class VoyageApiTestingCucumberApplicationTests {

    /**.
     *
     * context load test
     */
    @Test
    public void contextLoads() {
        Computer computer = new Computer();
        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.run(computer, CucumberRunner.class);
    }

}
