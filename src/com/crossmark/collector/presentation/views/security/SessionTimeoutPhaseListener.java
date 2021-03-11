package com.crossmark.collector.presentation.views.security;

import com.crossmark.collector.business.services.ServiceLogin;
import com.crossmark.collector.business.services.ServiceLoginImpl;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.crossmark.collector.presentation.views.utils.Utileria.logger;

/**
 * Created by christian on 15/01/2015.
 */
public class SessionTimeoutPhaseListener implements PhaseListener {

    public void beforePhase(PhaseEvent event) {
        // Session detection code goes

        FacesContext fc = event.getFacesContext();
        ExternalContext ex = fc.getExternalContext();
        HttpSession session = (HttpSession)ex.getSession(true);
        Map<String, Object> sessionMap = ex.getSessionMap();

        if (!(sessionMap.get("userLoged") == null)) {
            UsuarioSession userLoged = (UsuarioSession) sessionMap.get("userLoged");
            ServiceLogin serviceLogin = (ServiceLoginImpl)sessionMap.get("serviceLogin");
            session.setAttribute("userLoged",userLoged);
        }

    }


    public void afterPhase(PhaseEvent event) {
        // Do nothing
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}