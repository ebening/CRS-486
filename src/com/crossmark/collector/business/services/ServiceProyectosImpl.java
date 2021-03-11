package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Archivos;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.persistence.daos.DAOEncuestas;
import com.crossmark.collector.persistence.daos.DAOProyectos;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceProyectosImpl implements ServiceProyectos, Serializable {
	
    DAOProyectos daoProyectos;
    DAOProyectos daoGuardaProyectos;
    DAOProyectos daoTraeProyecto;
    private DAOProyectos daoAgregaEncuesta;
    private DAOProyectos daoEliminaProyecto;
    private DAOProyectos daoEliminaEncuestaProyecto;
    private DAOProyectos daoTraeListaEncuestasProyecto;
    private DAOProyectos daoTrarEncuestas;
    private DAOProyectos daoArchivos;
    
    @Override
    public String getProyecto(Integer clienteId, String claveProyecto,String nombreProyecto, Date fechaInicio, Date fechaFin) {
        // TODO Auto-generated method stub
        return daoProyectos.getProyecto(clienteId, claveProyecto, nombreProyecto, fechaInicio, fechaFin);
    }

    @Override
    public List<Proyectos> listaProyectos(Integer proyectoId,String claveProyecto, String descripcionProyecto,String nombreProyecto,Integer clienteId, Integer unidadNegocioId,
                    Date fechaInicio, Date fechaFin, Date fechaVisible, boolean activo) {
        // TODO Auto-generated method stub
        return daoProyectos.listaProyectos(proyectoId, claveProyecto, descripcionProyecto, nombreProyecto,clienteId, unidadNegocioId, fechaInicio, fechaFin, fechaVisible, activo);
    }

    @Override
    public Integer guardaProyecto(int idProyecto,String claveProyecto,String nombreProyecto,
                    String descripcionProyecto, Date fechaVisible, Date fechaInicio,
                    Date fechaFin, int status, Integer unidadNegocioId, int visitaAutomatica,Integer clienteId, String imagen,Integer diasVigencias) {
        // TODO Auto-generated method stub
        return daoGuardaProyectos.guardaProyecto(idProyecto,claveProyecto, nombreProyecto,
                 descripcionProyecto,  fechaVisible,  fechaInicio,
                 fechaFin,  status,  unidadNegocioId,  visitaAutomatica, clienteId, imagen, diasVigencias);
    }

        
    @Override
    public Proyectos proyectoEditar(Integer proyectoId) {
        return daoTraeProyecto.proyectoEditar(proyectoId);
    }
        
        
        
    /**
     * @return the daoGuardaProyectos
     */
    public DAOProyectos getDaoGuardaProyectos() {
        return daoGuardaProyectos;
    }

    /**
     * @param daoGuardaProyectos the daoGuardaProyectos to set
     */
    public void setDaoGuardaProyectos(DAOProyectos daoGuardaProyectos) {
        this.daoGuardaProyectos = daoGuardaProyectos;
    }

    /**
     * @return the datoTraeProyecto
     */
    public DAOProyectos getDaoTraeProyecto() {
        return daoTraeProyecto;
    }

    /**
     * @param daoTraeProyecto the datoTraeProyecto to set
     */
    public void setDaoTraeProyecto(DAOProyectos daoTraeProyecto) {
        this.daoTraeProyecto = daoTraeProyecto;
    }

    @Override
    public String agregaEncuesta(int proyectoId, int encuestaId) {
        
        return daoAgregaEncuesta.agregaEncuesta(proyectoId, encuestaId);
    }

    /**
     * @return the daoAgregaEncuesta
     */
    public DAOProyectos getDaoAgregaEncuesta() {
        return daoAgregaEncuesta;
    }

    /**
     * @param daoAgregaEncuesta the daoAgregaEncuesta to set
     */
    public void setDaoAgregaEncuesta(DAOProyectos daoAgregaEncuesta) {
        this.daoAgregaEncuesta = daoAgregaEncuesta;
    }

    @Override
    public String eliminaProyecto(Integer proyectoId) {
        return daoEliminaProyecto.eliminaProyecto(proyectoId);
    }

    /**
     * @return the daoEliminaProyecto
     */
    public DAOProyectos getDaoEliminaProyecto() {
        return daoEliminaProyecto;
    }

    /**
     * @param daoEliminaProyecto the daoEliminaProyecto to set
     */
    public void setDaoEliminaProyecto(DAOProyectos daoEliminaProyecto) {
        this.daoEliminaProyecto = daoEliminaProyecto;
    }

    @Override
    public String eliminaEncuestaProyecto(int proyectoId, int encuestaId) {
        return daoEliminaEncuestaProyecto.eliminaEncuestaProyecto(proyectoId, encuestaId);
    }
    
    
    @Override
    public List<Encuestas> listaEncuestasProyecto(Integer inidadesNegociosID, Integer proyectoId){
        return daoTraeListaEncuestasProyecto.listaEncuestasProyecto(inidadesNegociosID, proyectoId);
    }
    
    @Override
    public List<Encuestas> listaEncuestas(Integer idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, Integer statusId, boolean esPlantilla, boolean activa, Date fechaCreacionEncuesta, String observacionEncuesta, Integer proyectoId, Integer unidadesNegocioEncuestaId){
        return daoTrarEncuestas.listaEncuestas(idEncuesta, nombreEncuesta, claveEncuesta, gpsEncuesta, statusId, esPlantilla, activa, fechaCreacionEncuesta, observacionEncuesta, proyectoId, unidadesNegocioEncuestaId);
    }
    
    @Override
    public List<Archivos> getListaArchivos(String archivosOID, Integer proyectosId){
        return daoArchivos.getListaArchivos(archivosOID, proyectosId);
    }
    
    @Override
    public void eliminaArchivoProyecto(String archivosOID){
        daoArchivos.eliminaArchivoProyecto(archivosOID);
    }

    @Override
    public String agregaArchivoProyecto(Archivos archivo){
        return daoArchivos.agregaArchivoProyecto(archivo);
    }
    
    /**
     * @return the daoEliminaEncuestaProyecto
     */
    public DAOProyectos getDaoEliminaEncuestaProyecto() {
        return daoEliminaEncuestaProyecto;
    }

    /**
     * @param daoEliminaEncuestaProyecto the daoEliminaEncuestaProyecto to set
     */
    public void setDaoEliminaEncuestaProyecto(DAOProyectos daoEliminaEncuestaProyecto) {
        this.daoEliminaEncuestaProyecto = daoEliminaEncuestaProyecto;
    }

    public DAOProyectos getDaoTraeListaEncuestasProyecto() {
        return daoTraeListaEncuestasProyecto;
    }

    public void setDaoTraeListaEncuestasProyecto(DAOProyectos daoTraeListaEncuestasProyecto) {
        this.daoTraeListaEncuestasProyecto = daoTraeListaEncuestasProyecto;
    }

    public DAOProyectos getDaoTrarEncuestas() {
        return daoTrarEncuestas;
    }

    public void setDaoTrarEncuestas(DAOProyectos daoTrarEncuestas) {
        this.daoTrarEncuestas = daoTrarEncuestas;
    }
    
    public DAOProyectos getDaoProyectos() {
        return daoProyectos;
    }
    
    public void setDaoProyectos(DAOProyectos daoProyectos) {
        this.daoProyectos = daoProyectos;
    }

    public DAOProyectos getDaoArchivos() {
        return daoArchivos;
    }

    public void setDaoArchivos(DAOProyectos daoArchivos) {
        this.daoArchivos = daoArchivos;
    }
    
    
    
}
