package org.ipccenter.visitadvisor.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ipccenter.visitadvisor.model.User;

@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
}
