package org.ipccenter.visitadvisor.bean;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
    private final long seed = 228;
    
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
        Random r = new Random(seed);
        
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
            persistEntities(events, Event.class);
        } catch (NotSupportedException ex) {
            Logger.getLogger(TestEntityCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(TestEntityCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(TestEntityCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(TestEntityCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(TestEntityCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            persistEntities(tis, TimeInterval.class);
            persistEntities(users, User.class);
        } catch (Exception ex) {
            LOG.error("Somthing's wrong");
        }
    }
    
    private <T> void persistEntities(List<T> entities, Class<T> c) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        LOG.debug("0");
        EntityManager em = emf.createEntityManager();
        LOG.debug("1");
        LOG.debug("2");
        // begin transaction
        utx.begin();
        // join to started transaction
        LOG.debug("3");
        em.joinTransaction();
        LOG.debug("4");
        for (int i = 0; i < entities.size(); i++) { // no lambda for compatibility
            T e = em.find(c, (long) i + 1);
            LOG.debug("5 " + i);
            if (e == null)
                em.persist(entities.get(i));
        }
        LOG.debug("4");
        //em.flush();
        LOG.debug("5");
        utx.commit();
    }
    
    private List<Event> createEvents(int size) {
        List<Event> events = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Event event = new Event();
            event.setTime(new ArrayList<>());
            event.setUsers(new ArrayList<>());
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
            user.setAvailableTimeIntervals(new ArrayList<>());
            user.setDesiredEvents(new ArrayList<>());
            user.setId(Long.valueOf(i + 1));
            user.setName("User #" + i);
            users.add(user);
        }
        return users;
    }
}
