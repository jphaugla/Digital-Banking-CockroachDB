package com.jphaugla.util;

/**
 * various constants such as error messages and configuration values that are not expected to change
 */

public class Constants {

    // list of possible error messages
    public static final String ERR_EMAIL_NOT_FOUND = "Email <%s> not found";
    public static final String ERR_PHONE_NOT_FOUND = "Phone <%s> not found";
    public static final String ERR_MERCHANT_NOT_FOUND = "Merchant <%s> not found";
    public static final String ERR_ACCOUNT_NOT_FOUND = "Account id <%s> not found";
    public static final String ERR_INVALID_ACCOUNT = "Account id <%s> is not valid id : %s";
    public static final String ERR_CUSTOMER_NOT_FOUND = "Customer id <%s> not found";
    public static final String ERR_INVALID_CUSTOMER = "Customer id <%s> is not valid id : %s";
    public static final String ERR_CUSTOMER_NOT_FOUND_2 = "No Customers found for <%s>";
    public static final String ERR_DISPUTE_NOT_FOUND = "Dispute id <%s> not found";
    public static final String ERR_TRANSACTION_NOT_FOUND = "Transaction id <%s> not found";
    public static final String ERR_TRANSACTION_RETURN_NOT_FOUND = "Transaction return <%s> not found";
    public static final String ERR_INVALID_TRANSACTION = "Transaction id <%s>is not valid id";
    public static final String ERR_INVALID_DISPUTE = "Dispute id <%s> is not valid id : %s";
    public static final String ERR_CUSTOMER_EMAIL_NOT_FOUND = "No email found for Customer id <%s>";
    public static final String ERR_CUSTOMER_PHONE_NOT_FOUND = "No phone found for Customer id <%s>";
    public static final String ERR_NO_TRANSACTIONS_FOUND_FOR = "No transaction found for <%s>";
    public static final String ERR_LON_INVALID = "Longitude must be between -180 and 180.";

    // success messages
    public static final String MSG_DELETED_EMAIL = "You have successfully deleted your account.";
    public static final String MSG_DELETED_VEHICLE = "Deleted vehicle with id <%s> from database.";
    public static final String MSG_RIDE_STARTED = "Ride started with vehicle %s";
    public static final String MSG_RIDE_ENDED_1 = "You have completed your ride on vehicle %s.";
    public static final String MSG_RIDE_ENDED_2 = "You traveled %.2f km in %.2f minutes, for an average velocity of %.2f km/hr";

    private Constants() {
    }
}
