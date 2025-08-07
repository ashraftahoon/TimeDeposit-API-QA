package tests.auth;

import endpoints.auth.AuthEndpoint;
import io.restassured.response.Response;
import models.auth.LoginRequest;
import org.testng.annotations.Test;
import utils.TestContext;
import utils.TestDataBuilder;

import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertNotNull;

public class LoginTests {

    @Test
    void testSuccessfulLogin() {
        LoginRequest request = TestDataBuilder.buildUserLoginRequest();
        Response response = AuthEndpoint.login(request);
        // 3. Validate response and extract token
        String token = response.then()
                .statusCode(200)
                .extract()
                .path("token");

        // 4. Print for debugging
        System.out.println("JWT Token: " + token);

        // 5. Store token in TestContext for later use
        TestContext.setAuthToken(token);
        // 6. Verify token was extracted
        assertNotNull(token, "Token should not be null");
    }


    @Test
    void loginWithWrongPassword_ShouldFail() {
        LoginRequest request = TestDataBuilder.buildUserLoginRequest();
        request.setPassword("123456789");
        Response response = AuthEndpoint.login(request);

        response.then()
                .statusCode(400)
                .log().ifError()
                .body( equalTo("Invalid credentials"));
    }

    @Test
    void loginWithEmptyPassword_ShouldFail() {

        LoginRequest request = TestDataBuilder.buildUserLoginRequest();
        request.setPassword("");
        Response response = AuthEndpoint.login(request);

        response.then()
                .statusCode(400)
                .body("errors.Password[0]", equalTo("Password is required."));
    }
}
