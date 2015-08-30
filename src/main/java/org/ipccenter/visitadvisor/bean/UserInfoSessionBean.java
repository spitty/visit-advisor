package org.ipccenter.visitadvisor.bean;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.ipccenter.visitadvisor.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Oleg
 */
@ManagedBean
@SessionScoped
public class UserInfoSessionBean implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(UserInfoSessionBean.class);

    @Inject
    private UserFacade userFacade;

    public User getUser() {
        if (userFacade == null) {
            LOG.warn("Instance of UserFacade had not been injected");
            return null;
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String name = ec.getRemoteUser();
        User user = userFacade.findByName(name);
        return user;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
