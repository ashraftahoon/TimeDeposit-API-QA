package tests.deposits;

import endpoints.topup.TopUpEndpoint;
import io.restassured.response.Response;
import models.topup.TopUpRequest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestDataBuilder;
import static org.hamcrest.Matchers.equalTo;

public class TopUpTests {
   public double balance;


   @BeforeClass
    public void getTopUps() {
        Response response = TopUpEndpoint.getTopUps();
        response.then()
                .log().all()
                .statusCode(200)
                .extract().response();
         balance = response.jsonPath().getDouble("balance");
    }


    @Test(priority = 1)
    void createValidTopUp_ShouldSucceed() {
        TopUpRequest request = TestDataBuilder.buildValidTopUpRequest();
        double topUpAmount = 100.0;  // The amount you are topping up
        double expectedBalance = balance + topUpAmount;
        String expectedMessage = "Account Successfully Topped Up, New Balance is " + expectedBalance;
        TopUpEndpoint.createTopUp(request)
                .then()
                .log().ifError()
                .statusCode(200)
                .body("message", equalTo(expectedMessage));
    }


    @Test(priority = 2)
    void createTopUpWithNegativeAmount_ShouldFail() {
        TopUpRequest request = TestDataBuilder.buildInvalidTopUpRequest();
        TopUpEndpoint.createTopUp(request)
                .then()
                .log().ifError()
                .statusCode(400);
    }



}
