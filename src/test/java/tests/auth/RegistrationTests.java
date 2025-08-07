package tests.auth;
import endpoints.auth.AuthEndpoint;
import io.restassured.response.Response;
import models.auth.RegisterRequest;
import org.testng.annotations.Test;
import utils.TestDataBuilder;

import static org.hamcrest.Matchers.equalTo;

public class RegistrationTests {

    @Test
    void testSuccessfulRegistration() {
        RegisterRequest request = TestDataBuilder.buildAdminRegisterRequest();
        Response response = AuthEndpoint.register(request);
        response.then()
                .log().ifError() // Log the response for debugging
                .statusCode(200);
    }

    @Test
    void testSuccessfulRegistrationWithUserAccount() {
        RegisterRequest request = TestDataBuilder.buildUserRegisterRequest();
        Response response = AuthEndpoint.register(request);
        response.then()
                .log().ifError() // Log the response for debugging
                .statusCode(200);
    }

    @Test
    void testRegisterWithExistingEmail_ShouldFail() {
        // First register a user
        RegisterRequest validRequest = TestDataBuilder.buildAdminRegisterRequest();
        AuthEndpoint.register(validRequest);

        // Try to register again with same email
        Response response = AuthEndpoint.register(validRequest);

        response.then()
                .log().ifError()
                .statusCode(400)
                .body(equalTo("User with this email already exists."))
                .extract().asString();
    }

    @Test
    void testRegisterWithShortPassword_ShouldFail() {
        RegisterRequest request = TestDataBuilder.buildAdminRegisterRequest();
        request.setPassword("short");

        Response response = AuthEndpoint.register(request);

        response.then()
                .statusCode(400)
                .log().ifError()
                .body("errors.Password[0]", equalTo("Password must be at least 8 characters long."));
    }

    @Test
    void testRegisterWithMissingFirstName_ShouldFail() {
        RegisterRequest request = TestDataBuilder.buildAdminRegisterRequest();
        request.setFirstName(null);

        Response response = AuthEndpoint.register(request);

        response.then()
                .statusCode(400)
                .log().ifError()
                .body("errors.FirstName[0]", equalTo("The FirstName field is required."));
    }

}
