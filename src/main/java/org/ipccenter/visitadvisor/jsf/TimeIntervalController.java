package org.ipccenter.visitadvisor.jsf;

import org.ipccenter.visitadvisor.model.TimeInterval;
import org.ipccenter.visitadvisor.bean.TimeIntervalFacade;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("timeIntervalController")
@SessionScoped
public class TimeIntervalController extends AbstractEntityController<TimeInterval> implements Serializable {

    @EJB
    private TimeIntervalFacade ejbFacade;

    public TimeIntervalController() {
    }

    @Override
    protected TimeIntervalFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public TimeInterval prepareCreate() {
        TimeInterval selected = new TimeInterval();
        setSelected(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public TimeInterval getEvent(java.lang.Long id) {
        return getItemById(id);
    }

}
