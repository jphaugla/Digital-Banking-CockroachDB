package com.jphaugla.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import com.jphaugla.exception.InvalidUUIDException;
import com.jphaugla.exception.InvalidValueException;

/**
 * Utility class to handle common operations
 */

public final class Common {

    private Common() {}


    // returns the time between two timestamps, in decimal minutes
    public static double calculateDurationMinutes(Timestamp startTime, Timestamp endTime) {
        return calculateDuration(startTime, endTime).toMillis()/60000d;
    }

    private static Duration calculateDuration(Timestamp startTime, Timestamp endTime) {
        LocalDateTime startDateTime = startTime.toLocalDateTime();
        LocalDateTime endDateTime = endTime.toLocalDateTime();
        return Duration.between(startDateTime, endDateTime);
    }



    // converts String to UUID and throws exception if string is not a valid UUID
    public static UUID toUUID(String id, String errMsg) throws InvalidUUIDException {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException(String.format(errMsg, id, e.getMessage()));
        }
        return uuid;
    }



}
