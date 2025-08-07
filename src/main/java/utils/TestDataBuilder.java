package utils;
import models.admin.CrowdDepositOfferRequest;
import models.auth.LoginRequest;
import models.auth.RegisterRequest;
import models.auth.UpdateProfileRequest;
import models.deposits.CrowdDepositEnrollRequest;
import models.deposits.TimeDepositRequest;
import models.topup.TopUpRequest;

public class TestDataBuilder {

    // Generate unique email for each test run
    public static String generateUniqueEmail() {
        return "testuser_" + System.currentTimeMillis() + "@stc.com";
    }


//    // This method builds a valid register  request for admin with dynamic email generation
    public static RegisterRequest buildAdminRegisterRequest() {
        return new RegisterRequest(
                generateUniqueEmail(),  // Use dynamic email
                "Password123!",
                "ashraf",
                "tahoon",
                0
        );
    }

    // Regular user registration (role 1)
    public static RegisterRequest buildUserRegisterRequest() {
        return new RegisterRequest(
                generateUniqueEmail(),
                "UserPass123!",
                "Regular",
                "User",
                1  // Regular user role
        );
    }


    //    // This method builds a valid login request for user  with a known email and password
    public static LoginRequest buildUserLoginRequest() {
        return new LoginRequest(
                "user2@gm.com",
                "password"
        );
    }

    //    // This method builds a valid login request for user  with a known email and password
    public static LoginRequest buildAdminLoginRequest() {
        return new LoginRequest(
                "admin@gm.com",
                "password"
        );
    }

    // This method builds a valid update profile request with new details
    public static UpdateProfileRequest buildUpdateProfileRequest() {
        return new UpdateProfileRequest(
                "updated_" + System.currentTimeMillis() + "@test.com",
                "newPassword123!",
                "UpdatedFirstName",
                "UpdatedLastName"
        );
    }

    // This method builds a valid time deposit request with a positive amount and duration
    public static TimeDepositRequest buildValidDepositRequest() {
        return new TimeDepositRequest(5000.0, 30); // $5000 for 30 days
    }

    // This method builds a time deposit request with an invalid amount (e.g., negative amount)
    public static TimeDepositRequest buildInvalidAmountDeposit() {
        return new TimeDepositRequest(-100.0, 30); // Negative amount
    }

    // This method builds a time deposit request with an invalid duration (e.g., 0 days)
    public static TimeDepositRequest buildInvalidDurationDeposit() {
        return new TimeDepositRequest(1000.0, 0);
        // 0 days duration
    }

    // This method builds a valid crowd deposit offer with a positive amount, period, and valid dates
    public static CrowdDepositOfferRequest buildValidCrowdDepositOffer() {
        return new CrowdDepositOfferRequest(
                50000,    // $50,000
                30,         // 30 days
                "2025-08-08T05:37:17.812Z", // Start date
                "2025-09-06T05:37:17.812Z", // Maturity date (same as start for simplicity)
                4.5       // 5.25% APY
        );
    }

    // This method builds an invalid crowd deposit offer with negative amount, zero period, and maturity before start
    public static CrowdDepositOfferRequest buildInvalidCrowdDepositOffer() {
        return new CrowdDepositOfferRequest(
                -1000.0,    // Negative amount
                0,          // 0 days period
                "2025-08-08T05:37:17.812Z", // Start date
                "2025-08-09T05:37:17.812Z", // Maturity date (same as start for simplicity)
                -1.0        // Negative APY
        );
    }

    // This method builds a valid top-up request with a positive amount
    public static TopUpRequest buildValidTopUpRequest() {
        return new TopUpRequest(100.0); // $100 top-up
    }

    // This method builds an invalid top-up request with a negative amount
    public static TopUpRequest buildInvalidTopUpRequest() {
        return new TopUpRequest(-50.0); // Negative amount
    }


    public static CrowdDepositEnrollRequest buildValidEnrollRequest(int offerId) {
        return new CrowdDepositEnrollRequest(
                offerId,
                100, // Default amount
                "2025-08-06T09:38:06.518Z" // 3 months rollover
        );
    }

    public static CrowdDepositEnrollRequest buildInvalidEnrollRequest(int offerId) {
        return new CrowdDepositEnrollRequest(
                 offerId,
                -100.0, // Invalid amount
               "2025-08-06T09:38:06.518Z" // Past date
        );
    }



}