package endpoints.auth;
import config.ApiConfig;
import config.ApiRoutes;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import models.auth.RegisterRequest;
import io.restassured.RestAssured;
import models.auth.UpdateProfileRequest;
import utils.TestContext;
import utils.TestDataBuilder;

import static config.ApiRoutes.*;
import static io.restassured.RestAssured.given;


public class AuthEndpoint {
    static {
        RestAssured.baseURI = ApiConfig.getBaseUrl();
    }

    public static Response register(RegisterRequest request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(REGISTER);
    }

    public static Response login(LoginRequest request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(LOGIN);
    }

    public static Response updateProfile(UpdateProfileRequest request) {
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
                .post(UPDATE_PROFILE);
    }


    public static Response getProfile() {
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
                .get(UPDATE_PROFILE);
    }




}
