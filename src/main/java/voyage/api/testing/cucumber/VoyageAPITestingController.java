package voyage.api.testing.cucumber;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Voyage API Testing Controller runs the specific integration tests
 */
@RestController
@RequestMapping("/api/v1")
public class VoyageAPITestingController {

    /**
     * @api {get} /voyageautomation voyage automation for runTestCaseForVoyageAPIAuthenticationToken
     * @apiVersion 1.0.0
     * @apiName runTestCaseForVoyageAPIAuthenticationToken
     * @apiGroup authentication
     *
     * @apiDescription Creates a new authentication token for a valid user
     *
     *
     * @apiExample {json} Example body:
     * {
     * }
     *
     * @apiHeader (Response Headers) {String} access_token of the newly created token
     * @apiHeader (Response Headers) {String} token_type of the newly created token
     * @apiHeader (Response Headers) {String} expires_in time of the newly created token
     * @apiHeader (Response Headers) {String} scope of the newly created token
     * @apiHeader (Response Headers) {String} created date of the newly created token
     * @apiHeader (Response Headers) {String} jti of the newly created token
     *
     * @apiHeaderExample {json} Voyage API Authentication Token Test
     * HTTP/1.1 201: Created
     * {
     *     "Location": "https://my-app/api/v1/automation/generatetoken"
     * }
     *
     **/
    @GetMapping("/automation/generatetoken")
    public ResponseEntity<?> runTestCaseForVoyageAPIAuthenticationToken(){
        // TODO: This is a sample code, the actual implementation will call the actual service class, which will run the appropriate BDD test class and returns the result
        String sampleResponse = "{\n" +
                "   \"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVkIjoxNTEwNTg0NDkzMjU0LCJzY29wZSI6WyJSZWFkIERhdGEiLCJXcml0ZSBEYXRhIl0sImV4cCI6MTUxMDU5MTY5MywiYXV0aG9yaXRpZXMiOlsiYXBpLnBlcm1pc3Npb25zLmRlbGV0ZSIsImFwaS5yb2xlcy5kZWxldGUiLCJhcGkucm9sZXMudXBkYXRlIiwiYXBpLnBlcm1pc3Npb25zLmxpc3QiLCJhcGkucGVybWlzc2lvbnMudXBkYXRlIiwiYXBpLnVzZXJzLmNyZWF0ZSIsImFwaS51c2Vycy5nZXQiLCJhcGkudXNlcnMubGlzdCIsImFwaS5wZXJtaXNzaW9ucy5nZXQiLCJhcGkucm9sZXMuZ2V0IiwiYXBpLnVzZXJzLnVwZGF0ZSIsImFwaS5yb2xlcy5jcmVhdGUiLCJhcGkudXNlcnMuZGVsZXRlIiwiYXBpLnBlcm1pc3Npb25zLmNyZWF0ZSIsImFwaS5yb2xlcy5saXN0Il0sImp0aSI6IjhlMDU2MmU4LTY0YzEtNDk2Yi04OTViLWU2ZWY4OTNkMDNhOCIsImNsaWVudF9pZCI6ImNsaWVudC1zdXBlciJ9.o2SnZRPe0cv08IcreoXXhcESuA-QIPhyO9FEqOUcjSK37pdCwVhfbsS877zaoA4i5tQ4iAQ49vMy5Ag7tn300Zw4QwXsRRzOpfeo50XeUOJAobMJwa-MpIgKd4jGqpjsKQbnI2oKOs_CBW5G63qWCdLYzi8AYvO1cTVFDzD2D0Yg_Rc9iF4XvA32URgi0LQTni8OxM_dn5nHHr53WbFy3EdJvRZN60LCX9sUHCKYVkpmJ_oY4iIqouXGUC8W0q91u4alejzTIDIFYeNdI1GyssK1DuAFvsR032KWHjpNraGeFzfgK8vdgUOPrSquZIJ2dSuSsMM10Be3hv43CPR9Pg\",\n" +
                "   \"token_type\":\"bearer\",\n" +
                "   \"expires_in\":7199,\n" +
                "   \"scope\":\"Read Data Write Data\",\n" +
                "   \"created\":1510584493254,\n" +
                "   \"jti\":\"8e0562e8-64c1-496b-895b-e6ef893d03a8\"\n" +
                "}";
        return new ResponseEntity<Object>(sampleResponse, HttpStatus.OK);
    }

}
