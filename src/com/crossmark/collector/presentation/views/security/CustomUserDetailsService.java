/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.security;

/**
 *
 * @author Francisco Mora
 */
import com.crossmark.collector.business.services.ServiceLogin;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.security.ad.LDAPAuthenticator;
import com.crossmark.security.ad.UsuarioAD;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.faces.bean.ManagedProperty;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public class CustomUserDetailsService implements UserDetailsService {

    @ManagedProperty(value = "#{serviceLogin}")
    private ServiceLogin serviceLogin;

    @ManagedProperty(value = "#{usuarioLogin}")
    private UsuarioSession usuarioLogin;

    @ManagedProperty(value = "#{ldapAuthenticator}")
    private LDAPAuthenticator ldapAuthenticator;
    private UsuarioAD usuarioAD;
    private List<UsuarioAD> listaAD;
    private boolean ldapActivo;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        String retorno = "failure";
        String mensaje = "Error de acceso.";
        String accesos[] = null;
        User security = null;
        Usuario user = new Usuario();
        if (usuario == null) {
            mensaje = "Acceso fallido";
        }

        try {

            if (usuarioLogin.getUserName() == null || usuarioLogin.getPassword() == null) {
                mensaje = "Ingrese un usuario y contraseña";
            }

            //Si LDAP se encuentra activo en la coniguración
            if (isLdapActivo()) {
                Utileria.logger(getClass()).info("validarConexionLDAP():" + validarConexionLDAP());
                //Si hay conexión con el servidor LDAP
                if (validarConexionLDAP()) {
                    //Si el usuario LDAP es correcto
                    if (validateAD()) {
                        //listaAD = ldapAuthenticator.getUsers();//Descomentar para produccion

                        //Se obtiene el usuario de base de datos para el usuario LDAP.
                        user = serviceLogin.getUsuarioSession(null, usuario, null, null, null, null,
                                null, null, null, null, null, null, null,
                                null, null, null, null, 1);

                        //Si el usuario LDAP existe en la base de datos
                        if (user == null) {
                            mensaje = "El usuario no cuenta con permisos para acceder a FlexRetail.";
                            retorno = "failure";
                            throw new UsernameNotFoundException("El usuario LDAP no cuenta con permisos para la aplicación...");
                        } else {
                            retorno = "success";
                        }
                    } else {
                        if (validaUsuarioBD(user)) {
                            // usuario correcto por base de datos
                            retorno = "success";
                        } else {
                            // validacion por base de datos fallida  
                            mensaje = "Acceso denegado, ingrese un usuario y contraseña correctos.";
                        }
                    }
                } else {
                    //sino hay conexion ldap: validar por base de datos
                    if (validaUsuarioBD(user)) {
                        // usuario correcto por base de datos
                        retorno = "success";
                    } else {
                        // validacion por base de datos fallida  
                        mensaje = "Acceso denegado, ingrese un usuario y contraseña correctos.";
                    }
                }
            } else if (validaUsuarioBD(user)) {
                // usuario correcto por base de datos
                retorno = "success";
            } else {
                // validacion por base de datos fallida  
                mensaje = "Acceso denegado, ingrese un usuario y contraseña correctos.";
            }

        } catch (AuthenticationException e) {
            Utileria.logger(getClass()).info("UsernameNotFoundException:" + mensaje);
        }
        
        //Si el usuario NO es nulo (puede ser nulo si existe un usuario LDAP que no tiene permisos en BD)
        if (user != null) {
            accesos = serviceLogin.getAccesos(user.getUsuariosOID());
            if (accesos == null || accesos.length == 0) {
                mensaje = "No tiene permisos en el sistema.";
                retorno = "failure";
            }
        }
        
        //Si hubo algun error con el usuario
        if (retorno.equals("failure")) {
            Utileria.logger(getClass()).info("UsernameNotFoundException:" + mensaje);
            Utileria.mensajeAlerta(mensaje);
            throw new UsernameNotFoundException("Ocurrió un error con el usuario especificado...");
        }
        
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(accesos);
        authorities = AuthorityUtils.createAuthorityList(accesos);
        return new User(usuarioLogin.getUserName(), usuarioLogin.getPassword(), authorities);
        /*
         //// Con acceso a base de datos //////////
         //Utileria.logger(getClass()).info("loadUserByUsername-getPerfil:"+serviceSeguridad.getUsuarioSession(usuario).getOID());
         Usuario user = new Usuario();
         String accesos[] = null;
         Utileria.logger(getClass()).info("CustomUserDetailsService:"+usuario);
         user = serviceSeguridad.getUsuarioSession(null,usuario, null, null, null, null,
         null,null,null,null,null,null,null, 
         null,null,null,null,null,null, 
         null,1);
        
        
         //user.setUserName("mourete");
         //user.setPassword("password");
         Utileria.logger(getClass()).info("CustomUserDetailsService:"+user.getPassword());
         if (user == null ) {
         Utileria.mensajeErroneo(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acceso fallido" ));
         throw new UsernameNotFoundException("usuario o password no validos");
         }
        
         accesos = serviceSeguridad.getAccesos(user.getUsuariosOID());
         if (accesos == null || accesos.length == 0) {
         Utileria.mensajeErroneo(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Acceso fallido" ));
         throw new UsernameNotFoundException("usuario o password no validos");
         }
         Utileria.logger(getClass()).info("CustomUserDetailsService getPassword:"+user.getPassword());
         //"3","4","5","6","7","8", "9", "10","11","13", "14", "18","20","23", "24","25","26","27", "30", "32","33","34","35"
         Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(accesos);
        
         return new User(user.getUserName(), user.getPassword(), authorities);
         */
    }

    private boolean validateAD() {
        Utileria.logger(getClass()).info("loadUserByUsername getUserName:" + usuarioLogin.getUserName() + "   -    loadUserByUsername getPassword:" + usuarioLogin.getPassword());
        this.setUsuarioAD(ldapAuthenticator.getUserObj(usuarioLogin.getUserName(), usuarioLogin.getPassword()));
        if (this.usuarioAD != null) {

            //usSistema.setUserName(usuarioAD.getNick());
            //usSistema.setPassword(usuarioAD.getPassword());
            //Utileria.setSessionAttribute("userLoged", usSistema);
            Utileria.logger(getClass()).info("Logeado :D (Solo en LDAP): " + usuarioAD.getNick() + "    usuarioAD.getNick():" + usuarioAD.getNick());
            return true;
        } else {
            Utileria.logger(getClass()).info("No Logeado >:D (aun es prueba, no es el login definitivo)");
            return false;
        }
    }

    private boolean validaUsuarioBD(Usuario user) {
        boolean valido;

        // si ldap esta desactivado valida por base de datos.
        user = serviceLogin.getUsuarioSession(null, usuarioLogin.getUserName(), null, null, null, null,
                null, null, null, null, null, null, null,
                null, null, null, null, 1);

        //Estas lineas, quitarlas cuando haya LDAp
        if (user != null) {
            valido = user.getUserName().equals(usuarioLogin.getUserName())
                    && user.getPassword().equals(usuarioLogin.getPassword());
        } else {
            valido = false;
        }

        return valido;
    }

    private boolean validarConexionLDAP() {
        return ldapAuthenticator.validarConexionLDAP();
        //return new ADAuthenticator().validarConexionLDAP();//ldapProperties
    }

    public UsuarioSession getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioSession usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public UsuarioAD getUsuarioAD() {
        return usuarioAD;
    }

    public void setUsuarioAD(UsuarioAD usuarioAD) {
        this.usuarioAD = usuarioAD;
    }

    public ServiceLogin getServiceLogin() {
        return serviceLogin;
    }

    public void setServiceLogin(ServiceLogin serviceLogin) {
        this.serviceLogin = serviceLogin;
    }

    public LDAPAuthenticator getLdapAuthenticator() {
        return ldapAuthenticator;
    }

    public void setLdapAuthenticator(LDAPAuthenticator ldapAuthenticator) {
        this.ldapAuthenticator = ldapAuthenticator;
    }

    public boolean isLdapActivo() {
        return ldapActivo;
    }

    public void setLdapActivo(boolean ldapActivo) {
        this.ldapActivo = ldapActivo;
    }

}
