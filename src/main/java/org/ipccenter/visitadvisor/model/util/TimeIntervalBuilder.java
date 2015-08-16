package org.ipccenter.visitadvisor.model.util;

import java.util.Date;
import org.ipccenter.visitadvisor.model.TimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class builds instances of {@link TimeInterval} with next defaults:
 * <ul>
 *   <li>{@code startTime} -- current time</li>
 *   <li>{@code endTime} -- {@code null}</li>
 *   <li>{@code duration} -- 1 hour</li>
 *   <li>{@code repeatInterval} -- 1 day</li>
 * </ul>
 */
public class TimeIntervalBuilder {

    public static final long DEFAULT_DURATION = 1 * TimeConstants.MILLIS_PER_HOUR;
    public static final long DEFAULT_REPEAT_INTERVAL = 1 * TimeConstants.MILLIS_PER_DAY;

    private static final Logger LOG = LoggerFactory.getLogger(TimeIntervalBuilder.class);

    private Date startTime;
    private Date endTime;
    private long duration;
    private long repeatInterval;

    public TimeIntervalBuilder() {
        duration = DEFAULT_DURATION;
        repeatInterval = DEFAULT_REPEAT_INTERVAL;
    }

    public TimeIntervalBuilder from(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public TimeIntervalBuilder to(Date endTime) {
        if (startTime == null && endTime != null) {
            LOG.warn("EndTime is set to {} but StartTime is undefined for TimeIntervalBuilder", endTime);
        }
        this.endTime = endTime;
        return this;
    }

    public TimeIntervalBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    public TimeIntervalBuilder repeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
        return this;
    }

    public TimeInterval build() {
        TimeInterval t = new TimeInterval();

        Date localStartTime = startTime == null ? new Date() : startTime;
        t.setStartTime(localStartTime);
        t.setEndTime(endTime);
        t.setDuration(duration);
        t.setRepeatInterval(repeatInterval);

        return t;
    }

}
