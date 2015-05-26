package org.ipccenter.visitadvisor.bean;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.model.User;

/**
 *
 * @author spitty
 */
@ManagedBean(name="eventService")
@ApplicationScoped
public class EventService {
    
    @Resource
    UserTransaction utx;
    
    @PersistenceUnit 
    EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void createUser(){
        try {
            System.out.println("createUser");
            EntityManager em = entityManagerFactory.createEntityManager();
            utx.begin();
            em.joinTransaction();
            User u = new User();
            u.setId(1L);
            u.setName("User#1");
            em.persist(u);
            em.flush();
//            em.find(User.class, (long) 1);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(EventService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Generate sample list of events
     * @param size specifies size of result {@link List}
     * @return {@link List} of {@link Event}
     */
    public List<Event> createEvents(int size) {
        if (size < 1) {
            return Collections.EMPTY_LIST;
        }
        
        List<Event> events = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Event event = new Event(String.format("Event %3d", i), LocalDateTime.now().plusDays(i));
            event.setId(Long.valueOf(i));
            events.add(event);
        }
        return events;
    }

}
