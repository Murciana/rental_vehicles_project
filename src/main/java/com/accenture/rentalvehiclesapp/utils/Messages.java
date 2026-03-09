package com.accenture.rentalvehiclesapp.utils;


public class Messages {

    private Messages() {
    }

    //LOGGED IN USER
    public static final String USER_NULL = "user.null";
    public static final String USER_LAST_NAME_NULL = "user.lastname.null";
    public static final String USER_FIRST_NAME_NULL = "user.firstname.null";
    public static final String USER_EMAIL_NULL = "user.email.null";
    public static final String USER_EMAIL_PATTERN_INVALID = "user.email.invalid";
    public static final String USER_EMAIL_DUPLICATE = "user.email.duplicate";
    public static final String USER_PASSWORD_NULL = "user.password.null";
    public static final String USER_PASSWORD_SIZE = "user.password.size";
    public static final String USER_PASSWORD_PATTERN_INVALID = "user.password.invalid";

    //ADMIN
    public static final String ADMIN_NOT_FOUND = "admin.id.not-found";
    public static final String ADMIN_DELETION_UNAUTHORIZED = "admin.deletion.unauthorized";

    // CUSTOMER
    public static final String CUSTOMER_NOT_FOUND = "customer.id.not-found";
    public static final String CUSTOMER_UNDERAGE = "customer.birthdate.underage";
    public static final String CUSTOMER_BIRTHDATE_NULL = "customer.birthdate.null";
    public static final String CUSTOMER_BIRTHDATE_FUTURE = "customer.birthdate.future";
    public static final String CUSTOMER_ADDRESS_NULL = "customer.address.null";

    //ADDRESS
    public static final String ADDRESS_STREET_NULL = "address.street.null";
    public static final String ADDRESS_POSTCODE_NULL = "address.postcode.null";
    public static final String ADDRESS_POSTCODE_PATTERN_INVALID = "address.postcode.invalid";
    public static final String ADDRESS_CITY_NULL = "address.city.null";


    //LICENCE
    public static final String LICENCE_NOT_FOUND = "licence.id.notfound";
    public static final String LICENCE_NULL = "licence.null";
    public static final String LICENCE_NAME_NULL = "licence.name.null";
    public static final String LICENCE_NAME_LENGTH = "licence.name.length";

    //VEHICLE
    public static final String VEHICULE_ALREADY_REMOVED = "vehicle.already.removed";
    public static final String VEHICULE_NULL = "vehicle.null";

    //CAR MESSAGES
    public static final String CAR_NOT_FOUND = "car.id.not-found";
    public static final String CAR_DOORS_INVALID = "car.doors.invalid";

    //MOTORCYCLE
    public static final String MOTORCYCLE_NOT_FOUND = "motorcycle.id.not-found";

    //BICYCLE
    public static final String BICYCLE_NOT_FOUND = "bicycle.id.not-found";
    public static final String BICYCLE_AUTONOMY_NULL = "bicycle.autonomy.null";
    public static final String BICYCLE_BATTERY_CAPACITY_NULL ="bicycle.battery-capacity.null" ;
}
