Feature: SpecFlowFeatureGetStatus
  In order to role information
  As a user of voyage application
  I want to see the user roles related information

  Scenario: list all roles
    Given user has a valid jwt token for roles
    When user requests for list of user roles "http://localhost:8080/api/v1/roles"
    Then I should obtain the user roles
    """
    [{"id": 1, "name": "Super User", "authority": "role.super"}]
    """

  Scenario: list role details
    Given user has a valid jwt token for roles
    When user requests for user roles "http://localhost:8080/api/v1/roles/1"
    Then I should obtain the user role by id
    """
    [{"id": 1, "name": "Super User", "authority": "role.super"}]
    """

  Scenario: add new role
    Given user has a valid jwt token for roles
    When user requests for adding a new roles "http://localhost:8080/api/v1/roles"
    Then I get the new role id in response
    """
    [{"id": 1, "name": "Super User", "authority": "role.super"}]
    """

  Scenario: delete a role
    Given user has a valid jwt token for roles
    When user requests for deleting a roles "http://localhost:8080/api/v1/roles/3"
    Then I get a role is deleted response

  Scenario: list user roles using invalid jwt token
    Given an access_token "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwMjMwNDIzOTE4LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDIzNzYyMywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjM1ZmMzNjM1LWUyNmMtNDUwMi05OTMyLTNmMDhlOTQwZGQzNiIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.gBgsG5lho32ulgOikpr31yMXbM2vQ_MZQUmfPRdAHF8HkuL23qW8cBb-64vBkFGvyvT6OkofmCsBNfnLSfkEHqClaKoOb2wPQhlOnJfLnI7F85zh_SaHTyN1zGPOx3j3EhxCjxir5d8DGVCeqgVB8JoonaTqd5O3-v4xiYplKE0qRQZyuqQuGjeIUOIrhwMfPkyZyZuQs-BhnpidKeDL1fGa5BRtcTXvurbVchz2ex_C4HQgTZe8E7zfloYwBmmKUUi7HPe-zS98U5uBXlLGodXZnM-gkzqvYEMEhPoIIKe_CXnwgEof0b2ENqK8325_KcJ_iirKK3keyJveZmpT3g"
    And with users url "http://localhost:8080/api/roles"
    When I request the login through JWT token
    Then I should get a failed login message "The remote server returned an error: (401) Unauthorized."
