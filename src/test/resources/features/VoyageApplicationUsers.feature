Feature: SpecFlowFeatureGetStatus
  In order to get user information
  As a user of voyage application
  I want to see the user related information

  Scenario: list users
    Given I have a valid jwt token
    When user requests for list of users "http://localhost:8080/api/v1/users"
    Then I should obtain the user list
    """
    [{"id":1,"firstName":"Super","lastName":"User","username":"super",
    "email":"support@LighthouseSoftware.com","password":"$2a$10$
    .Qa2l9VysOeG5M8HhgUbQ.h8KlTBLdMY/slPwMtL/I5OYibYUFQle","isEnabled":true,
    "isAccountExpired":false,"isAccountLocked":false,
    "isCredentialsExpired":false,"phones":[{"id":1,"phoneType":"MOBILE",
    "phoneNumber":"16518886021"},{"id":2,"phoneType":"OFFICE",
    "phoneNumber":"16518886022"},{"id":3,"phoneType":"HOME",
    "phoneNumber":"16518886023"},{"id":4,"phoneType":"OTHER",
    "phoneNumber":"16518886024"}]},{"id":2,"firstName":"Standard",
    "lastName":"User","username":"standard","email":"standard@user.com",
    "password":"$2a$10$.Qa2l9VysOeG5M8HhgUbQ
    .h8KlTBLdMY/slPwMtL/I5OYibYUFQle","isEnabled":true,
    "isAccountExpired":false,"isAccountLocked":false,
    "isCredentialsExpired":false,"phones":[{"id":5,"phoneType":"MOBILE",
    "phoneNumber":"16518886025"},{"id":6,"phoneType":"OFFICE",
    "phoneNumber":"16518886027"},{"id":7,"phoneType":"HOME",
    "phoneNumber":"16518886028"},{"id":8,"phoneType":"OTHER",
    "phoneNumber":"16518886029"}]}]
    """

  Scenario: list users using invalid jwt token
    Given an access_token "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwMjMwNDIzOTE4LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDIzNzYyMywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjM1ZmMzNjM1LWUyNmMtNDUwMi05OTMyLTNmMDhlOTQwZGQzNiIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.gBgsG5lho32ulgOikpr31yMXbM2vQ_MZQUmfPRdAHF8HkuL23qW8cBb-64vBkFGvyvT6OkofmCsBNfnLSfkEHqClaKoOb2wPQhlOnJfLnI7F85zh_SaHTyN1zGPOx3j3EhxCjxir5d8DGVCeqgVB8JoonaTqd5O3-v4xiYplKE0qRQZyuqQuGjeIUOIrhwMfPkyZyZuQs-BhnpidKeDL1fGa5BRtcTXvurbVchz2ex_C4HQgTZe8E7zfloYwBmmKUUi7HPe-zS98U5uBXlLGodXZnM-gkzqvYEMEhPoIIKe_CXnwgEof0b2ENqK8325_KcJ_iirKK3keyJveZmpT3g"
    And with users url "http://localhost:8080/api/users"
    When I request the login through JWT token
    Then I should get a failed login message "The remote server returned an error: (401) Unauthorized."
