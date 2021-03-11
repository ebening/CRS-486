package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.*;
import com.crossmark.collector.business.domain.Tienda;
import com.crossmark.collector.persistence.daos.DAOOpciones;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.utils.SeccionParametro;
import com.crossmark.collector.presentation.views.visitas.objects.*;

import java.util.List;
import java.util.Map;

public interface ServiceCatalogos {

    public Region getRegionById(Integer id);

    public List<Region> getAllRegions();

    public List<Region> getAllActivatedRegions();

    public void editar(Region o);

    public void crear(Region o);

    public String eliminar(Region o);

    public void crear(Territorio o);

    public void editar(Territorio o);

    public String eliminar(Territorio o);

    public Territorio getTerritorioById(Integer id);

    public List<Territorio> getAllTerritories();

    public List<Territorio> getAllActivatedTerritories();

    public List<Usuario> getAllUsuariosCoordinador();

    public void crear(Equipo o);

    public void editar(Equipo o);

    public String eliminar(Equipo o);

    public Equipo getEquipoById(Integer id);

    public List<Equipo> getAllEquipos();

    public List<Equipo> getAllActivatedEquipos();

    public void crear(UnidadesNegocio o);

    public void editar(UnidadesNegocio o);

    public String eliminar(UnidadesNegocio o);

    public UnidadesNegocio getUnidadNegocioById(Integer id);

    public List<UnidadesNegocio> getAllUnidadesNegocios();

    public List<UnidadesNegocio> getAllActivatedUnidadesNegocios();

    public void crear(Tienda o);

    public void editar(Tienda o);

    public String eliminar(Tienda o);

    public Tienda getTiendaById(Integer id);

    public List<Tienda> getAllTiendas(int pageIndex, int pageSize, Map<String, Object> filters);

    public List<Tienda> getAllActivatedTiendas(int pageIndex, int pageSize);
    
    //public List<Tienda> getAllTiendas();

    public List<Tienda> getAllActivatedTiendas();

    public void crear(Estado o);

    public void editar(Estado o);

    public String eliminar(Estado o);

    public Estado getEstadoById(Integer id);

    public List<Estado> getAllEstados();

    public List<Estado> getAllActivatedEstados();

    public void crear(Ciudad o);

    public void editar(Ciudad o);

    public String eliminar(Ciudad o);

    public Ciudad getCiudadById(Integer id);

    public List<Ciudad> getAllCiudades();

    public List<Ciudad> getAllActivatedCiudades();

    public List<Ciudad> getCiudadesByEstado(Integer id);

    public void crear(Cliente o);

    public void editar(Cliente o);

    public String eliminar(Cliente o);

    public Cliente getClienteById(Integer id);

    public List<Cliente> getAllClientes();

    public List<Cliente> getAllActivatedClientes();

    public void crear(TipoMensaje o);

    public void editar(TipoMensaje o);

    public String eliminar(TipoMensaje o);

    public TipoMensaje getTipoMensajeById(Integer id);

    public List<TipoMensaje> getAllTiposMensajes();

    public List<TipoMensaje> getAllActivatedTipoMensaje();

    public Motivo getMotivoById(Integer id);

    public List<Motivo> getAllMotivos();

    public List<Motivo> getAllActivatedMotivos();

    public void editar(Motivo o);

    public void crear(Motivo o);

    public String eliminar(Motivo o);

    public List<Parametros> getAllParametros();

    public List<SeccionParametro> getAllSeccionParametro();

    void guardar(Map<Integer,String> parametros);

//    public void crear(Opciones o);

  //  public void editar(Opciones o);

//    public String eliminar(Opciones o);

//    public Opciones getOpcionById(Integer id);

    public DAOOpciones getDaoOpciones();

    public List<Lista> getAllLista();

    public List<OpcionCruzada> getOpcionesCruzadasByOid(String oid);

    public List<OpcionPlana> getOpcionesPlanasByOid(String oid);

    public void crear(Lista o);

    public void editar(Lista o);

    public List<TipoLista> getAllTipoListas();

    public List<Formato> getAllActivatedFormatos();

    public List<Cadena> getAllActivatedCadenas();


}
