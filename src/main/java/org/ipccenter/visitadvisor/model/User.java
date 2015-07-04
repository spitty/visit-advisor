package org.ipccenter.visitadvisor.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author stad
 */
@Entity
@Table(name = "user_table")
@NamedQuery(name = "User.getAll", query = "SELECT e from User e")
public class User {
    @Id
    private Long id;
    String name;
    List<TimeInterval> availableTimeIntervals;
    Collection<Event> desiredEvents;

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableTimeIntervals(List<TimeInterval> availableTimeIntervals) {
        this.availableTimeIntervals = availableTimeIntervals;
    }

    public void setDesiredEvents(Collection<Event> desiredEvents) {
        this.desiredEvents = desiredEvents;
    }

    public String getName() {
        return name;
    }

    public List<TimeInterval> getAvailableTimeIntervals() {
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
