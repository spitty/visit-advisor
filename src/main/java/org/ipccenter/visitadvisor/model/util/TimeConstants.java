package org.ipccenter.visitadvisor.model.util;

/**
 * This interface contains time constants mostly obtained from {@link java.time.LocalTime}
 * where they are package local
 */
public interface TimeConstants {
    
    /**
     * Hours per day.
     */
    int HOURS_PER_DAY = 24;
    /**
     * Minutes per hour.
     */
    int MINUTES_PER_HOUR = 60;
    /**
     * Minutes per day.
     */
    int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
    /**
     * Seconds per minute.
     */
    int SECONDS_PER_MINUTE = 60;
    /**
     * Seconds per hour.
     */
    int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    /**
     * Seconds per day.
     */
    int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
    /**
     * Milliseconds per day.
     */
    long MILLIS_PER_DAY = SECONDS_PER_DAY * 1000L;
    /**
     * Milliseconds per hour.
     */
    long MILLIS_PER_HOUR = SECONDS_PER_HOUR * 1000L;
    

}
