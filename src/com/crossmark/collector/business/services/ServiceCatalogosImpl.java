package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.*;
import com.crossmark.collector.business.domain.Tienda;
import com.crossmark.collector.persistence.daos.*;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.SeccionParametro;
import com.crossmark.collector.presentation.views.visitas.objects.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ServiceCatalogosImpl implements ServiceCatalogos, Serializable {

    private DAORegiones daoRegiones;
    private DAOTerritorios daoTerritorios;
    private DAOUsuarios daoUsuarios;
    private DAOEquipos daoEquipos;
    private DAOUnidadesNegocio daoUnidadesNegocio;
    private DAOTiendas daoTiendas;
    private DAOTiendas daoTiendasTerritoriosSel;
    private DAOCiudades daoCiudades;
    private DAOEstados daoEstados;
    private DAOClientes daoClientes;
    private DAOTiposMensajes daoTiposMensajes;
    private DAOFormatos daoFormatos;
    private DAOCadena daoCadena;
    private DAOMotivos daoMotivos;
    private DAOParametros daoParametros;
    private DAOOpciones daoOpciones;

    public DAOTiendas getDaoTiendasTerritoriosSel() {
        return daoTiendasTerritoriosSel;
    }

    public void setDaoTiendasTerritoriosSel(DAOTiendas daoTiendasTerritoriosSel) {
        this.daoTiendasTerritoriosSel = daoTiendasTerritoriosSel;
    }

    public DAOUsuarios getDaoUsuarios() {
        return daoUsuarios;
    }

    public void setDaoUsuarios(DAOUsuarios daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    public DAORegiones getDaoRegiones() {
        return daoRegiones;
    }

    public void setDaoRegiones(DAORegiones daoRegiones) {
        this.daoRegiones = daoRegiones;
    }

    public DAOTerritorios getDaoTerritorios() {
        return daoTerritorios;
    }

    public void setDaoTerritorios(DAOTerritorios daoTerritorios) {
        this.daoTerritorios = daoTerritorios;
    }

    public DAOEquipos getDaoEquipos() {
        return daoEquipos;
    }

    public void setDaoEquipos(DAOEquipos daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    public DAOUnidadesNegocio getDaoUnidadesNegocio() {
        return daoUnidadesNegocio;
    }

    public void setDaoUnidadesNegocio(DAOUnidadesNegocio daoUnidadesNegocio) {
        this.daoUnidadesNegocio = daoUnidadesNegocio;
    }

    public DAOTiendas getDaoTiendas() {
        return daoTiendas;
    }

    public void setDaoTiendas(DAOTiendas daoTiendas) {
        this.daoTiendas = daoTiendas;
    }

    public DAOCiudades getDaoCiudades() {
        return daoCiudades;
    }

    public void setDaoCiudades(DAOCiudades daoCiudades) {
        this.daoCiudades = daoCiudades;
    }

    public DAOEstados getDaoEstados() {
        return daoEstados;
    }

    public void setDaoEstados(DAOEstados daoEstados) {
        this.daoEstados = daoEstados;
    }

    public DAOClientes getDaoClientes() {
        return daoClientes;
    }

    public void setDaoClientes(DAOClientes daoClientes) {
        this.daoClientes = daoClientes;
    }

    public DAOTiposMensajes getDaoTiposMensajes() {
        return daoTiposMensajes;
    }

    public void setDaoTiposMensajes(DAOTiposMensajes daoTiposMensajes) {
        this.daoTiposMensajes = daoTiposMensajes;
    }

    public DAOCadena getDaoCadena() {
        return daoCadena;
    }

    public void setDaoCadena(DAOCadena daoCadena) {
        this.daoCadena = daoCadena;
    }

    public DAOFormatos getDaoFormatos() {
        return daoFormatos;
    }

    public void setDaoFormatos(DAOFormatos daoFormatos) {
        this.daoFormatos = daoFormatos;
    }

    public DAOMotivos getDaoMotivos() {
        return daoMotivos;
    }

    public void setDaoMotivos(DAOMotivos daoMotivos) {
        this.daoMotivos = daoMotivos;
    }

    public DAOParametros getDaoParametros() {
        return daoParametros;
    }

    public void setDaoParametros(DAOParametros daoParametros) {
        this.daoParametros = daoParametros;
    }

    @Override
    public DAOOpciones getDaoOpciones() {
        return daoOpciones;
    }

    public void setDaoOpciones(DAOOpciones daoOpciones) {
        this.daoOpciones = daoOpciones;
    }

    @Override
    public List<Region> getAllRegions() {
        return this.daoRegiones.getAll();
    }

    @Override
    public Region getRegionById(Integer regionId) {
        return daoRegiones.getById(regionId);
    }

    @Override
    public void editar(Region region) {
        daoRegiones.editar(region);
    }

    @Override
    public void crear(Region region) {
        daoRegiones.crear(region);
    }

    @Override
    public String eliminar(Region region) {
        return daoRegiones.eliminar(region);
    }

    @Override
    public void crear(Territorio territorio) {
        daoTerritorios.crear(territorio);
    }

    @Override
    public void editar(Territorio territorio) {
        daoTerritorios.editar(territorio);
    }

    @Override
    public String eliminar(Territorio territorio) {
        return daoTerritorios.eliminar(territorio);
    }

    @Override
    public Territorio getTerritorioById(Integer idTerritorio) {
        return daoTerritorios.getById(idTerritorio);
    }

    @Override
    public List<Territorio> getAllTerritories() {
        return daoTerritorios.getAll();
    }

    @Override
    public List<Region> getAllActivatedRegions() {
        return daoRegiones.getAllActivated();
    }

    @Override
    public List<Territorio> getAllActivatedTerritories() {
        return daoTerritorios.getAllActivated();
    }

    @Override
    public List<Usuario> getAllUsuariosCoordinador() {
        return daoUsuarios.getAllUsuariosCoordinador();
    }

    @Override
    public void crear(Equipo equipo) {
        daoEquipos.crear(equipo);
    }

    @Override
    public void editar(Equipo equipo) {
        daoEquipos.editar(equipo);
    }

    @Override
    public String eliminar(Equipo equipo) {
        return daoEquipos.eliminar(equipo);
    }

    @Override
    public Equipo getEquipoById(Integer idEquipo) {
        return daoEquipos.getById(idEquipo);
    }

    @Override
    public List<Equipo> getAllEquipos() {
        return daoEquipos.getAll();
    }

    @Override
    public List<Equipo> getAllActivatedEquipos() {
        return daoEquipos.getAllActivated();
    }

    @Override
    public void crear(UnidadesNegocio unidadesNegocio) {
        daoUnidadesNegocio.crear(unidadesNegocio);
    }

    @Override
    public void editar(UnidadesNegocio unidadesNegocio) {
        daoUnidadesNegocio.editar(unidadesNegocio);
    }

    @Override
    public String eliminar(UnidadesNegocio unidadesNegocio) {
        return daoUnidadesNegocio.eliminar(unidadesNegocio);
    }

    @Override
    public UnidadesNegocio getUnidadNegocioById(Integer idUnidad) {
        return daoUnidadesNegocio.getById(idUnidad);
    }

    @Override
    public List<UnidadesNegocio> getAllUnidadesNegocios() {
        return daoUnidadesNegocio.getAll();
    }

    @Override
    public List<UnidadesNegocio> getAllActivatedUnidadesNegocios() {
        return daoUnidadesNegocio.getAllActivated();
    }

    @Override
    public void crear(Tienda o) {
        daoTiendas.crear(o);
    }

    @Override
    public void editar(Tienda o) {
        daoTiendas.editar(o);
    }

    @Override
    public String eliminar(Tienda o) {
        return daoTiendas.eliminar(o);
    }

    @Override
    public Tienda getTiendaById(Integer id) {
        return daoTiendas.getById(id);
    }

    @Override
    public List<Tienda> getAllTiendas(int pageIndex, int pageSize, Map<String, Object> filters) {
        //return daoTiendas.getAll(pageIndex, pageSize);
        return daoTiendasTerritoriosSel.getAll(pageIndex, pageSize, filters);
    }

    @Override
    public List<Tienda> getAllActivatedTiendas(int pageIndex, int pageSize) {
        return daoTiendas.getAllActivated(pageIndex, pageSize);
    }
    /*
    @Override
    public List<Tienda> getAllTiendas() {
        return daoTiendas.getAll(1, 10);
    }*/

    @Override
    public List<Tienda> getAllActivatedTiendas() {
        return daoTiendas.getAllActivated(1, 10);
    }

    @Override
    public void crear(Estado o) {
        daoEstados.crear(o);
    }

    @Override
    public void editar(Estado o) {
        daoEstados.editar(o);
    }

    @Override
    public String eliminar(Estado o) {
        return daoEstados.eliminar(o);
    }

    @Override
    public Estado getEstadoById(Integer id) {
        return daoEstados.getById(id);
    }

    @Override
    public List<Estado> getAllEstados() {
        return daoEstados.getAll();
    }

    @Override
    public List<Estado> getAllActivatedEstados() {
        return daoEstados.getAllActivated();
    }

    @Override
    public void crear(Ciudad o) {
        daoCiudades.crear(o);
    }

    @Override
    public void editar(Ciudad o) {
        daoCiudades.editar(o);
    }

    @Override
    public String eliminar(Ciudad o) {
        return daoCiudades.eliminar(o);
    }

    @Override
    public Ciudad getCiudadById(Integer id) {
        return daoCiudades.getById(id);
    }

    @Override
    public List<Ciudad> getAllCiudades() {
        return daoCiudades.getAll();
    }

    @Override
    public List<Ciudad> getAllActivatedCiudades() {
        return daoCiudades.getAllActivated();
    }

    @Override
    public List<Ciudad> getCiudadesByEstado(Integer id) {
        return  daoCiudades.getCiudadesByEstado(id);
    }

    @Override
    public void crear(Cliente o) {
        daoClientes.crear(o);
    }

    @Override
    public void editar(Cliente o) {
        daoClientes.editar(o);
    }

    @Override
    public String eliminar(Cliente o) {
        return daoClientes.eliminar(o);
    }

    @Override
    public Cliente getClienteById(Integer id) {
        return daoClientes.getById(id);
    }

    @Override
    public List<Cliente> getAllClientes() {
        return daoClientes.getAll();
    }

    @Override
    public List<Cliente> getAllActivatedClientes() {
        return daoClientes.getAllActivated();
    }

    @Override
    public void crear(TipoMensaje o) {
        daoTiposMensajes.crear(o);
    }

    @Override
    public void editar(TipoMensaje o) {
        daoTiposMensajes.editar(o);
    }

    @Override
    public String eliminar(TipoMensaje o) {
        return  daoTiposMensajes.eliminar(o);
    }

    @Override
    public TipoMensaje getTipoMensajeById(Integer id) {
        return  daoTiposMensajes.getById(id);
    }

    @Override
    public List<TipoMensaje> getAllTiposMensajes() {
        return daoTiposMensajes.getAll();
    }

    @Override
    public List<TipoMensaje> getAllActivatedTipoMensaje() {
        return daoTiposMensajes.getAllActivated();
    }

    @Override
    public Motivo getMotivoById(Integer id) {
        return daoMotivos.getById(id);
    }

    @Override
    public List<Motivo> getAllMotivos() {
        return daoMotivos.getAll();
    }

    @Override
    public List<Motivo> getAllActivatedMotivos() {
        return daoMotivos.getAllActivated();
    }

    @Override
    public void editar(Motivo o) {
        daoMotivos.editar(o);
    }

    @Override
    public void crear(Motivo o) {
        daoMotivos.crear(o);
    }

    @Override
    public String eliminar(Motivo o) {
        return daoMotivos.eliminar(o);
    }
/*
    @Override
    public void crear(Opciones o) {
        daoOpciones.crear(o);
    }

    @Override
    public void editar(Opciones o) {
        daoOpciones.editar(o);
    }

    @Override
    public String eliminar(Opciones o) {
        return daoOpciones.eliminar(o);
    }

    @Override
    public Opciones getOpcionById(Integer id) {
        return daoOpciones.getById(id);
    }

    @Override
    public List<Opciones> getAllOpciones() {
        return daoOpciones.getAll();
    }

    @Override
    public List<Opciones> getAllActivatedOpciones() {
        return daoOpciones.getAll();
    }
*/

    @Override
    public List<Lista> getAllLista() {
        return daoOpciones.getDaoLista().getAll();
    }

    @Override
    public List<Parametros> getAllParametros() {
        return daoParametros.getAll();
    }

    @Override
    public List<SeccionParametro> getAllSeccionParametro() {
        return daoParametros.getAllSecciones();
    }

    @Override
    public void guardar(Map<Integer,String> parametros) {
        daoParametros.guardar(parametros);
    }


    @Override
    public List<OpcionCruzada> getOpcionesCruzadasByOid(String oid) {
        return daoOpciones.getDaoLista().getDaoTipoLista().getDaoOpcionCruzada().getByOIDLista(oid);
    }

    @Override
    public List<OpcionPlana> getOpcionesPlanasByOid(String oid) {
        return daoOpciones.getDaoLista().getDaoTipoLista().getDaoOpcionPlana().getByOIDLista(oid);
    }

    @Override
    public List<TipoLista> getAllTipoListas() {
        return daoOpciones.getDaoLista().getDaoTipoLista().getAll();
    }

    @Override
    public void crear(Lista o) {
        daoOpciones.getDaoLista().crear(o);
    }

    @Override
    public void editar(Lista o) {
        daoOpciones.getDaoLista().editar2(o);
    }

    @Override
    public List<Formato> getAllActivatedFormatos() {
        return daoFormatos.getAllActivated();
    }

    @Override
    public List<Cadena> getAllActivatedCadenas() {
        return daoCadena.getAllActivated();
    }

    
}
