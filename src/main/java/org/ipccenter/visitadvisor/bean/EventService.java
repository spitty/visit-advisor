package org.ipccenter.visitadvisor.bean;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.model.User;

/**
 *
 * @author spitty
 */
@ManagedBean
@ApplicationScoped
public class EventService {
    private static final Logger log = Logger.getLogger(EventContainer.class);
    
    @PersistenceUnit(name="org.ipccenter_visit-advisor_war_1.0-SNAPSHOTPU")
    EntityManagerFactory emf;

    public Event add(Event event) {
        log.debug("add called");
        EntityManager em = emf.createEntityManager();
        try {
            em.persist(event);
            return event;   // bad idea
        }
        finally {
            em.close();
        }
    }
    

    public void delete(long id){
        log.debug("delete called");
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Event.deletById");
            q.setParameter("id", id);
        }
        finally {
            em.close();
        }       
    }

    public Event get(long id){
        log.debug("get called");
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Event.getById");
            q.setParameter("id", id);
            return (Event)q.getSingleResult();
        }
        finally {
            em.close();
        }  
    }

    public void update(Event event){
        log.debug("update called");
        EntityManager em = emf.createEntityManager();
        try {
            em.merge(event);
        }
        finally {
            em.close();
        }  
    }

    public List<Event> getAll() {
        log.debug("getAll called");
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Event.getAll");
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }
    
    public void createUser() {
        EntityManager em = emf.createEntityManager();
        em.find(User.class, 1);
    }
}
