package org.ipccenter.visitadvisor.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ipccenter.visitadvisor.model.TimeInterval;

@Stateless
public class TimeIntervalFacade extends AbstractFacade<TimeInterval> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TimeIntervalFacade() {
        super(TimeInterval.class);
    }
}
