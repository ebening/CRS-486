package com.crossmark.collector.presentation.views.security;

import com.crossmark.collector.business.services.ServiceLogin;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Theme;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.crossmark.collector.presentation.views.utils.Utileria.*;


public class MBLogin implements Serializable {

    private UsuarioSession usuarioLogin;
    private AuthenticationManager authenticationManager;
    private ServiceLogin serviceLogin;
    private boolean retorno;
    private List<Equipo> equipos;
    private UsuarioSession usSistema;
    private boolean conVentana;
    private boolean ban;
    String mensaje = "";
    UsuarioSession nva;
    private Theme theme;
    
    public MBLogin() {
        equipos = new ArrayList<>();
        conVentana = false;
    }

    public UsuarioSession getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioSession usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public ServiceLogin getServiceLogin() {
        return serviceLogin;
    }

    public void setServiceLogin(ServiceLogin serviceLogin) {
        this.serviceLogin = serviceLogin;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public UsuarioSession getUsSistema() {
        return usSistema;
    }

    public void login() {
        Usuario usuario = new Usuario();
        usSistema = new UsuarioSession();
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(usuarioLogin.getUserName(), usuarioLogin.getPassword());
             Authentication result = authenticationManager.authenticate(request);
             SecurityContextHolder.getContext().setAuthentication(result);
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             if (auth.isAuthenticated()) {
                usuario = serviceLogin.getUsuarioSession(null, auth.getName(),
                        null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, 1);
           /* usuario = serviceLogin.getUsuarioSession(null, usuarioLogin.getUserName(),
                    null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, 1);*/
            usSistema.setOID(usuario.getUsuariosOID());
            usSistema.setUserName(usuario.getUserName());
            usSistema.setPerfilesId(usuario.getPerfilesId());
            usSistema.setFechaLogIn(new Date());
            usSistema.setIp(getIP());
            equipos = serviceLogin.equiposUsuarios(usSistema);

            logger(getClass()).info(equipos.size());
            int c = equipos.size();
            if (c < 1) {
                conVentana = false;
                ban = false;
                mensaje = "failure";
            }

            if (c == 1) {
                conVentana = false;
                usSistema.setEquipoId(equipos.get(0).getId());
                ban = true;
            }
            if (c > 1) {
                conVentana = true;
            }

            if (conVentana) {
                RequestContext.getCurrentInstance().execute("PF('verificacion').show();");
            }else{
                if(ban){
                    cl();
                 //mensaje = cl();
                }else{
                    mensajeErroneo("No se puede usar este usuario porque no tiene equipos");
                }
            }

        }} catch (Exception e) {
            logger(getClass()).error(e);
            mensajeErroneo(e.getMessage());
        }
       // return mensaje;
    }

    private boolean sessionValida() {
        return serviceLogin.validarSession(usSistema.getOID());
    }

    public String getUserName() {
        UsuarioSession usSistema = null;
        //Utileria.setSessionAttribute("userLoged", usSistema);
        usSistema = getSessionAttribute("userLoged");

        if (usSistema == null) {
            return "";
        }
        return usSistema.getUserName();
    }

    public void logout() {
        UsuarioSession usSistema = getSessionAttribute("userLoged");
        serviceLogin.cerrarSession(usSistema.getUsuariosLogOID());
        setSessionAttribute("userLoged", null);
        SecurityContextHolder.clearContext();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/pages/Login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

    }

    public void continueLogin() {
        try {
            cl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cl() throws IOException {

        if (sessionValida()) {
            String nvoLogOID = serviceLogin.crearSession(usSistema);
            nva = serviceLogin.getCurrentSession(nvoLogOID);
            if (nva != null) {
                System.out.println("eq: "+nva.getEquipoId());
                if(!((nva.getEquipoId() == null) || (nva.getEquipoId() == 0))){
                    serviceLogin.cambiarEquipo(nva);
                    nva.setUserName(usSistema.getUserName());
                    setSessionAttribute("userLoged", nva);
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    logger(getClass()).info(ec.getRequestContextPath());
                    ec.redirect(ec.getRequestContextPath()+"/pages/Principal.xhtml");
                    // mensaje = "success";
                }else{
                    mensajeErroneo("No se puede usar este usuario porque no tiene equipos");
                   // mensaje = "failure";
                }
            } else {
                mensajeErroneo("No se puede usar este usuario porque está en uso");
             //   mensaje = "failure";
            }
        } else {
            mensajeErroneo("No se puede usar este usuario porque esta en uso");
           // mensaje = "failure";
        }
       // return mensaje;
    }

/*
public String continueLogin() {
    setSessionAttribute("userLoged", usSistema);
    return "success";
}*/

    public void imprimir(UsuarioSession o) {
        logger(getClass()).info("Imprimir");
        logger(getClass()).info(o.getUsuariosLogOID());
        logger(getClass()).info(o.getOID());
        logger(getClass()).info(o.getUserName());
        logger(getClass()).info(o.getPassword());
        logger(getClass()).info(o.getIp());
        logger(getClass()).info(o.getEquipoId());
        logger(getClass()).info(o.getPerfilesId());
    }

    public void onIdle() {

        RequestContext.getCurrentInstance().execute("PF('sessionExpired').show();");
    }
    
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

}
