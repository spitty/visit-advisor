package org.ipccenter.visitadvisor.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ipccenter.visitadvisor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UserFacade extends AbstractFacade<User> {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserFacade.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public User findByName(String name) {
        LOG.debug("Facade.findByName('{}')", name);
        List<User> resultList = em.createNamedQuery("User.getByName", User.class).setParameter("name", name).getResultList();
        if (resultList.isEmpty()) {
            LOG.warn("User hasn't been found by name '{}'", name);
            return null;
        }
        return resultList.get(0);
    }
}
