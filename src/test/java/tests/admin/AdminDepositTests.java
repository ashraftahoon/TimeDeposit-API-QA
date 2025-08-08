package tests.admin;

import endpoints.deposits.CrowdDepositEndpoint;
import io.restassured.response.Response;
import models.admin.CrowdDepositOfferRequest;
import models.deposits.CrowdDepositEnrollRequest;
import org.testng.annotations.Test;
import utils.TestDataBuilder;

import static org.hamcrest.Matchers.equalTo;

public class AdminDepositTests {
    int firstOfferId;
    // Test to create a valid crowd deposit offer
    @Test(priority = 1)
    void adminCanCreateDepositOffer() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        Response response = CrowdDepositEndpoint.createCrowdDepositOffer(request);
        response.then()
                .log().ifError()
                .statusCode(200)
                .body(equalTo("Crowd deposit offer created"));
    }

    // Test to create a valid crowd deposit offer
    @Test (priority = 2)
    void createInvalidCrowdDepositOffer_ShouldFail() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildInvalidCrowdDepositOffer();
        Response response = CrowdDepositEndpoint.createCrowdDepositOffer(request);
        response.then()
                .log().ifError()
                .statusCode(400);
    }

    // Test to create an offer with a future start date
    @Test (priority = 3)
    void createOffer_Unauthorized_ShouldFail() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        Response response = CrowdDepositEndpoint.createCrowdDepositOffer(request);
        response.then()
                .log().ifError()
                .statusCode(403);
    }

    // Test to create an offer with a past start date
    @Test (priority = 4)
    void createOffer_WithPastStartDate_ShouldFail() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        request.setStartDate("2025-08-01T05:37:17.812Z");
        CrowdDepositEndpoint.createCrowdDepositOffer(request)
                .then()
                .log().ifError()
                .statusCode(400);
    }

    // Test to create an offer with extreme APY
    @Test (priority = 5)
    void createOffer_WithExtremeApy_ShouldFail() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        request.setApy(500.0); // 500% APY
        CrowdDepositEndpoint.createCrowdDepositOffer(request)
                .then()
                .statusCode(400);
    }

    // Test to update a deposit offer
    @Test (priority = 6)
    void adminCanUpdateDepositOffer() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        request.setId(5);
        request.setAmount(2000);
        Response response = CrowdDepositEndpoint.updateOffer(request);

        response.then()
                .log().ifError()
                .statusCode(200)
                .body( equalTo("Crowd deposit offer updated"));
    }

    // Test to get all deposit offers
    @Test (priority = 7)
    void adminCanGetDepositOffer() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        Response response = CrowdDepositEndpoint.getCrowdDepositOffers();

        // Extract first offer's ID
        firstOfferId = response.jsonPath().getInt("crowdDepositOffers[0].id");
        System.out.println("First Offer ID: " + firstOfferId);
        // Get the last offer in the array
        response.then()
                .statusCode(200)
                .log().all()
                .body("crowdDepositOffers[-1].amount", equalTo(200.0f)) // Last item amount
                .body("crowdDepositOffers[-1].apy", equalTo(500f))      // Last item APY
                .body("crowdDepositOffers[-1].period", equalTo(30))     // Last item period
                .body("crowdDepositOffers[-1].startDate", equalTo("2025-08-08T05:37:17.812Z"))
                .body("crowdDepositOffers[-1].maturityDate", equalTo("2025-09-06T05:37:17.812Z"));

    }

    // Test to delete a deposit offer
    @Test (priority = 8)
    void adminCanDeleteDepositOffer() {
        CrowdDepositOfferRequest request = TestDataBuilder.buildValidCrowdDepositOffer();
        Response response = CrowdDepositEndpoint.deleteOffer(firstOfferId);
        // Use the firstOfferId extracted earlier
        request.setId(firstOfferId);
        response.then()
                .log().ifError()
                .statusCode(200)
                .body( equalTo("Crowd deposit offer successfully deleted"));
    }
}
