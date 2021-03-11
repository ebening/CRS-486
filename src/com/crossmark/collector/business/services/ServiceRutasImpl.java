package com.crossmark.collector.business.services;

import java.util.List;
import java.util.Map;

import com.crossmark.collector.presentation.views.visitas.objects.Ruta;
import com.crossmark.collector.persistence.daos.DAORutas;
import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.io.Serializable;
import java.util.HashMap;

public class ServiceRutasImpl implements ServiceRutas, Serializable {
	DAORutas daoRutasTiendasUsuarios;
        DAORutas daoRutasSel;
        DAORutas daoRutasDel;
        DAORutas daoGuardaRuta;
	DAORutas daoRutasTiendas;
        DAORutas daoRutasUsuarios;
        DAORutas daoRutasTiendasSel;
        DAORutas daoRutasUsuariosSel;
        DAORutas daoRutasTiendasDel;
        DAORutas daoRutasUsuariosDel;
        DAORutas daoRutasTiendasVal;
        DAORutas daoRutasUsuariosVal;

        
    @Override
    public HashMap<String, Object> eliminaRutasTiendas(int RutaId, int TiendaId) {
        return getDaoRutasTiendasDel().eliminaRutasTiendas(RutaId, TiendaId);
    }

    @Override
    public HashMap<String, Object> eliminaRutasUsuario(int RutaId, String usuarioOID) {
        return getDaoRutasUsuariosDel().eliminaRutasUsuarios(RutaId, usuarioOID);
    }    
        
    @Override
    public Ruta getRutaById(int rutaId) {
        return getDaoRutasTiendasUsuarios().getRutaById(rutaId);
    }

    @Override
    public List<Map<String, Object>> listaRutasXTerritorio(int territorioId, int equipoId) {
        return getDaoRutasTiendasUsuarios().listaRutasXTerritorio(territorioId, equipoId);
    }

    @Override
    public HashMap<String, Object> guardaRuta(int territorioId, String nombreRuta,Integer equiposId, int RutaId) {
        return getDaoGuardaRuta().guardaRuta(territorioId, nombreRuta, equiposId, RutaId);
    }
    
    @Override
    public HashMap<String, Object> guardaTiendasRutas(int RutaId, List<Tienda> tiendas) {
        return getDaoRutasTiendas().guardaTiendasRutas(RutaId, tiendas);
    }

    @Override
    public HashMap<String, Object> guardaPromotoresRutas(int RutaId, List<Promotor> promotores) {
        return getDaoRutasUsuarios().guardaPromotorRutas(RutaId, promotores);
    }
    
    @Override
    public List<Map<String, Object>> listaTiendasXRuta(int RutaId, int TerritoriosId, int UnidadesNegociosId, int EquiposId) {
        return getDaoRutasTiendasSel().listaTiendasXRuta(RutaId, TerritoriosId, UnidadesNegociosId, EquiposId);
    }

    @Override
    public List<Map<String, Object>> listaUsuariosXRuta(int RutaId, int TerritoriosId, int UnidadesNegociosId, int EquiposId) {
        return getDaoRutasUsuariosSel().listaUsuariosXRuta(RutaId, TerritoriosId, UnidadesNegociosId, EquiposId);
    }
    
    @Override
    public List<Map<String, Object>> listaRutasSel(int RutaId, String Nombre, int TerritoriosId, int EquiposId) {
        return getDaoRutasSel().listaRutasSel(RutaId, Nombre, TerritoriosId, EquiposId);
    }
    
    @Override
    public HashMap<String, Object> eliminaRuta(int RutaId) {
        return getDaoRutasDel().eliminaRuta(RutaId);
    }

    @Override
    public List<Map<String, Object>> listaTiendasVal(int RutasId) {
        return getDaoRutasTiendasVal().listaTiendasVal(RutasId);
    }

    @Override
    public List<Map<String, Object>> listaUsuariosVal(int RutasId) {
        return getDaoRutasUsuariosVal().listaUsuariosVal(RutasId);
    }
    
    public DAORutas getDaoRutasTiendasUsuarios() {
        return daoRutasTiendasUsuarios;
    }

    public void setDaoRutasTiendasUsuarios(DAORutas daoRutasTiendasUsuarios) {
        this.daoRutasTiendasUsuarios = daoRutasTiendasUsuarios;
    }
    
    public DAORutas getDaoGuardaRuta() {
        return daoGuardaRuta;
    }

    public void setDaoGuardaRuta(DAORutas daoGuardaRuta) {
        this.daoGuardaRuta = daoGuardaRuta;
    }

    public DAORutas getDaoRutasTiendas() {
        return daoRutasTiendas;
    }

    public void setDaoRutasTiendas(DAORutas daoRutasTiendas) {
        this.daoRutasTiendas = daoRutasTiendas;
    }

    public DAORutas getDaoRutasUsuarios() {
        return daoRutasUsuarios;
    }

    public void setDaoRutasUsuarios(DAORutas daoRutasUsuarios) {
        this.daoRutasUsuarios = daoRutasUsuarios;
    }

    public DAORutas getDaoRutasTiendasSel() {
        return daoRutasTiendasSel;
    }

    public void setDaoRutasTiendasSel(DAORutas daoRutasTiendasSel) {
        this.daoRutasTiendasSel = daoRutasTiendasSel;
    }

    public DAORutas getDaoRutasUsuariosSel() {
        return daoRutasUsuariosSel;
    }

    public void setDaoRutasUsuariosSel(DAORutas daoRutasUsuariosSel) {
        this.daoRutasUsuariosSel = daoRutasUsuariosSel;
    }

    public DAORutas getDaoRutasSel() {
        return daoRutasSel;
    }

    public void setDaoRutasSel(DAORutas daoRutasSel) {
        this.daoRutasSel = daoRutasSel;
    }

    public DAORutas getDaoRutasDel() {
        return daoRutasDel;
    }

    public void setDaoRutasDel(DAORutas daoRutasDel) {
        this.daoRutasDel = daoRutasDel;
    }

    public DAORutas getDaoRutasTiendasDel() {
        return daoRutasTiendasDel;
    }

    public void setDaoRutasTiendasDel(DAORutas daoRutasTiendasDel) {
        this.daoRutasTiendasDel = daoRutasTiendasDel;
    }

    public DAORutas getDaoRutasUsuariosDel() {
        return daoRutasUsuariosDel;
    }

    public void setDaoRutasUsuariosDel(DAORutas daoRutasUsuariosDel) {
        this.daoRutasUsuariosDel = daoRutasUsuariosDel;
    }

    public DAORutas getDaoRutasTiendasVal() {
        return daoRutasTiendasVal;
    }

    public void setDaoRutasTiendasVal(DAORutas daoRutasTiendasVal) {
        this.daoRutasTiendasVal = daoRutasTiendasVal;
    }

    public DAORutas getDaoRutasUsuariosVal() {
        return daoRutasUsuariosVal;
    }

    public void setDaoRutasUsuariosVal(DAORutas daoRutasUsuariosVal) {
        this.daoRutasUsuariosVal = daoRutasUsuariosVal;
    }
    
    
}
