package org.ipccenter.visitadvisor.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 * @author spitty
 */
public class Event {
    
    private String name;
    private LocalDateTime time;

    public Event(String name, LocalDateTime time) {
        this.name = name;
        this.time = time;
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
