package org.ipccenter.visitadvisor.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


/**
 *
 * @author spitty
 */
@Entity
@NamedQuery(name = "Event.getAll", query = "SELECT e from Event e")
public class Event implements Serializable {

    @Id
    private Long id;
    private String name;
    private Collection<TimeInterval> time;

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
        return "Event{" + "name=" + name + ", time=" + time + '}';
    }

}
