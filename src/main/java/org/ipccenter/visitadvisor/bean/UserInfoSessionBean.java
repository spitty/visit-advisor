package org.ipccenter.visitadvisor.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
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
            LOG.warn("Instance of UserFacade had not beeb injected");
            return null;
        }

        // TODO we should replace it with real user getting by userId
        List<User> allUsers = userFacade.findAll();
        if (allUsers.isEmpty()) {
            LOG.warn("No users found");
            return null;
        }
        Collections.sort(allUsers, (User o1, User o2) -> Long.compare(o1.getId(), o2.getId()));

        return allUsers.get(0);
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
