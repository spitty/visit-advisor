package org.ipccenter.visitadvisor.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJBException;
import org.ipccenter.visitadvisor.bean.AbstractFacade;
import org.ipccenter.visitadvisor.jsf.util.JsfUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEntityController<T> implements Serializable {

    private static final long serialVersionUID = -5973181053828687492L;
    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    private List<T> items = null;
    private T selected;

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    protected abstract AbstractFacade<T> getFacade();

    public abstract T prepareCreate();

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EventCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void updateFromDB() {
        LOG.debug("updateFromDB called");
        items = null;
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EventUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EventDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<T> getItems() {
        if (items == null) {
            LOG.debug("Update items from Facade");
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex
                            , ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                LOG.error(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"), ex);
            }
        }
    }

    public T getItemById(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<T> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<T> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

}
