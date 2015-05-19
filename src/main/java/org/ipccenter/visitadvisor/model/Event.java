package org.ipccenter.visitadvisor.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 * @author spitty
 */
@Entity
@Table(name = "events")
@NamedQuery(name = "Event.getAll", query = "SELECT e from Event e")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "time")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime time;

    public Event() {
    }

    public Event(String name, LocalDateTime time) {
        this.name = name;
        this.time = time;
    }

    // TODO: String to LocalDateTime
    public Event(String name, String time) {
        this.name = name;
        this.time = null;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", time=" + time + '}';
    }
    
}
