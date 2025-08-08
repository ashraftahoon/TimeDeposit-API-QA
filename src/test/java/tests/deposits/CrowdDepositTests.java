package tests.deposits;

import endpoints.deposits.CrowdDepositEndpoint;
import io.restassured.response.Response;
import models.deposits.CrowdDepositEnrollRequest;
import org.testng.annotations.Test;
import utils.TestDataBuilder;
import static org.hamcrest.Matchers.*;

public class CrowdDepositTests  {

    // Test to enroll in a crowd deposit with a valid offer ID
    @Test
    void userCanEnrollInCrowdDeposit() {
        CrowdDepositEnrollRequest request = TestDataBuilder.buildValidEnrollRequest(2);
        Response response = CrowdDepositEndpoint.enrollInOffer(request);

        response.then()
                .statusCode(200)
                .log().all()
                .body(equalTo("Enrolled"));
    }

    // Test to enroll in a crowd deposit with an invalid offer ID
    @Test
    void enrollWithInvalidAmount_ShouldFail() {
        CrowdDepositEnrollRequest request =
                TestDataBuilder.buildInvalidEnrollRequest(2);

        CrowdDepositEndpoint.enrollInOffer(request)
                .then()
                .log().ifError()
                .statusCode(400);
    }

    // Test to enroll in a crowd deposit with an amount less than the balance
    @Test
    void enrollWithInsufficientBalance_ShouldFail() {
        CrowdDepositEnrollRequest request = TestDataBuilder.buildInvalidEnrollRequest(3);
        request.setAmount(1000000); // Set amount greater than available balance
        CrowdDepositEndpoint.enrollInOffer(request)
                .then()
                .log().ifError()
                .statusCode(400)
                .body(containsString("Insufficient funds for this deposit please charge"));
    }

    // Test to verify that the user can retrieve all deposits
    @Test
    void getAllDeposits_ShouldReturnValidData() {
        // Get and verify raw response
        CrowdDepositEndpoint.getAllDeposits()
                .then()
                .log().all()
                .statusCode(200)
                .body("[0].id", notNullValue())
                .body("[0].amount", anyOf(nullValue(), isA(Number.class)))
                .body("[0].type", equalTo(1)) // Assuming 1 is Time Deposit
                .body("[0].status", anyOf(nullValue(), isA(Integer.class)));
    }





}


