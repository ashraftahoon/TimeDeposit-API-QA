package endpoints.deposits;

import endpoints.auth.AuthEndpoint;
import io.restassured.response.Response;
import models.admin.CrowdDepositOfferRequest;
import models.auth.LoginRequest;
import models.deposits.CrowdDepositEnrollRequest;
import models.deposits.DepositResponse;
import utils.TestContext;
import utils.TestDataBuilder;

import java.util.List;

import static config.ApiRoutes.*;
import static io.restassured.RestAssured.given;

public class CrowdDepositEndpoint {

    private static String getAuthToken(boolean asAdmin) {
        String token = TestContext.getAuthToken();

        if (token == null || asAdmin != TestContext.isAdmin()) {
            LoginRequest loginRequest = asAdmin
                    ? TestDataBuilder.buildAdminLoginRequest()
                    : TestDataBuilder.buildUserLoginRequest();

            Response loginResponse = AuthEndpoint.login(loginRequest);
            token = "Bearer " + loginResponse.jsonPath().getString("token");
            TestContext.setAuthToken(token);
        }
        return token;
    }

    private static Response executeAuthorizedRequest(String method, String path, Object body, boolean asAdmin) {
        String token = getAuthToken(asAdmin);

        return given()
                .header("Authorization", token)
                .contentType("application/json")
                .body(body)
                .when()
                .request(method, path);
    }

    // Admin operations
    public static Response createCrowdDepositOffer(CrowdDepositOfferRequest request) {
        return executeAuthorizedRequest("POST", CROWD_DEPOSIT_OFFER_PATH, request, true);
    }

    public static Response updateOffer(CrowdDepositOfferRequest request) {
        return executeAuthorizedRequest("PUT", CROWD_DEPOSIT_OFFER_PATH, request, true);
    }

    public static Response deleteOffer(int offerId) {
        String requestBody = String.format("{\"id\": %d}", offerId);
        return executeAuthorizedRequest("DELETE", CROWD_DEPOSIT_OFFER_PATH, requestBody, true);
    }

    // User operations
    public static Response getCrowdDepositOffers() {
        String token = getAuthToken(false);
        return given()
                .header("Authorization", token)
                .when()
                .get(AVAILABLE_OFFERS);
    }

    public static Response enrollInOffer(CrowdDepositEnrollRequest request) {
        return executeAuthorizedRequest("POST", CROWD_DEPOSIT_ENROLL, request, false);
    }

    public static Response getAllDeposits() {
        String token = getAuthToken(false);
        return given()
                .header("Authorization", token)
                .when()
                .get(DEPOSITS_BASE);
    }


}