/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.application;

import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Usuario;
import com.crossmark.security.ad.ADAuthenticator;
import com.crossmark.security.ad.UsuarioAD;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author christian
 */
public class Login implements Serializable {

    private ADAuthenticator adAuthenticator;
    private List<UsuarioAD> listaAD;
    private UsuarioAD usuarioAD;
    private Usuario usuarioLogin;

    public Login() {
        usuarioLogin = new Usuario();
       // adAuthenticator = new ADAuthenticator();
      //  listaAD = adAuthenticator.getUsers();
    }

    public List<UsuarioAD> getListaAD() {
        return listaAD;
    }

    public String login() {
        
        Utileria.logger(getClass()).info("getUserName:"+usuarioLogin.getUserName()+"   -    getPassword:"+usuarioLogin.getPassword());
        if (!validarConexionLDAP()) {
            return "failure";
        } else if (validateAD()) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String logout() {
        Utileria.setSessionAttribute("userLoged", null);
        return "login";
    }

    private boolean validateAD() {
        usuarioAD = adAuthenticator.getUserObj(usuarioLogin.getUserName(), usuarioLogin.getPassword());

        Usuario usSistema = new Usuario();
        if (usuarioAD != null) {
            
            usSistema.setUserName(usuarioAD.getNick());
            usSistema.setPassword(usuarioAD.getPassword());
            Utileria.setSessionAttribute("userLoged", usSistema);
            Utileria.logger(getClass()).info("Logeado :D (aun es prueba, no es el login definitivo)");
            return true;
        } else {
            Utileria.logger(getClass()).info("No Logeado >:D (aun es prueba, no es el login definitivo)");
            return false;
        }
//		if (usuarioAD != null) {
//			Usuarios usrTmp = this.serviceUsuarios.validate(login, password);
//			if (usrTmp == null) {
//				usSistema = new Usuarios();
//				usSistema.setLogin(login);
//				usSistema.setNombres(userAD.getNombre());
//				usSistema.setApellidos(userAD.getBmr());
//				usSistema.setDistribucion(Constant.ZERO);
//				usSistema.setNoNomina(123456);// TODO definir campo nomina
//				usSistema.setRegion("MX");// TODO definir region
//				usSistema.setPassUsuario("123456");// default
//
//				Perfil perfil = serviceUsuarios
//						.getPerfilById(Constant.PERFIL_SOLICITANTE);
//				usSistema.setPerfil(perfil);
//
//				usSistema = serviceUsuarios.saveUsuario(usSistema);
//			} else {
//				usSistema = usrTmp;
//			}
//		}

    }

    private boolean validarConexionLDAP() {
        return new ADAuthenticator().validarConexionLDAP();
    }

    public Usuario getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Usuario usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }


}
