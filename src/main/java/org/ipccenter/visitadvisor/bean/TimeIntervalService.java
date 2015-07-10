package org.ipccenter.visitadvisor.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import javax.transaction.UserTransaction;
import org.ipccenter.visitadvisor.model.TimeInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ApplicationScoped
public class TimeIntervalService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TimeIntervalService.class);

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
    public List<TimeInterval> createTimeIntervals(int size) {
        LOG.debug("createTimeIntervals({}) called", size);
        if (size < 1) {
            return Collections.EMPTY_LIST;
        }
        try {
            EntityManager em = emf.createEntityManager();
            // begin transaction
            utx.begin();
            // join to started transaction
            em.joinTransaction();
            List<TimeInterval> timeIntervals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TimeInterval timeInterval = em.find(TimeInterval.class, (long) i + 1);
                if (timeInterval == null) {
                    timeInterval = new TimeInterval();
                    timeInterval.setId(Long.valueOf(i + 1));
                    timeInterval.setStartTime(Timestamp.valueOf(LocalDateTime.of(2001, 9, 11, 8, 46)));
                    timeInterval.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
                    timeInterval.setDuration(i * 1337);
                    timeInterval.setRepeatInterval(i * 228228);
                    em.persist(timeInterval);
                }
                timeIntervals.add(timeInterval);
            }
            em.flush();
            utx.commit();
            LOG.debug("Returned: {}", timeIntervals);
            return timeIntervals;
        } catch (SecurityException | RollbackException | HeuristicMixedException
                | HeuristicRollbackException | SystemException
                | IllegalStateException | NotSupportedException
                ex) {
            LOG.error("Error occurred on creating test TimeIntervals", ex);
            try {
                utx.rollback();
            } catch (SystemException | IllegalStateException | SecurityException exeption) {
                LOG.error("Can't rollback", exeption);
            }
            return null;
        }
    }
}
