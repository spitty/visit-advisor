package org.ipccenter.visitadvisor.bean;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Startup;
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
import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.model.TimeInterval;
import org.ipccenter.visitadvisor.model.User;
import org.slf4j.LoggerFactory;

@ManagedBean(eager=true)
@ApplicationScoped
public class TestEntityCreator {
    private final int eventsSize = 10;
    private final int timeIntervalsSize = 10;
    private final int usersSize = 10;
    
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TestEntityCreator.class);
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction utx;
    
    @PostConstruct
    public void createTestEntities() {
        LOG.debug("createTestEntities() called");
        List<Event> events = createEvents(eventsSize);
        List<TimeInterval> tis = createTimeIntervals(timeIntervalsSize);
        List<User> users = createUsers(usersSize);
        Random r = new Random(LocalDateTime.now().getNano());
        
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < events.size(); j++)
                if (r.nextBoolean()) {
                    users.get(i).addDesiredEvent(events.get(j));
                    events.get(j).addUser(users.get(i));
                }
            for (int j = 0; j < tis.size(); j++)
                if (r.nextBoolean()) {
                    users.get(i).addAvailableTimeInterval(tis.get(j));
                }
        }
        
        for (int i = 0; i < events.size(); i++) {
            for (int j = 0; j < tis.size(); j++)
                if (r.nextBoolean()) {
                    events.get(i).addTime(tis.get(j));
                }
        }
        
        try {
            persistEntities(events);
            persistEntities(tis);
            persistEntities(users);
        } catch (Exception ex) {
            
        }
    }
    
    private <T> void persistEntities(List<T> entities) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        EntityManager em = emf.createEntityManager();

        final Class<T> c =                                  // FIXMEPLEASE
                (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        
        // begin transaction
        utx.begin();
        // join to started transaction
        em.joinTransaction();
        for (int i = 0; i < entities.size(); i++) { // no lambda for compatibility
            T e = em.find(c, (long) i + 1);
            if (e == null)
                em.persist(e);
        }
        em.flush();
        utx.commit();
    }
    
    private List<Event> createEvents(int size) {
        List<Event> events = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Event event = new Event();
            event.setName(String.format("Event %3d", i));
            event.setId(Long.valueOf(i + 1));
            events.add(event);
        }
        return events;
    }
    
    private List<TimeInterval> createTimeIntervals(int size) {
        List<TimeInterval> timeIntervals = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TimeInterval ti = new TimeInterval();
            ti.setId(Long.valueOf(i + 1));
            ti.setStartTime(Timestamp.valueOf(LocalDateTime.of(2001, 9, 11, 8, 46)));
            ti.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
            ti.setDuration(i * 1337);
            ti.setRepeatInterval(i * 228228);
            timeIntervals.add(ti);
        }
        return timeIntervals;
    }
    
    private List<User> createUsers(int size) {
        List<User> users = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setId(Long.valueOf(i + 1));
            user.setName("User #" + i);
            users.add(user);
        }
        return users;
    }
}
