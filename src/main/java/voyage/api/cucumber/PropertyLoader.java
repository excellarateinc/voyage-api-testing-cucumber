/*
 *
 *  * Copyright 2017 Lighthouse Software, Inc.   http://www.LighthouseSoftware.com
 *  *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  * contributor license agreements.  See the NOTICE file distributed with
 *  * this work for additional information regarding copyright ownership.
 *  * The ASF licenses this file to You under the Apache License, Version 2.0
 *  * (the "License"); you may not use this file except in compliance with
 *  * the License.  You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package voyage.api.cucumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
// import org.springframework.context.annotation.PropertySource;
/**.
 * loads the application properties required by the integration tests
 */
@Configuration
@Component
@EnableAutoConfiguration
@ConfigurationProperties
@PropertySource(value = "classpath:application.yml")
public class PropertyLoader{
    /**
     * .
     * voyage api's authentication server url
     */
    /*@Value("${voyagestepdefinitions.specflowfeatureoauth2tokenrequeststeps.authtypebasic.applicationcontenttype}")*/
    /**
     *
     */
    private String authUrl;

    public String getAuthUrl() {
        return authUrl;
    }


    /**.
     *
     */
    @Value("$voyagestepdef.baseUrl")
    private String authtypebasic;


}
