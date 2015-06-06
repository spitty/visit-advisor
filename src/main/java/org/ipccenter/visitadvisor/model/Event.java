package org.ipccenter.visitadvisor.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author spitty
 */
@Entity
public class Event {
    
    @Id
    private Long id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_stamp")
    private Date time;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", time=" + time + '}';
    }
    
}
