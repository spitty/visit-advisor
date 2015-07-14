package org.ipccenter.visitadvisor.jsf;

import org.ipccenter.visitadvisor.model.User;
import org.ipccenter.visitadvisor.bean.UserFacade;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("userController")
@SessionScoped
public class UserController extends AbstractEntityController<User> implements Serializable {

    @EJB
    private UserFacade ejbFacade;

    public UserController() {
    }

    @Override
    protected UserFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public User prepareCreate() {
        User selected = new User();
        setSelected(selected);
        initializeEmbeddableKey();
        return selected;
    }

    public User getEvent(java.lang.Long id) {
        return getItemById(id);
    }

}
