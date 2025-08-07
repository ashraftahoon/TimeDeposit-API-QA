package tests.deposits;
import endpoints.deposits.TimeDepositEndpoint;
import io.restassured.response.Response;
import models.deposits.TimeDepositRequest;
import org.testng.annotations.Test;
import utils.TestDataBuilder;

import static org.hamcrest.Matchers.*;

public class TimeDepositTests {

    //
    @Test
    void createValidDeposit_ShouldSucceed() {
        TimeDepositRequest request = TestDataBuilder.buildValidDepositRequest();
        Response response = TimeDepositEndpoint.requestCustomDeposit(request);
        response.then()
                .statusCode(200)
                .log().ifError()
                .body(equalTo("Your deposit request has been created"));
    }

    // Test to create a deposit with a invalid amount and duration
    @Test
    void createDepositWithNegativeAmount_ShouldFail() {
        TimeDepositRequest request = TestDataBuilder.buildInvalidAmountDeposit();
        TimeDepositEndpoint.requestCustomDeposit(request)
                .then()
                .statusCode(400)
                .body("errors.Amount[0]", equalTo("amount must be greater than 0"));
    }

    // Test to create a deposit with a zero duration
    @Test
    void createDepositWithZeroDuration_ShouldFail() {
        TimeDepositRequest request = TestDataBuilder.buildInvalidDurationDeposit();
        TimeDepositEndpoint.requestCustomDeposit(request)
                .then()
                .statusCode(400)
                .body("errors.DurationInDays[0]", equalTo("'Duration In Days' must be greater than '0'."));

    }

}
