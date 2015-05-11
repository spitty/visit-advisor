/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ipccenter.visitadvisor.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.ipccenter.visitadvisor.model.Event;

/**
 *
 * @author ochuikin
 */
@ManagedBean(name="managedBead")
@SessionScoped
public class ManagedBead implements Serializable{

    /**
     * Creates a new instance of ManagedBead
     */
    @ManagedProperty("#{eventService}")
    private EventService eventService;
    
    private List<Event> events;
    
    private Event selectedEvent;
    
    @PostConstruct
    public void init() {
        events = eventService.createEvents(10);
    }

    public EventService getEventService() {
        return eventService;
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
    
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}
