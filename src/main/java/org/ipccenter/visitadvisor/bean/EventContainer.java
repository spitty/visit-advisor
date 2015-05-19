package org.ipccenter.visitadvisor.bean;

import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

/**
 * Created by stad on 11.05.15.
 */
@ManagedBean
@SessionScoped
public class EventContainer {
    private static final Logger log = Logger.getLogger(EventContainer.class);

    public EventContainer() {
        log.debug("Constructor called");
    }

    public List<Event> getEventsList() {
        log.debug("getEventList() called");
        return new EventService().getAll();
    }

    @Override
    protected void finalize() throws Throwable {
        log.debug("Finalized");
        super.finalize();
    }
}
