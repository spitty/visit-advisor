package org.ipccenter.visitadvisor.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@NamedQuery(name = "User.getAll", query = "SELECT e from User e")
public class User implements Serializable {
    @Id
    private Long id;
    String name;
    Collection<TimeInterval> availableTimeIntervals;
    Collection<Event> desiredEvents;

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableTimeIntervals(Collection<TimeInterval> availableTimeIntervals) {
        this.availableTimeIntervals = availableTimeIntervals;
    }

    public void setDesiredEvents(Collection<Event> desiredEvents) {
        this.desiredEvents = desiredEvents;
    }

    public String getName() {
        return name;
    }

    public Collection<TimeInterval> getAvailableTimeIntervals() {
        return availableTimeIntervals;
    }

    public Collection<Event> getDesiredEvents() {
        return desiredEvents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
