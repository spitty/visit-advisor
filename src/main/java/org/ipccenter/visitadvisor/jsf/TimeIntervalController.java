package org.ipccenter.visitadvisor.jsf;

import org.ipccenter.visitadvisor.model.TimeInterval;
import org.ipccenter.visitadvisor.jsf.util.JsfUtil;
import org.ipccenter.visitadvisor.jsf.util.JsfUtil.PersistAction;
import org.ipccenter.visitadvisor.bean.TimeIntervalFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("timeIntervalController")
@SessionScoped
public class TimeIntervalController implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(TimeIntervalController.class);

    @EJB
    private TimeIntervalFacade ejbFacade;
    private List<TimeInterval> items = null;
    private TimeInterval selected;

    public TimeIntervalController() {
    }

    public TimeInterval getSelected() {
        return selected;
    }

    public void setSelected(TimeInterval selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TimeIntervalFacade getFacade() {
        return ejbFacade;
    }

    public TimeInterval prepareCreate() {
        selected = new TimeInterval();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TimeIntervalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void updateFromDB() {
        LOG.debug("updateFromDB called");
        items = null;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TimeIntervalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TimeIntervalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TimeInterval> getItems() {
        if (items == null) {
            LOG.debug("Update items from Facade");
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
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

    public TimeInterval getEvent(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TimeInterval> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TimeInterval> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TimeInterval.class)
    public static class EventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TimeIntervalController controller = (TimeIntervalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "timeIntervalController");
            return controller.getEvent(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TimeInterval) {
                TimeInterval o = (TimeInterval) object;
                return getStringKey(o.getId());
            } else {
                LOG.error("object {} is of type {}; expected type: {}"
                        , object, object.getClass().getName(), TimeInterval.class.getName());
                return null;
            }
        }

    }

}
