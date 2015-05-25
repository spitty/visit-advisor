package org.ipccenter.visitadvisor.bean;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.TypedQuery;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.apache.log4j.Logger;
import org.ipccenter.visitadvisor.model.Event;

/**
 *
 * @author spitty
 */
@ManagedBean
@ApplicationScoped
public class EventService {
    private static final Logger log = Logger.getLogger(EventContainer.class);
    
    @Resource(name="jdbc/visit-advisor-data")
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
        em.getTransaction().begin();
        Event eventFromDB = em.merge(event);
        em.getTransaction().commit();
        return eventFromDB;
    }

    public void delete(long id){
        log.debug("delete called");
        em.getTransaction().begin();
        em.remove(get(id));
        em.getTransaction().commit();
    }

    public Event get(long id){
        log.debug("get called");
        return em.find(Event.class, id);
    }

    public void update(Event event){
        log.debug("update called");
        em.getTransaction().begin();
        em.merge(event);
        em.getTransaction().commit();
    }

    public List<Event> getAll() {
        log.debug("getAll called");
        TypedQuery<Event> namedQuery = em.createNamedQuery("Event.getAll", Event.class);
        return namedQuery.getResultList();
        /*ResultSet resultSet;
        try {
            resultSet = getEventsStatement.executeQuery();
            log.debug("Events received from database");
        } catch (SQLException e) {
            log.error("Can't get events from database");
            e.printStackTrace();
            return null;
        }
        List<Event> eventList = new ArrayList<Event>();
        try {
            while(resultSet.next()) {
                eventList.add(new Event(resultSet.getString(2), resultSet.getString(3)));
                log.debug("Event from result added");
            }
        } catch (SQLException e) {
            log.error("Can't process result");
            e.printStackTrace();
        }
        return eventList;*/
    }

    /*public EventService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            log.debug("Driver loaded");
        } catch (ClassNotFoundException e) {
            log.error("Can't load driver");
            e.printStackTrace();
        }
        try {
            this.connection = DriverManager.getConnection
                            ("jdbc:mysql://localhost:3306/mysql", "eventsmanager", "Staddy32");
            log.debug("Connected to database");
        } catch (SQLException e) {
            log.error("Can't connect to database");
            e.printStackTrace();
        }
        try {
            log.debug("Preparing getEventsStatement");
            getEventsStatement = connection.prepareStatement("SELECT * FROM events");
            log.debug("getEventsStatement prepared");
        } catch (SQLException e) {
            log.error("Can't prepare statement");
            e.printStackTrace();
        }
    }*/

    /*
     * Generate sample list of events
     * @param size specifies size of result {@link List}
     * @return {@link List} of {@link Event}
     */
    /*public List<Event> createEvents(int size) {
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
    }*/
}
