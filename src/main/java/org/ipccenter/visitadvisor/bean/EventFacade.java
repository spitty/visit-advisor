package org.ipccenter.visitadvisor.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ipccenter.visitadvisor.model.Event;

/**
 *
 * @author spitty
 */
@Stateless
public class EventFacade extends AbstractFacade<Event> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventFacade() {
        super(Event.class);
    }

}
