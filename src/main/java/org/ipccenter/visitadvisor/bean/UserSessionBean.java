package org.ipccenter.visitadvisor.bean;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.slf4j.LoggerFactory;

@Named(value = "userSessionBean")
@ManagedBean
@SessionScoped
public class UserSessionBean implements Serializable {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserSessionBean.class);

    private String name;
    
    public UserSessionBean() {
    }

    public String getName() {
        if (name == null) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            name = ec.getRemoteUser();
        }
        return name;
    }
    
    public String getGreeting() {
        String localName = getName();
        return localName == null? "" : ", " + localName;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLogoutLabel() {
        return "Logout";
    }
    
    public String logout() throws IOException {
        LOG.info("Logout called");

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().clear();
        ec.invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
    
}
