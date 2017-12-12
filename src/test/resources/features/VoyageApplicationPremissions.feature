Feature: Feature Permissions
  In order to get permission information
  As a user of voyage application
  I want to see the permissions for a given user

  Scenario: list all permissions
    Given user has a valid jwt token for permissions
    When user requests for list of user permissions "http://localhost:8080/api/v1/permissions"
    Then I should obtain the user permissions
    """
    [{"id":1, "name":"api.users.list", "description":"/users GET web service endpoint to return a full list of users", "isImmutable":true}]
    """

  Scenario: get permission details for a given user
    Given user has a valid jwt token for permissions
    When user requests for user permissions "http://localhost:8080/api/v1/permissions/1"
    Then I should obtain the user permissions by id
    """
    [{"id":1,"name":"api.users.list","description":"/users GET web service endpoint to return a full list of users","isImmutable":true}]
    """

  Scenario: add new user permission
    Given user has a valid jwt token for permissions
    When user requests for adding a new user permission "http://localhost:8080/api/v1/permissions"
    Then I get the new permission id in response
    """
    [{"id":1,"name":"api.users.list","description":"/users GET web service endpoint to return a full list of users","isImmutable":true}]
    """

  Scenario: delete a user permission
    Given user has a valid jwt token for permissions
    When user requests for deleting a permission "http://localhost:8080/api/v1/permissions/3"
    Then I get a permission is deleted response

  Scenario: list user permissions using invalid jwt token
    Given an access_token for permissions "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwMjMwNDIzOTE4LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDIzNzYyMywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjM1ZmMzNjM1LWUyNmMtNDUwMi05OTMyLTNmMDhlOTQwZGQzNiIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.gBgsG5lho32ulgOikpr31yMXbM2vQ_MZQUmfPRdAHF8HkuL23qW8cBb-64vBkFGvyvT6OkofmCsBNfnLSfkEHqClaKoOb2wPQhlOnJfLnI7F85zh_SaHTyN1zGPOx3j3EhxCjxir5d8DGVCeqgVB8JoonaTqd5O3-v4xiYplKE0qRQZyuqQuGjeIUOIrhwMfPkyZyZuQs-BhnpidKeDL1fGa5BRtcTXvurbVchz2ex_C4HQgTZe8E7zfloYwBmmKUUi7HPe-zS98U5uBXlLGodXZnM-gkzqvYEMEhPoIIKe_CXnwgEof0b2ENqK8325_KcJ_iirKK3keyJveZmpT3g"
    And with users url for permissions "http://localhost:8080/api/permission"
    When I request the login through JWT token for permissions
    Then I should get a failed login message for permission "The remote server returned an error: (401) Unauthorized."
