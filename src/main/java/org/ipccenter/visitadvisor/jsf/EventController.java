package org.ipccenter.visitadvisor.jsf;

import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.bean.EventFacade;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("eventController")
@SessionScoped
public class EventController extends AbstractEntityController<Event> implements Serializable {

    @EJB
    private EventFacade ejbFacade;

    public EventController() {
    }

    @Override
    protected EventFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public Event prepareCreate() {
        Event selected = new Event();
        setSelected(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public Event getEvent(java.lang.Long id) {
        return getItemById(id);
    }

}
