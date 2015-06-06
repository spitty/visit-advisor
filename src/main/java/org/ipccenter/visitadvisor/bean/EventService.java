package org.ipccenter.visitadvisor.bean;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;

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

    @Resource
    UserTransaction utx;
    
    public Event add(String name, Date time) {
        log.debug("add called");
        Event event = new Event(name, time);
        EntityManager em = emf.createEntityManager();
        try {
            log.debug("Trying to add: " + event.getName());
            utx.begin();
            em.joinTransaction();
            em.persist(event);
            utx.commit();
            log.debug("Added: " + event.getName());
            return event;
        }
        catch (Exception ex) {
            log.error("Can't commit add transaction");
            return null;
        } finally {
            em.close();
        }
    }
    

    public void delete(long id){
        log.debug("delete called");
        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();
            em.joinTransaction();
            Query q = em.createNamedQuery("Event.deletById");
            q.setParameter("id", id);
            q.executeUpdate();
            utx.commit();
        } catch(Exception e) {
            log.error("Can't commit delete transaction");
        }
        finally {
            em.close();
        }       
    }

    public Event get(long id){
        log.debug("get called");
        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();
            em.joinTransaction();
            Query q = em.createNamedQuery("Event.getById");
            q.setParameter("id", id);
            Event retval = (Event)q.getSingleResult();
            utx.commit();
            return retval;
        } catch(Exception e) {
            log.error("Can't commit get transaction");
            return null;
        }
        finally {
            em.close();
        }  
    }

    public void update(Event event){
        log.debug("update called");
        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();
            em.joinTransaction();
            em.merge(event);
            utx.commit();
        } catch(Exception e) {
            log.error("Can't commit update transaction");
        }
        finally {
            em.close();
        }  
    }

    public List<Event> getAll() {
        log.debug("getAll called");
        EntityManager em = emf.createEntityManager();
        try {
            utx.begin();
            em.joinTransaction();
            Query q = em.createNamedQuery("Event.getAll");
            List<Event> retval = q.getResultList();
            utx.commit();
            return retval;
        } catch(Exception e) {
            log.error("Can't commit getAll transaction");
            return null;
        }
        finally {
            em.close();
        }
    }
}
