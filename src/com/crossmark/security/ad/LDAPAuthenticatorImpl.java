package com.crossmark.security.ad;

import com.crossmark.collector.presentation.views.utils.Utileria;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.Serializable;
import java.util.*;

/**
 * Created by christian on 23/12/2014.
 */
public class LDAPAuthenticatorImpl implements LDAPAuthenticator,Serializable{

    private LDAPProperties ldapProperties;

    private String returnedAtts[] = {"sn", "givenName", "name",
            "userPrincipalName", "displayName", "memberOf", "title", "mail",
            "departmentNumber", "department", "manager", "whenCreated",
            "sAMAccountName", "lockoutTime", "idPuesto", "employeeNumber"};


    @Override
    public Boolean userExist(int user) {
        return userExist(String.valueOf(user));
    }

    @Override
    public Boolean userExist(String user) {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user
                + "))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable<String, String> env = rellenarParametrosLDAPAdmin();

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            if (answer != null && answer.hasMore()) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
        return Boolean.FALSE;
    }

    @Override
    public UsuarioAD getUser(String user) {
        UsuarioAD usuario = null;
        Map<String, Object> attrs = this.authenticate(user);
        usuario = rellenarUsuarioAD(attrs);
        return usuario;
    }

    @Override
    public UsuarioAD rellenarUsuarioAD(Map<String, Object> attrs) {
        UsuarioAD usuario;
        if (attrs != null) {
            usuario = new UsuarioAD();
            for (Map.Entry<String, Object> elemento : attrs.entrySet()) {
                String key = elemento.getKey();
                Object value = elemento.getValue();
                if (attrs.get(key) instanceof String) {
                    //  Utileria.logger(getClass()).info(
                    //          key + ": " + attrs.get(key));
                    if (key.compareToIgnoreCase("sn") == 0) {
                        usuario.setBmr((String) value);// Apellido
                    }
                    if (key.compareToIgnoreCase("mail") == 0) {
                        usuario.setEmail((String) attrs.get((String) value));
                    }
                    if (key.compareToIgnoreCase("whenCreated") == 0) {
                        usuario.setFecha_alta((String) value);
                    }
                    if (key.compareToIgnoreCase("displayName") == 0) {
                        usuario.setNick((String) value);
                    }
                    if (key.compareToIgnoreCase("name") == 0) {
                        usuario.setNombre((String) value);
                    }
                    if (key.compareToIgnoreCase("departmentNumber") == 0) {
                        usuario.setStatus((String) value);
                    }

                    if (key.compareToIgnoreCase("employeeNumber") == 0) {
                        usuario.setNumeroEmpleado((String) value);
                    }

                    if (key.compareToIgnoreCase("sAMAccountName") == 0) {
                        usuario.setLogin((String) value);
                    }
                    // TODO:Falta definir como se llamara el atributo que
                    // cachara el numero de nomina
                    // if(attrKey.compareToIgnoreCase("------")==0){
                    // usuario.setNumeroNomina((String)attrs.get(attrKey));
                    // }

                    if (key.compareToIgnoreCase("title") == 0) {
                        usuario.setTitulo((String) value);
                    }

                    if (key.compareToIgnoreCase("password") == 0) {
                        usuario.setPassword((String) value);
                    }
                } //else {
                //  Utileria.logger(getClass()).info(
                //         key + ": (Multiple Values)");
                //   for (Object o : (HashSet) value) {
                ///   Utileria.logger(getClass()).info("\t value: " + o);
                //  }
                //}

            }
        } else {
            Utileria.logger(getClass()).info("Attributes are null!");
            usuario = null;
        }
        return usuario;
    }

    @Override
    public List<UsuarioAD> getUsers() {
        UsuarioAD usuario = null;
        List<UsuarioAD> lista = new ArrayList<>();
        List<Map<String, Object>> listaAttrs = searchActiveUsers();
        for (Map<String, Object> attrs : listaAttrs) {
            usuario = rellenarUsuarioAD(attrs);
            lista.add(usuario);
        }
        return lista;
    }

    @Override
    public boolean validarConexionLDAP() {
        boolean validacion = false;

        Hashtable<String, String> env = rellenarParametrosLDAPAdmin();

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            ctxGC.toString();
            validacion = true;

        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return validacion;
    }

    @Override
    public UsuarioAD getUserObj(String user, String password) {
        Utileria.logger(getClass()).info("getUserName:"+user+"   -    getPassword:"+password);
        
        UsuarioAD usuario = null;
        Map<String, Object> attrs = this.authenticate(ldapProperties.getPrefijo()+user, password);
        usuario = rellenarUsuarioAD(attrs);
        return usuario;
    }

