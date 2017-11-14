 # voyage-api-testing-cucumber
Voyage REST API integration testing framework based on Cucumber and Java Spring Boot


 ### voyage api project structure

 #### Start new project
 #####1. Folder structure

<pre>
+---.idea
+---src
|   +---cucumber
|   |   |---main
|   |   |   |---resources
|   |   |   |   \--voyage.sample.featurefile
|   |   |   +---stepdefinitions
|   |   |   |   \--VoyageStepDefinition(s).step
|   +---main
|   |   +---java
|   |   |   \---voyage.api.testing.cucumber
|   |   |   |   \--VoyageAPITestingController.java
|   |   |   |   \--VoyageApiTestingCucumberApplication.java
|   |   +---resources
|   |   |   \--application.yml
|   +---test
|   |   +---java
|   |   |   +---voyage.api.testing.cucumber
|   |   |   |   \--VoyageApiTestingCucumberApplicationTests.java
|   |   +---resources
|   |   |   +---stepdefinitions
|   |   |   |   \--VoyageAuthenticationTokenStepDefinition(s).step
|   |       +---features
|   |           |---VoyageAuthenticationToken.feature
</pre>

#####2. Install dependencies
Now we can get the project dependencies installed:

Run the cucumber tests like this:

    ./gradlew clean cucumber

Observe the console for test result. Also find cucumber test reports in directory `build/cucumber`.

Run the gradle build

    ./gradlew build

Observer the dependent jars downloaded


Run the gradle build

    ./gradlew bootRun

Observer the server startup

from the browser Run this:

    http://localhost:8083/api/v1/automation/generatetoken

You should get a static responce

    {
       "access_token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwNTg0NDkzMjU0LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDU5MTY5MywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjhlMDU2MmU4LTY0YzEtNDk2Yi04OTViLWU2ZWY4OTNkMDNhOCIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.o2SnZRPe0cv08IcreoXXhcESuA-QIPhyO9FEqOUcjSK37pdCwVhfbsS877zaoA4i5tQ4iAQ49vMy5Ag7tn300Zw4QwXsRRzOpfeo50XeUOJAobMJwa-MpIgKd4jGqpjsKQbnI2oKOs_CBW5G63qWCdLYzi8AYvO1cTVFDzD2D0Yg_Rc9iF4XvA32URgi0LQTni8OxM_dn5nHHr53WbFy3EdJvRZN60LCX9sUHCKYVkpmJ_oY4iIqouXGUC8W0q91u4alejzTIDIFYeNdI1GyssK1DuAFvsR032KWHjpNraGeFzfgK8vdgUOPrSquZIJ2dSuSsMM10Be3hv43CPR9Pg",
       "token_type":"bearer",
       "expires_in":7199,
       "scope":"Read Data Write Data",
       "created":1510584493254,
       "jti":"8e0562e8-64c1-496b-895b-e6ef893d03a8"
    }
