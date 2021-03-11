package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOEquiposUsuarios;
import com.crossmark.collector.persistence.daos.DAOLogin;
import com.crossmark.collector.persistence.daos.DAOUsuarios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.crossmark.collector.presentation.views.utils.Utileria.*;
public class ServiceLoginImpl implements ServiceLogin, Serializable {

    private DAOUsuarios daoUsuarios; // = new DAOCatalogosImpl();
    private DAOEquiposUsuarios daoEquiposUsuarios;
    private DAOLogin daoLogin;

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAOEquiposUsuarios getDaoEquiposUsuarios() {
        return daoEquiposUsuarios;
    }

    public void setDaoEquiposUsuarios(DAOEquiposUsuarios daoEquiposUsuarios) {
        this.daoEquiposUsuarios = daoEquiposUsuarios;
    }

    public DAOLogin getDaoLogin() {
        return daoLogin;
    }

    public void setDaoLogin(DAOLogin daoLogin) {
        this.daoLogin = daoLogin;
    }

    @Override
    public Usuario getUsuarioSession(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
                                     String colonia, String cP, String nroEmpleado, Integer ciudadesId,
                                     Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
                                     Integer pertenece, Integer activo) {
        List<Usuario> userList = new ArrayList();
        Usuario user = null;

        userList = getDaoUsuarios().getUsuariosByParams(usuariosOID, userName, nombre, apellidoMaterno, apellidoPaterno, direccion,
                colonia, cP, nroEmpleado, ciudadesId, estadosId, territoriosId, territorioNativoId, perfilesId, unidadesNegociosId, equiposId,
                pertenece, activo);

        if (!userList.isEmpty() || userList.size() != 0) {
            user = new Usuario();
            user = userList.get(0);
        }
        return user;
    }

    @Override
    public void getMetodo() {
        logger(getClass()).info("prueba de service seguridad");
    }

    public String[] getAccesos(String usuariosOID) {
        //"3","4","5","6","7","8", "9", "10","11","13", "14", "18","20","23", "24","25","26","27", "30", "32","33","34","35"
        String[] accesos = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "13", "14", "18", "20", "23", "24", "25", "26", "27", "30", "32", "33", "34", "35"};
        //3,4,5,6,7,8,9,10,11,13,14,18,20,23,24,25,26,27,30,32,33,34,35
        //accesos[1]="4";

        Utileria.logger(getClass()).info("Logeado por los reloes");
        return accesos;
    }

    @Override
    public List<Equipo> equiposUsuarios(UsuarioSession o) {
        return daoEquiposUsuarios.traerListaEquipoUsuario(o);
    }

    @Override
    public String crearSession(UsuarioSession o) {
        return  daoLogin.crearSession(o);
    }

    @Override
    public void cerrarSession(String oid) {
        daoLogin.cerrarSession(oid);
    }

    @Override
    public void cerrarSessionesLocales() {
        daoLogin.cerrarSessionesLocales();
    }
    @Override
    public void closeAllSessions(){
        daoLogin.closeAllSessions();
    }


    @Override
    public void cambiarEquipo(UsuarioSession o) {
        daoLogin.cambiarEquipo(o);
    }

    @Override
    public boolean validarSession(String oid) {
       return daoLogin.validarSession(oid);
    }

    @Override
    public UsuarioSession getCurrentSession(String logOid) {
        return daoLogin.getCurrentSession(logOid);
    }
}
