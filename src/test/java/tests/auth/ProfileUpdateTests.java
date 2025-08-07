package tests.auth;

import endpoints.auth.AuthEndpoint;
import io.restassured.response.Response;
import models.auth.UpdateProfileRequest;
import org.testng.annotations.Test;
import utils.TestDataBuilder;

public class ProfileUpdateTests {

    @Test
    void testSuccessfulProfileUpdate() {
        // 1. Prepare update request
        UpdateProfileRequest updateRequest = TestDataBuilder.buildUpdateProfileRequest();
        // 2. Execute update
        Response response = AuthEndpoint.updateProfile(updateRequest);
        // 3. Verify response
        response.then()
                .log().ifError()
                .statusCode(200);

    }

}
