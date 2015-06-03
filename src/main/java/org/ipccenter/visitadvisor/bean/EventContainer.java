package org.ipccenter.visitadvisor.bean;

import java.util.Date;
import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 * Created by stad on 11.05.15.
 */
@ManagedBean
@SessionScoped
public class EventContainer {
    private static final Logger log = Logger.getLogger(EventContainer.class);

    @ManagedProperty("#{eventService}")
    EventService service;

    private String newEventName;
    private Date newEventTime;
    
    public EventContainer() {
        log.debug("Constructor called");
    }

    public List<Event> getEventsList() {
        log.debug("getEventList() called");
        return service.getAll();
    }

    @Override
    protected void finalize() throws Throwable {
        log.debug("Finalized");
        super.finalize();
    }

    public void setService(EventService service) {
        this.service = service;
        log.debug("Service is set");
    }
    
    public void deleteEvent(ActionEvent event) {
        log.debug("deleteEvent called");
        UIParameter component = (UIParameter)event.getComponent().findComponent("deleteEvent");
        Long eventId = (Long)component.getValue();
        service.delete(eventId);
    }
    
    public void addEvent(ActionEvent event) {
        log.debug("addEvent called");
        service.add(newEventName, newEventTime);
    }

    public String getNewEventName() {
        return newEventName;
    }

    public void setNewEventName(String newEventName) {
        this.newEventName = newEventName;
    }

    public Date getNewEventTime() {
        return newEventTime;
    }

    public void setNewEventTime(Date newEventTime) {
        this.newEventTime = newEventTime;
    }
}
