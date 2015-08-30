package org.ipccenter.visitadvisor.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "SELECT e from User e"),
    @NamedQuery(name = "User.getByName", query = "SELECT e from User e where e.name = :name")
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(unique = true)
    private String name;
    @OneToMany
    private Collection<TimeInterval> availableTimeIntervals;
    @ManyToMany
    private Collection<Event> desiredEvents;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    private String password;

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
    
    public void addDesiredEvent(Event e) {
        desiredEvents.add(e);
    }
    
    public void addAvailableTimeInterval(TimeInterval ti) {
        availableTimeIntervals.add(ti);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", availableTimeIntervals=" + availableTimeIntervals + ", desiredEvents=" + desiredEvents + ", group=" + group + '}';
    }
}
