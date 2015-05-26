package org.ipccenter.visitadvisor.bean;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;
import org.ipccenter.visitadvisor.model.User;

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
    private ConnectionPoolDataSource ds;
    private PooledConnection conn;

    public Event add(Event event) throws SQLException{
        log.debug("add called");
        try {
            conn = ds.getPooledConnection();
            
        } catch (Exception e) {
            log.error("Can't add event to database");
            return null;
        } finally {
            conn.close();
        }
        return null;
    }
    

    public void delete(long id){
        log.debug("delete called");
    }

    public Event get(long id){
        log.debug("get called");
        return null;
    }

    public void update(Event event){
        log.debug("update called");
    }

    public List<Event> getAll() {
        log.debug("getAll called");
        return null;
    }
    
    public void createUser() {
        EntityManager em = emf.createEntityManager();
        em.find(User.class, 1);
    }
}
