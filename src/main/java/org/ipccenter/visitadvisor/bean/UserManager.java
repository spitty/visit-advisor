/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ipccenter.visitadvisor.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
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

/**
 *
 * @author Oleg
 */
@ManagedBean
@ApplicationScoped
public class UserManager implements Serializable{

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction utx;
    
    public UserManager() {
    }
    
    @PostConstruct
    public void preLoad() {
        EntityManager em = emf.createEntityManager();
        
        try {
            utx.begin();
            
            em.joinTransaction();
            
            for (int i = 0; i < 10; i++){
                User user = new User();
                user.setName("User" + i);
                user.setId((long)i);
                Collection<Event> desiredEvents = new ArrayList<Event>();
                for (int j = 0; j < 10; j++){
                    Event event = new Event();
                    event.setId((long)(i*100 + j));
                    event.setName("Event" + (i*100 + j));
                    Date date = new Date();
                    date.setTime(date.getTime() + j * 24 * 60 * 60);
                    event.setTime(date);
                }
                user.setDesiredEvents(desiredEvents);
                
                Collection<TimeInterval> intervals = new ArrayList<TimeInterval>();
                for (int j = 0; j < 5; j++){
                    TimeInterval tm = new TimeInterval();
                    tm.setId((long)(i*100 + j));
                    Timestamp timestamp = new Timestamp(new Date().getTime() + j * 24 * 60 * 60);
                    tm.setStartTime(timestamp);
                    tm.setDuration(60 * 60);
                    intervals.add(tm);
                }
                user.setAvailableTimeIntervals(intervals);
                em.persist(user);
                em.flush();
                utx.commit();
            }    
        } catch (NotSupportedException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        User user = new User();
    }
    
}
