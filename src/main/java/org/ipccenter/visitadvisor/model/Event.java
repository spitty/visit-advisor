package org.ipccenter.visitadvisor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQuery(name = "Event.getAll", query = "SELECT e from Event e")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany
    private Collection<TimeInterval> timeIntervals;
    @ManyToMany
    private Collection<User> users;

    public Collection<TimeInterval> getTime() {
        return timeIntervals;
    }

    public void setTime(Collection<TimeInterval> time) {
        this.timeIntervals = time;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", time=" + timeIntervals + '}';
    }

    public void addTimeInterval(TimeInterval ti) {
        if (timeIntervals == null) {
            timeIntervals = new ArrayList<>();
        }
        timeIntervals.add(ti);
    }

    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
