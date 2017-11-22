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
|   |   |   |   |---voyage.api.testing.cucumber.controller
|   |   |   |   |   \---VoyageApiTestingController.java
|   |   |   |   |---voyage.api.testing.cucumber.util
|   |   |   |   |   \---Utils.java
|   |   |   |   |   \---VoyageConstants.java
|   |   |   |   |---voyage.api.testing.cucumber.service
|   |   |   |   |   \---CucumberRunnerService.java
|   |   |   |   \---CucumberRunner.java
|   |   |   |   \---package-info.java
|   |   |   |   \--VoyageApiTestingCucumberApplication.java
|   |   |   |   \--VoyageApplicationAuthenticationStepdefs.step
|   |   +---resources
|   |   |   \--application.yml
|   +---test
|   |   +---java
|   |   |   +---com.lssinc.voyage.api.cucumber.test
|   |   |   |   \--VoyageApiTestingCucumberApplicationTests.java
|   |   +---resources
|   |       +---features
|   |   |   |   |---VoyageAuthenticationToken.feature
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

You should get an example responce
<pre>
[
  {
    "line": 1,
    "elements": [
      {
        "line": 6,
        "name": "getting Oauth2 Token",
        "description": "",
        "id": "specflowfeatureoauth2tokenrequest;getting-oauth2-token",
        "type": "scenario",
        "keyword": "Scenario",
        "steps": [
          {
            "result": {
              "status": "undefined"
            },
            "line": 7,
            "name": "a Oauth2 url \"http://localhost:8080/oauth/token\"",
            "match": {},
            "keyword": "Given "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 8,
            "name": "with token name \u0027Voyage SUPER\u0027",
            "match": {},
            "keyword": "And "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 9,
            "name": "with Client ID \u0027client-super\u0027",
            "match": {},
            "keyword": "And "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 10,
            "name": "with Client Secret \u0027secret\u0027",
            "match": {},
            "keyword": "And "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 11,
            "name": "with Grant Type \u0027client_credentials\u0027",
            "match": {},
            "keyword": "And "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 12,
            "name": "I request the Oauth2 token form of this url",
            "match": {},
            "keyword": "When "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 13,
            "name": "I should obtain the following JSON message \"Successful\"",
            "match": {},
            "keyword": "Then "
          }
        ]
      },
      {
        "line": 15,
        "name": "invalid JWT Token",
        "description": "",
        "id": "specflowfeatureoauth2tokenrequest;invalid-jwt-token",
        "type": "scenario",
        "keyword": "Scenario",
        "steps": [
          {
            "result": {
              "status": "undefined"
            },
            "line": 16,
            "name": "an access_token \"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwMjMwNDIzOTE4LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDIzNzYyMywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjM1ZmMzNjM1LWUyNmMtNDUwMi05OTMyLTNmMDhlOTQwZGQzNiIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.gBgsG5lho32ulgOikpr31yMXbM2vQ_MZQUmfPRdAHF8HkuL23qW8cBb-64vBkFGvyvT6OkofmCsBNfnLSfkEHqClaKoOb2wPQhlOnJfLnI7F85zh_SaHTyN1zGPOx3j3EhxCjxir5d8DGVCeqgVB8JoonaTqd5O3-v4xiYplKE0qRQZyuqQuGjeIUOIrhwMfPkyZyZuQs-BhnpidKeDL1fGa5BRtcTXvurbVchz2ex_C4HQgTZe8E7zfloYwBmmKUUi7HPe-zS98U5uBXlLGodXZnM-gkzqvYEMEhPoIIKe_CXnwgEof0b2ENqK8325_KcJ_iirKK3keyJveZmpT3g\"",
            "match": {},
            "keyword": "Given "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 17,
            "name": "with \"http://localhost:8080/api/v1/statuses\"",
            "match": {},
            "keyword": "And "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 18,
            "name": "I request the login through JWT token",
            "match": {},
            "keyword": "When "
          },
          {
            "result": {
              "status": "undefined"
            },
            "line": 19,
            "name": "I should get a failed login message \"The remote server returned an error: (401) Unauthorized.\"",
            "match": {},
            "keyword": "Then "
          }
        ]
      }
    ],
    "name": "SpecFlowFeatureOauth2TokenRequest",
    "description": "In order to authenticate a user\r\nAs a user of voyage application\r\nI want to be able to access resource based on my access permissions",
    "id": "specflowfeatureoauth2tokenrequest",
    "keyword": "Feature",
    "uri": "features/VoyageAuthenticationToken.feature"
  }
]
</pre>
