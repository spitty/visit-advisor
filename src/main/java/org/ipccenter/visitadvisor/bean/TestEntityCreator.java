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
        LOG.debug("Event objects created");
        List<TimeInterval> tis = createTimeIntervals(timeIntervalsSize);
        LOG.debug("TimeInterval objects created");
        List<User> users = createUsers(usersSize);
        LOG.debug("User objects created");
        Random r = new Random(seed);
        
        users.stream().map((user) -> {
            events.stream().filter((event) -> (r.nextBoolean())).map((event) -> {
                user.addDesiredEvent(event);
                return event;
            }).forEach((event) -> {
                event.addUser(user);
            });
            return user;
        }).forEach((user) -> {
            tis.stream().filter((ti) -> (r.nextBoolean())).forEach((ti) -> {
                user.addAvailableTimeInterval(ti);
            });
        });
        
        events.stream().forEach((event) -> {
            tis.stream().filter((ti) -> (r.nextBoolean())).forEach((ti) -> {
                event.addTimeInterval(ti);
            });
        });
        LOG.debug("Relations set");
        
        ArrayList all = new ArrayList();
        tis.forEach(ti -> all.add(ti));
        events.forEach(e -> all.add(e));
        users.forEach(u -> all.add(u));
        LOG.debug("Lists merged");
        
        try {
            persistEntities(all);
        } catch (Exception ex) {
            LOG.debug("Something went wrong during persisting entities");
        }
    }
    
    private void persistEntities(List entities) throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        LOG.debug("persistEntities(...) called");
        EntityManager em = emf.createEntityManager();
        LOG.debug("Entity manager created");
        // begin transaction
        utx.begin();
        LOG.debug("Transaction started");
        // join to started transaction
        em.joinTransaction();
        LOG.debug("Joined transaction");
        for (int i = 0; i < entities.size(); i++) {
            em.persist(entities.get(i));
            LOG.debug("Entity #" + i + " persisted");
        }
        em.flush();
        LOG.debug("Entity manager flushed");
        utx.commit();
        LOG.debug("Transaction committed");
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
