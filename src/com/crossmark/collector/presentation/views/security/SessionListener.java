package com.crossmark.collector.presentation.views.security;

import com.crossmark.collector.business.services.ServiceLogin;
import com.crossmark.collector.business.services.ServiceLoginImpl;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.Serializable;

import static com.crossmark.collector.presentation.views.utils.Utileria.logger;

/**
 * Created by christian on 15/01/2015.
 */

public class SessionListener implements HttpSessionListener, Serializable {

    private ServiceLogin serviceLogin;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger(getClass()).info("SESSION CREADA");
        HttpSession session = se.getSession();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        serviceLogin = (ServiceLoginImpl) ctx.getBean("serviceLogin");
        serviceLogin.cerrarSessionesLocales();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger(getClass()).info("SESSION DESTRUIDA");
        HttpSession session = se.getSession();

        if (se.getSession().getAttribute("userLoged") == null) {
            logger(getClass()).info("Current User: null");
        } else {
            UsuarioSession usuarioSession = (UsuarioSession) session.getAttribute("userLoged");
            serviceLogin.cerrarSession(usuarioSession.getUsuariosLogOID());
            SecurityContextHolder.clearContext();
            session.invalidate();
        }
    }


}