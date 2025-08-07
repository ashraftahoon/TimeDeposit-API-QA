package endpoints.topup;

import models.topup.TopUpRequest;
import io.restassured.response.Response;
import utils.TestContext;
import utils.TestDataBuilder;
import static config.ApiRoutes.CREATE_TOP_UP;
import static config.ApiRoutes.GET_TOP_UPS;
import static endpoints.auth.AuthEndpoint.login;
import static io.restassured.RestAssured.given;

public class TopUpEndpoint {


    public static Response createTopUp(TopUpRequest request) {
        String token = TestContext.getAuthToken();
        if (token == null) {
            // Auto-login if token is missing
            Response loginResponse = login(TestDataBuilder.buildUserLoginRequest());
            token = "Bearer " + loginResponse.jsonPath().getString("token");
            TestContext.setAuthToken(token);
        }

        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(request)
                .when()
                .post(CREATE_TOP_UP);
    }


    public static Response getTopUps() {
        String token = TestContext.getAuthToken();

        if (token == null) {
            // Auto-login if token is missing
            Response loginResponse = login(TestDataBuilder.buildUserLoginRequest());
            token = "Bearer " + loginResponse.jsonPath().getString("token");
            TestContext.setAuthToken(token);
        }
        return given()
                .header("Authorization", token)
                .when()
                .get(GET_TOP_UPS);
    }


}