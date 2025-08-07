package config;

public class ApiRoutes {
    // Auth routes
    public static final String BASE_PATH = "http://localhost:8081/api";
    public static final String AUTH_BASE = BASE_PATH + "/auth";
    public static final String REGISTER = AUTH_BASE + "/register";
    public static final String LOGIN = AUTH_BASE + "/login";
    public static final String UPDATE_PROFILE = AUTH_BASE + "/update";
    // Deposit routes
    public static final String DEPOSITS_BASE = BASE_PATH + "/Deposit";
    public static final String TIME_DEPOSITS = DEPOSITS_BASE + "/request-custom";
    public static final String CROWD_DEPOSITS = DEPOSITS_BASE + "/crowd";
    public static final String AVAILABLE_OFFERS =DEPOSITS_BASE + "/Available";
    public static final String CROWD_DEPOSIT_ENROLL = DEPOSITS_BASE + "/Crowd/enroll";

    // Top-up routes
    public static final String TOP_UP_BASE = BASE_PATH + "/TopUp";
    public static final String CREATE_TOP_UP = TOP_UP_BASE;
    public static final String GET_TOP_UPS = TOP_UP_BASE;

    // Admin routes
    public static final String ADMIN_BASE = BASE_PATH + "/admin";
    public static final String MANAGE_DEPOSITS = ADMIN_BASE + "/deposits";
    public static final String CROWD_DEPOSIT_OFFER_PATH = ADMIN_BASE + "/crowd/deposit-offer";
    public static final String UPDATE_CROWD_DEPOSIT_OFFER =ADMIN_BASE + "/crowd/deposit-offer";


    // Deposit  routes

}
