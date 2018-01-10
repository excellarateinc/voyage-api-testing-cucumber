Feature: Feature Users
  In order to add account information
  As a user of voyage application
  I want to submit the user account information

  Scenario: submit user account information
    Given I have a valid jwt token for account submission
    When user requests for account submission "http://localhost:8080/api/v1/accounts"
    Then I should submit account information
    """
    { "userName": "abc", "email": "abc@abc.com", "password": "abcD@1234", "confirmPassword": "abcD@1234", "firstName": "string", "lastName": "string", "phoneNumbers": [ { "id": 0, "userId": "1", "phoneNumber": "14155552671", "phoneType": 0, "verificationCode": "string" } ] }
    """

  Scenario: submit user account information using invalid jwt token
    Given an access_token "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwMjMwNDIzOTE4LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDIzNzYyMywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjM1ZmMzNjM1LWUyNmMtNDUwMi05OTMyLTNmMDhlOTQwZGQzNiIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.gBgsG5lho32ulgOikpr31yMXbM2vQ_MZQUmfPRdAHF8HkuL23qW8cBb-64vBkFGvyvT6OkofmCsBNfnLSfkEHqClaKoOb2wPQhlOnJfLnI7F85zh_SaHTyN1zGPOx3j3EhxCjxir5d8DGVCeqgVB8JoonaTqd5O3-v4xiYplKE0qRQZyuqQuGjeIUOIrhwMfPkyZyZuQs-BhnpidKeDL1fGa5BRtcTXvurbVchz2ex_C4HQgTZe8E7zfloYwBmmKUUi7HPe-zS98U5uBXlLGodXZnM-gkzqvYEMEhPoIIKe_CXnwgEof0b2ENqK8325_KcJ_iirKK3keyJveZmpT3g"
    And with users url "http://localhost:8080/api/v1/accounts"  for listing users
    When I request the login through JWT token for users
    Then I should get a failure message "The remote server returned an error: (401) Unauthorized."
