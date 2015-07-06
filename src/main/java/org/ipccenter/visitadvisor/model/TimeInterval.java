package org.ipccenter.visitadvisor.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "TimeInterval.getAll", query = "SELECT e from TimeInterval e")
public class TimeInterval {
    @Id
    private Long id;
    Timestamp startTime;
    Timestamp endTime;
    long duration;
    long repeatInterval;

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public void setStartTime(Timestamp startAt) {
        this.startTime = startAt;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
