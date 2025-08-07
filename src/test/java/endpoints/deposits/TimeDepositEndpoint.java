package endpoints.deposits;

import io.restassured.response.Response;
import models.deposits.TimeDepositRequest;
import utils.TestContext;
import utils.TestDataBuilder;

import static config.ApiRoutes.DEPOSITS_BASE;
import static config.ApiRoutes.TIME_DEPOSITS;
import static endpoints.auth.AuthEndpoint.login;
import static io.restassured.RestAssured.given;

public class TimeDepositEndpoint {
    public static Response requestCustomDeposit(TimeDepositRequest request) {
        String token = TestContext.getAuthToken();

        if (token == null) {
            // Auto-login if token is missing
            Response loginResponse = login(TestDataBuilder.buildUserLoginRequest());
            token = "Bearer " + loginResponse.jsonPath().getString("token");
            TestContext.setAuthToken(token);
        }
        return given()
                .contentType("application/json")
                .header("Authorization", token)
                .body(request)
                .when()
                .post(TIME_DEPOSITS);
    }


    public static Response getDeposits() {
        String token = TestContext.getAuthToken();

        if (token == null) {
            // Auto-login if token is missing
            Response loginResponse = login(TestDataBuilder.buildUserLoginRequest());
            token = "Bearer " + loginResponse.jsonPath().getString("token");
            TestContext.setAuthToken(token);
        }
        return given()
                .header("Authorization",token)
                .when()
                .get(DEPOSITS_BASE);
    }
}
