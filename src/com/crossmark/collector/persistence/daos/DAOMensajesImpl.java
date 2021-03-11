package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Mensaje;
import com.crossmark.collector.presentation.views.utils.DateHelper;
import com.crossmark.collector.presentation.views.utils.DateUtil;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by christian on 05/01/2015.
 */
public class DAOMensajesImpl implements  DAOMensajes {

    private DatabaseStoredProc spMensajesSel;

    private DAOTiposMensajes  daoTiposMensajes;

    private DAOEquipos daoEquipos;

    public DAOTiposMensajes getDaoTiposMensajes() {
        return daoTiposMensajes;
    }

    public void setDaoTiposMensajes(DAOTiposMensajes daoTiposMensajes) {
        this.daoTiposMensajes = daoTiposMensajes;
    }

    public DAOEquipos getDaoEquipos() {
        return daoEquipos;
    }

    public void setDaoEquipos(DAOEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public DatabaseStoredProc getSpMensajesSel() {
        return spMensajesSel;
    }

    public void setSpMensajesSel(DatabaseStoredProc spMensajesSel) {
        this.spMensajesSel = spMensajesSel;
    }

    @Override
    public void crear(Mensaje o) {

    }

    @Override
    public void editar(Mensaje o) {

    }

    @Override
    public String eliminar(Mensaje o) {
        return null;
    }

    @Override
    public Mensaje getById(Integer id) {
        List<Mensaje> listado = new ArrayList<>();
        Mensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MensajesOID", null);
        inputs.put("UsuariosOID", null);
        inputs.put("TipoMensajesID", null);
        inputs.put("FechaIni", null);
        inputs.put("FechaFin", null);
        Map out = spMensajesSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Mensaje> getAll() {
        List<Mensaje> listado = new ArrayList<>();
        Mensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MensajesOID", null);
        inputs.put("UsuariosOID", null);
        inputs.put("TipoMensajesID", null);
        inputs.put("FechaIni", null);
        inputs.put("FechaFin", null);
        Map out = spMensajesSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Mensaje> getAllActivated() {
        List<Mensaje> listado = new ArrayList<>();
        Mensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MensajesOID", null);
        inputs.put("UsuariosOID", null);
        inputs.put("TipoMensajesID", null);
        inputs.put("FechaIni", null);
        inputs.put("FechaFin", null);
        Map out = spMensajesSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Mensaje> getAllMensajesByUsuarioOID(String oid) {
        List<Mensaje> listado = new ArrayList<>();
        Mensaje o;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("MensajesOID", null);
        inputs.put("UsuariosOID", oid);
        inputs.put("TipoMensajesID", null);
        inputs.put("FechaIni", null);
        inputs.put("FechaFin", null);
        Map out = spMensajesSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public Mensaje genericObject(Object args) {
        Map element = (Map) args;
        Mensaje o = new Mensaje();

        o.setOid(Utileria.validarStringNull(element.get("MensajesOID")));
        o.setMensaje(Utileria.validarStringNull(element.get("Mensaje")));
        o.getUsuario().setUsuariosOID(Utileria.validarStringNull(element.get("UsuariosOID")));
        o.getUsuario().setApellidoMaterno(Utileria.validarStringNull(element.get("ApellidoMaterno")));
        o.getUsuario().setApellidoPaterno(Utileria.validarStringNull(element.get("ApellidoPaterno")));
        o.getUsuario().setNombre(Utileria.validarStringNull(element.get("Nombre")));
        o.setFecha( DateHelper.dateFormatDMY(DateHelper.convertirStringToDateDMYHMS( element.get("Fecha") == null ? "" : String.valueOf(element.get("Fecha")) )) );
        /*
        o.setdFecha(Utileria.fechaFormat3(o.getFecha()));
        try {
            o.setFecha(Utileria.dateToString(o.getdFecha(), Utileria.DATA_FORMAT_1));
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e.getCause());
        }
        */
        o.getTipoMensaje().setId(Integer.valueOf(element.get("TipoMensajesId").toString()));
        o.getTipoMensaje().setNombre(Utileria.validarStringNull(element.get("TipoMensaje")));

        o.setEquipo(daoEquipos.getById(Integer.valueOf(element.get("EquiposId").toString())));
        o.setUsuarioDestinatarioOID(Utileria.validarStringNull(element.get("Destinatario")));

        return o;
    }
}