    @Override
    public List<Map<String, Object>> searchActiveUsers() {
        String searchFilter = "(&(objectClass=user)(!(lockoutTime=*)))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable<String, String> env = rellenarParametrosLDAPAdmin();

        List<Map<String, Object>> listValues = new ArrayList<Map<String, Object>>();
        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String, Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration<?> n = attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String) n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                    listValues.add(amap);
                }
                ctxGC.close(); // Close and clean up
            }
        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return listValues;
    }

    @Override
    public List<Map<String, Object>> especificSearch(String filter) {
        String searchFilter = "(&(objectClass=user)(" + filter + "))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable<String, String> env = rellenarParametrosLDAPAdmin();
        List<Map<String, Object>> listValues = new ArrayList<Map<String, Object>>();

        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String, Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration<?> n = attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String) n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                    listValues.add(amap);
                }
                ctxGC.close(); // Close and clean up
            }
        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return listValues;
    }

    @Override
    public Map<String, Object> authenticateByEmployeeNumber(int employeeNumber) {
        String searchFilter = "(&(objectClass=user)(employeeNumber="
                + employeeNumber + "))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable<String, String> env = rellenarParametrosLDAPAdmin();

        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String, Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration<?> n = attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String) n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                }
                ctxGC.close(); // Close and clean up
                return amap;
            }
        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return null;
    }

    @Override
    public Map<String, Object> authenticate(String user) {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user
                + "))";

        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        Hashtable<String, String> env = inicializarParametrosLDAP(user, null);
        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String, Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration<?> n = attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String) n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                }
                ctxGC.close(); // Close and clean up
                return amap;
            }
        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return null;
    }

    @Override
    public Map<String, Object> authenticate(String user, String pass) {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user
                + "))";
        Utileria.logger(getClass()).error("searchFilter:"+searchFilter);
        
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        Hashtable<String, String> env = inicializarParametrosLDAP(user, pass);

        LdapContext ctxGC = null;
        try {
            ctxGC = new InitialLdapContext(env, null);
            NamingEnumeration<SearchResult> answer = ctxGC.search(ldapProperties.getSearchBase(),
                    searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                Map<String, Object> amap = null;
                if (attrs != null) {
                    amap = new HashMap<String, Object>();
                    NamingEnumeration<?> ne = attrs.getAll();
                    while (ne.hasMore()) {
                        Attribute attr = (Attribute) ne.next();
                        if (attr.size() == 1) {
                            amap.put(attr.getID(), attr.get());
                        } else {
                            HashSet<String> s = new HashSet<String>();
                            NamingEnumeration<?> n = attr.getAll();
                            while (n.hasMoreElements()) {
                                s.add((String) n.nextElement());
                            }
                            amap.put(attr.getID(), s);
                        }
                    }
                    ne.close();
                }
                ctxGC.close(); // Close and clean up
                return amap;
            }
        } catch (Exception ex) {
            Utileria.logger(getClass()).error(ex);
        }
        return null;
    }

    @Override
    public Hashtable<String, String> inicializarParametrosLDAP(String user,
                                                                String pass) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapProperties.getHost());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@" + ldapProperties.getDomain());
        if (pass != null) {
            env.put(Context.SECURITY_CREDENTIALS, pass);
        }
        Utileria.logger(getClass()).error("ldapProperties.getDomain():"+ldapProperties.getDomain()+"    ldapProperties.getHost():"+ldapProperties.getHost()+"  user:"+user+"    pass:"+pass);
        return env;
    }

    @Override
    public Hashtable<String, String> rellenarParametrosLDAPAdmin() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapProperties.getHost());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldapProperties.getAdmin() + "@" + ldapProperties.getDomain());
        env.put(Context.SECURITY_CREDENTIALS,ldapProperties.getPassword());
        
        Utileria.logger(getClass()).error("ldapProperties.getDomain():"+ldapProperties.getDomain()+"    ldapProperties.getHost():"+ldapProperties.getHost()+"  ldapProperties.getAdmin():"+ldapProperties.getAdmin()+"    ldapProperties.getPassword():"+ldapProperties.getPassword());
        return env;
    }

    /*
     * public static void main(String[] args) { ADAuthenticator adAuthenticator
     * = new ADAuthenticator(); System.out.println("Testing good password...");
     * // Map<String, Object> attrs = //
     * adAuthenticator.authenticate("2119","Passw0rd"); // if(attrs!=null){
     * List<Map<String, Object>> lstattrs = adAuthenticator .especificSearch(
     * "distinguishedName=CN=Administrador,CN=Users,DC=adinfinitumty,DC=com");
     * // List<Map<String, Object>> lstattrs = //
     * adAuthenticator.especificSearch("departmentNumber=1"); if (lstattrs !=
     * null && lstattrs.size() > 0) { for (Map<String, Object> attrs : lstattrs)
     * { for (String attrKey : attrs.keySet()) { if (attrs.get(attrKey)
     * instanceof String) { System.out.println(attrKey + ": " +
     * attrs.get(attrKey)); } else { System.out.println(attrKey +
     * ": (Multiple Values)"); for (Object o : (HashSet) attrs.get(attrKey)) {
     * System.out.println("\t value: " + o); } } } } } else {
     * System.out.println("Attributes are null!"); }
     *
     * System.out.println(adAuthenticator.userExist("2751")); }
     */

    public LDAPProperties getLdapProperties() {
        return ldapProperties;
    }

    public void setLdapProperties(LDAPProperties ldapProperties) {
        this.ldapProperties = ldapProperties;
    }
}
