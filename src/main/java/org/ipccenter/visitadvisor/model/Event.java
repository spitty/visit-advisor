package org.ipccenter.visitadvisor.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *
 * @author spitty
 */
@Entity
public class Event {
    
    @Id
    private Long id;
    private String name;
    private Timestamp time;

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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", time=" + time + '}';
    }
    
}
