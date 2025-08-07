package tests.mocks.external;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import config.ApiConfig;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import config.ConfigReader;

public class PaymentMockTest {

    private static WireMockServer wireMockServer;
    private static String baseUrlMock;
    private static String baseUrl;
    private static boolean useMock;

    @BeforeClass
    public static void globalSetup() {
        useMock = ConfigReader.getBoolean("useMock");
        if (useMock) {
            startWireMockServer();
            setupMockEndpoints();
            baseUrlMock = "http://localhost:8089";
        } else {
           baseUrl= ApiConfig.getBaseUrl();
            System.out.println("test flag useMock is set to false, using real API endpoints.");
        }
    }

    private static void startWireMockServer() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    private static void setupMockEndpoints() {
        // Success
        stubFor(post(urlEqualTo("/api/topup"))
                .withRequestBody(containing("\"amount\""))
                .willReturn(okJson("{ \"success\": true, \"newBalance\": 1000 }")));

        // Invalid amount
        stubFor(post(urlEqualTo("/api/topup"))
                .withRequestBody(equalToJson("{ \"amount\": -100 }"))
                .willReturn(badRequest()
                        .withBody("{ \"error\": \"Amount must be positive\" }")));

        // Server error
        stubFor(post(urlEqualTo("/api/topup"))
                .withRequestBody(containing("\"amount\": 999999"))
                .willReturn(serverError()
                        .withBody("{ \"error\": \"Server overloaded\" }")));

        // Simulated delay
        stubFor(post(urlEqualTo("/api/topup"))
                .withRequestBody(containing("\"amount\": 300"))
                .willReturn(okJson("{ \"success\": true, \"newBalance\": 1300 }")
                        .withFixedDelay(3000)));
    }

    @AfterClass
    public static void tearDown() {
        if (useMock && wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    // ---------------------- TESTS ----------------------

    @Test
    public void testTopUp_Success() {
        String requestBody = "{ \"amount\": 500 }";

        if (useMock) {
            String response = RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .post(baseUrlMock + "/api/topup")
                    .then()
                    .statusCode(200)
                    .extract().asString();

            System.out.println("Mock Success Response: " + response);
            Assert.assertTrue(response.contains("\"success\""));

        } else {
            // Real API expected behavior
            double topUpAmount = 500.0;
            double currentBalance = 600.0; // Optional: fetch current balance before top-up
            double expectedBalance = currentBalance + topUpAmount;

            RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .post(baseUrlMock + "/api/topup")
                    .then()
                    .log().ifError()
                    .statusCode(200)
                    .body("success", (ResponseAwareMatcher<Response>) equalTo(String.valueOf(true)))
                    .body("newBalance", (ResponseAwareMatcher<Response>) equalTo(String.valueOf((float) expectedBalance))); // Adjust data type if needed
        }
    }

    @Test
    public void testTopUp_InvalidAmount_ShouldReturn400() {
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{ \"amount\": -100 }")
                .post(baseUrlMock + "/api/topup")
                .then()
                .statusCode(400)
                .extract().asString();

        System.out.println("Invalid Amount Response: " + response);
        Assert.assertTrue(response.contains("Amount must be positive"));
    }

    @Test
    public void testTopUp_ServerDelay_HandledWithinTimeout() {
        RestAssured.given()
                .contentType("application/json")
                .body("{ \"amount\": 300 }")
                .post(baseUrlMock + "/api/topup")
                .then()
                .time(lessThan(4000L))
                .statusCode(200);
    }

    @Test
    public void testTopUp_ServerError_ShouldReturn500() {
        String response = RestAssured.given()
                .contentType("application/json")
                .body("{ \"amount\": 999999 }")
                .post(baseUrlMock + "/api/topup")
                .then()
                .statusCode(500)
                .extract().asString();

        System.out.println("Server Error Response: " + response);
        Assert.assertTrue(response.contains("Server overloaded"));
    }
}
