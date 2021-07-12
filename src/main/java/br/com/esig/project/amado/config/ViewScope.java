package br.com.esig.project.amado.config;

import lombok.val;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Map;


public class ViewScope implements Scope {

    private static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

    
    @Override
    public synchronized Object get( String name,  ObjectFactory<?> objectFactory) {
        var instance = getViewMap().get(name);
        if (instance == null) {
            instance = objectFactory.getObject();
            getViewMap().put(name, instance);
        }
        return instance;
    }

    @Override
    public Object remove( String name) {
        var instance = getViewMap().remove(name);
        if (instance == null) {
            val callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
            if (callbacks != null)
                callbacks.remove(name);
        }
        return instance;
    }

    @Override
    public void registerDestructionCallback( String name,  Runnable runnable) {
        val callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
        if (callbacks != null)
            callbacks.put(name, runnable);
    }

    @Override
    public Object resolveContextualObject( String key) {
        val facesContext = FacesContext.getCurrentInstance();
        val facesResquestAttributes = new FacesRequestAttributes(facesContext);
        return facesResquestAttributes.resolveReference(key);
    }

    @Override
    public String getConversationId() {
        val facesContext = FacesContext.getCurrentInstance();
        val facesResquestAttributes = new FacesRequestAttributes(facesContext);
        return facesResquestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
    }

    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    }

}