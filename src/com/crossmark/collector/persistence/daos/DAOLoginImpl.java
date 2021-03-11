package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.crossmark.collector.presentation.views.utils.Utileria.*;

/**
 * Created by christian on 13/01/2015.
 */
public class DAOLoginImpl implements DAOLogin {

    private DatabaseStoredProc spUsuariosLogUps;
    private DatabaseStoredProc spUsuariosOffUps;
    private DatabaseStoredProc spUsuariosLogDel;
    private DatabaseStoredProc spUsuariosEquiposUps;
    private DatabaseStoredProc spUsuariosLogSel;

    private UsuarioSession usuarioSession;

    public DatabaseStoredProc getSpUsuariosLogUps() {
        return spUsuariosLogUps;
    }

    public void setSpUsuariosLogUps(DatabaseStoredProc spUsuariosLogUps) {
        this.spUsuariosLogUps = spUsuariosLogUps;
    }

    public DatabaseStoredProc getSpUsuariosOffUps() {
        return spUsuariosOffUps;
    }

    public void setSpUsuariosOffUps(DatabaseStoredProc spUsuariosOffUps) {
        this.spUsuariosOffUps = spUsuariosOffUps;
    }

    public DatabaseStoredProc getSpUsuariosLogDel() {
        return spUsuariosLogDel;
    }

    public void setSpUsuariosLogDel(DatabaseStoredProc spUsuariosLogDel) {
        this.spUsuariosLogDel = spUsuariosLogDel;
    }

    public DatabaseStoredProc getSpUsuariosEquiposUps() {
        return spUsuariosEquiposUps;
    }

    public void setSpUsuariosEquiposUps(DatabaseStoredProc spUsuariosEquiposUps) {
        this.spUsuariosEquiposUps = spUsuariosEquiposUps;
    }

    public DatabaseStoredProc getSpUsuariosLogSel() {
        return spUsuariosLogSel;
    }

    public void setSpUsuariosLogSel(DatabaseStoredProc spUsuariosLogSel) {
        this.spUsuariosLogSel = spUsuariosLogSel;
    }

    @Override
    public String crearSession(UsuarioSession o) {
        String nvoLogOID = null;
            Map<String, Object> inputs = new TreeMap<>();
            inputs.put("UsuariosLogOID", null);
            inputs.put("UsuariosOID", o.getOID());
            inputs.put("EquiposID", o.getEquipoId());
            inputs.put("Ip", o.getIp());
        try {
            Map out =   spUsuariosLogUps.execute(inputs);
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map map = (Map) object;
                nvoLogOID = validarStringNull(map.get("UsuariosLogOID"));
            }
        } catch (Exception e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        logger(getClass()).info("Todo bien en crear session");
        return nvoLogOID;
    }


    public boolean validarSession(String oid) {
        List<UsuarioSession> listado = new ArrayList<>();
        UsuarioSession nvo;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", oid);
        inputs.put("Activo",1);
        inputs.put("UsuariosLogOID",null);
        try {
        Map out = spUsuariosLogSel.execute(inputs);

            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map map = (Map) object;
                nvo = usuarioSession(map);
                if(nvo.isActivo()){
                    listado.add(nvo);
                }
            }
        } catch (Exception e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        logger(getClass()).info("Todo bien en validar la session");
        return listado.isEmpty();
    }

    public UsuarioSession getCurrentSession(String oid) {
        List<UsuarioSession> listado = new ArrayList<>();
        UsuarioSession nvo;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", null);
        inputs.put("Activo",1);
        inputs.put("UsuariosLogOID",oid);
        try {
        Map out = spUsuariosLogSel.execute(inputs);
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map map = (Map) object;
                nvo = usuarioSession(map);
                if(nvo.isActivo()){
                    listado.add(nvo);
                }
            }
        } catch (Exception e) {
            Utileria.logger(getClass()).info(e.getCause());
        }
        logger(getClass()).info("Todo bien en getCurrentSession");
        return listado.get(0);
    }

    @Override
    public void cerrarSession(String oid) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosLogOID", oid);
        inputs.put("IP", getIP());
        inputs.put("All", 0);
        spUsuariosOffUps.execute(inputs);
    }

    @Override
    public void cambiarEquipo(UsuarioSession o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosOID", o.getOID());
        inputs.put("EquiposId", o.getEquipoId());
        spUsuariosEquiposUps.execute(inputs);
    }

    @Override
    public void cerrarSessionesLocales() {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosLogOID", null);
        inputs.put("IP", getIP());
        inputs.put("All", 0);
        spUsuariosOffUps.execute(inputs);
    }

    @Override
    public void closeAllSessions() {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UsuariosLogOID", null);
        inputs.put("IP", getIP());
        inputs.put("All", 0);
        spUsuariosOffUps.execute(inputs);
    }

    public UsuarioSession usuarioSession(Map element) {
        UsuarioSession o = new UsuarioSession();
        o.setUsuariosLogOID(Utileria.validarStringNull(element.get("UsuariosLogOID")));
        o.setOID(validarStringNull(element.get("UsuariosOID")));
        o.setEquipoId(validarIntegerNull((element.get("EquiposID"))));
        o.setFechaLogIn(getTimeStamp(element.get("FechaLogIn")));
        o.setFechaLogOff(getTimeStamp(element.get("FechaLogOff")));
        o.setActivo(Boolean.valueOf(element.get("Activo").toString()));
        o.setIp(Utileria.validarStringNull(element.get("Ip")));
        return o;
    }
}
