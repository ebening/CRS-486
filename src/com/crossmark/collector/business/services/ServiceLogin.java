package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;

import java.util.ArrayList;
import java.util.List;

public interface ServiceLogin {
    public Usuario getUsuarioSession(String usuariosOID, String userName, String nombre, String apellidoMaterno, String apellidoPaterno, String direccion,
            String colonia, String cP, String nroEmpleado, Integer ciudadesId,
            Integer estadosId, Integer territoriosId, Integer territorioNativoId, Integer perfilesId, Integer unidadesNegociosId, Integer equiposId,
            Integer pertenece, Integer activo);
    
    public void getMetodo();
    
    public String[] getAccesos(String usuariosOID);

    public List<Equipo> equiposUsuarios(UsuarioSession o);

    public String crearSession(UsuarioSession o);

    public void cerrarSession(String oid);

    public void cerrarSessionesLocales();

    public void closeAllSessions();

    public void cambiarEquipo(UsuarioSession o);

    public boolean validarSession(String oid);

    public UsuarioSession getCurrentSession(String logOid);
}
