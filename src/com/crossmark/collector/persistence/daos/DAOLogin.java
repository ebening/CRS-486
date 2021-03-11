package com.crossmark.collector.persistence.daos;


import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by christian on 13/01/2015.
 */
public interface DAOLogin extends Serializable {

    public String crearSession (UsuarioSession o);

    public boolean validarSession(String oid);

    public void cerrarSession(String o);

    public void cerrarSessionesLocales();

    public void cambiarEquipo(UsuarioSession o);

    public void closeAllSessions();

    public UsuarioSession getCurrentSession(String logOid);

}
