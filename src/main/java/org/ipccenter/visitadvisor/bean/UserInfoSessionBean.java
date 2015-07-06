/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ipccenter.visitadvisor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import org.eclipse.persistence.internal.core.helper.CoreClassConstants;
import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.model.User;

/**
 *
 * @author Oleg
 */
@ManagedBean
@SessionScoped
public class UserInfoSessionBean implements Serializable{

    private User user;
    
    public UserInfoSessionBean() {
        User user = new User();
        Collection<Event> events = new ArrayList<Event>();
        for (int i = 0; i < 10; i++){
            Event event = new Event();
            event.setName("event " + i);
            event.setTime(new Date());
            events.add(event);
        }
        user.setDesiredEvents(events);
        user.setName("User!");
        this.user = user;
        System.out.println("User created");
    }
    
    public User getUser(){
        System.out.println("User getted" + user.getName());
        return user;
        //return new User();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
