package org.ipccenter.visitadvisor.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import org.ipccenter.visitadvisor.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author spitty
 */
@ManagedBean
@ApplicationScoped
public class EventService {
    
    Logger LOG = LoggerFactory.getLogger(EventService.class);
    
    @PersistenceUnit
    EntityManagerFactory emf;
    
    @Resource
    UserTransaction utx;
    
    /**
     * Generate sample list of events
     * @param size specifies size of result {@link List}
     * @return {@link List} of {@link Event}
     */
    @Transactional
    public List<Event> createEvents(int size) {
        LOG.info("createEvents({}) called", size);
        if (size < 1) {
            return Collections.EMPTY_LIST;
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            utx.begin();
            em.joinTransaction();
            List<Event> events = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Event event = new Event();
                event.setName(String.format("Event %3d", i));
                event.setTime(new Timestamp(LocalDateTime.now().plusDays(i).toInstant(ZoneOffset.UTC).toEpochMilli()));
                event.setId(Long.valueOf(i+1));
                events.add(event);
                em.persist(event);
            }
            em.flush();
            utx.commit();
            LOG.info("Returned: {}", events);
            return events;
        } catch (SecurityException | RollbackException | HeuristicMixedException 
                | HeuristicRollbackException | SystemException 
                | IllegalStateException | NotSupportedException
                ex) {
            LOG.error("Error occurred on creating test Events", ex);
            try {
                utx.rollback();
            } catch (SystemException | IllegalStateException | SecurityException exeption) {
                LOG.error("Can't rollback", exeption);
            }
            return null;
        }
    }

}
