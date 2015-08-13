package org.ipccenter.visitadvisor.bean;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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

    private static final Logger LOG = LoggerFactory.getLogger(EventService.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;

    /**
     * Generate sample list of events.
     * @param size specifies size of result {@link List}
     * @return {@link List} of {@link Event}
     */
    public List<Event> createEvents(int size) {
        LOG.debug("createEvents({}) called", size);
        if (size < 1) {
            return Collections.EMPTY_LIST;
        }
        try {
            EntityManager em = emf.createEntityManager();
            // begin transaction
            utx.begin();
            // join to started transaction
            em.joinTransaction();
            List<Event> events = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Event event = em.find(Event.class, (long) i + 1);
                if (event == null) {
                    event = new Event();
                    event.setName(String.format("Event %3d", i));
                    event.setTime(new Timestamp(ZonedDateTime.now().plusDays(i).toInstant().toEpochMilli()));
                    event.setId(Long.valueOf(i + 1));
                    em.persist(event);
                }
                events.add(event);
            }
            em.flush();
            utx.commit();
            LOG.debug("Returned: {}", events);
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
