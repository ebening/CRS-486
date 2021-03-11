package com.crossmark.security.ad;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by christian on 23/12/2014.
 */
public interface LDAPAuthenticator {

    public Boolean userExist(int user);

    public Boolean userExist(String user);

    public UsuarioAD getUser(String user);

    public UsuarioAD rellenarUsuarioAD(Map<String, Object> attrs);

    public List<UsuarioAD> getUsers();

    public boolean validarConexionLDAP();

    public UsuarioAD getUserObj(String user, String password);

    public List<Map<String, Object>> searchActiveUsers();

    public List<Map<String, Object>> especificSearch(String filter);

    public Map<String, Object> authenticateByEmployeeNumber(int employeeNumber);

    public Map<String, Object> authenticate(String user);

    public Map<String, Object> authenticate(String user, String pass);

    public Hashtable<String, String> inicializarParametrosLDAP(String user, String pass);

    public Hashtable<String, String> rellenarParametrosLDAPAdmin();

}
