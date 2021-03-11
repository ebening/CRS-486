/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Catalogos;
import com.crossmark.collector.business.domain.Condiciones;
import com.crossmark.collector.business.domain.Encuestas;
import com.crossmark.collector.business.domain.Plantillas;
import com.crossmark.collector.business.domain.Preguntas;
import com.crossmark.collector.business.domain.Secciones;
import com.crossmark.collector.persistence.daos.DAOCondiciones;
import com.crossmark.collector.persistence.daos.DAOEncuestas;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usss
 */
public class ServiceEncuestasImpl implements ServiceEncuestas{
    
    private DAOEncuestas daoEncuestas;
    private DAOEncuestas daoCreaEncuesta;
    private DAOEncuestas daoGuardaEncuesta;
    private DAOEncuestas daoGuardaComoPlantilla;
    private DAOEncuestas daoTraePlantilla;
    private DAOEncuestas daoListaCatalogos;
    private DAOEncuestas daoListaPreguntaOrigen;
    private DAOEncuestas daoListaSecciones;
    private DAOEncuestas daoTraeEncuesta;
    private DAOEncuestas daoGuardaEncuestaComoPlantilla;
    private DAOEncuestas daoEliminaEncuesta;
    private DAOEncuestas daoEncuestaCondicionesJsonUps;
    private DAOCondiciones daoCondiciones;
    private DAOEncuestas daoListasPreguntasControlSel;
    /**
     * @return the daoEncuestas
     */
    public DAOEncuestas getDaoEncuestas() {
        return daoEncuestas;
    }

    /**
     * @param daoEncuestas the daoEncuestas to set
     */
    public void setDaoEncuestas(DAOEncuestas daoEncuestas) {
        this.daoEncuestas = daoEncuestas;
    }

    @Override
    public Integer creaEncuesta(int idTipoEncuesta, int idPlantilla) {
        return daoCreaEncuesta.creaEncuesta(idTipoEncuesta, idPlantilla);
    }

    /**
     * @return the daoCreaEncuestas
     */
    public DAOEncuestas getDaoCreaEncuesta() {
        return daoCreaEncuesta;
    }

    /**
     * @param daoCreaEncuesta the daoCreaEncuestas to set
     */
    public void setDaoCreaEncuesta(DAOEncuestas daoCreaEncuesta) {
        this.daoCreaEncuesta = daoCreaEncuesta;
    }

    @Override
    public Integer guardaEncuesta(Integer encuestasId,String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId, 
            boolean plantilla, boolean activa, Date fechaCreacion, String observacion, int unidadNeogico, boolean operacion) {
        
        return daoGuardaEncuesta.guardaEncuesta(encuestasId, nombreEncuesta,  claveEncuesta,  gpsEncuesta,  statusId,  plantilla,  activa,  fechaCreacion,  observacion,  unidadNeogico,operacion);
    }
    
    
        @Override
    public Integer guardaComoPlantilla(Integer idPlantilla, boolean esPlantilla, String nombre) {
        
        return daoGuardaComoPlantilla.guardaComoPlantilla(idPlantilla, esPlantilla, nombre);
    }

        @Override
    public List<Plantillas> traePlantilla(Integer idEncuestaPlantilla) {

        return daoTraePlantilla.traePlantilla(idEncuestaPlantilla);
    }
    
    
    /**
     * @return the daoGuardaEncuesta
     */
    public DAOEncuestas getDaoGuardaEncuesta() {
        return daoGuardaEncuesta;
    }

    /**
     * @param daoGuardaEncuesta the daoGuardaEncuesta to set
     */
    public void setDaoGuardaEncuesta(DAOEncuestas daoGuardaEncuesta) {
        this.daoGuardaEncuesta = daoGuardaEncuesta;
    }

    /**
     * @return the daoGuardaComoPlantilla
     */
    public DAOEncuestas getDaoGuardaComoPlantilla() {
        return daoGuardaComoPlantilla;
    }

    /**
     * @param daoGuardaComoPlantilla the daoGuardaComoPlantilla to set
     */
    public void setDaoGuardaComoPlantilla(DAOEncuestas daoGuardaComoPlantilla) {
        this.daoGuardaComoPlantilla = daoGuardaComoPlantilla;
    }

    /**
     * @return the daoTraePlantilla
     */
    public DAOEncuestas getDaoTraePlantilla() {
        return daoTraePlantilla;
    }

    /**
     * @param daoTraePlantilla the daoTraePlantilla to set
     */
    public void setDaoTraePlantilla(DAOEncuestas daoTraePlantilla) {
        this.daoTraePlantilla = daoTraePlantilla;
    }

    @Override
    public List<Catalogos> listaCatalogos(String listasOID, boolean catalogo,Integer idTipoLista) {
        return daoListaCatalogos.listaCatalogos(listasOID, catalogo,idTipoLista);
    }

    /**
     * @return the daoListaCatalogos
     */
    public DAOEncuestas getDaoListaCatalogos() {
        return daoListaCatalogos;
    }

    /**
     * @param daoListaCatalogos the daoListaCatalogos to set
     */
    public void setDaoListaCatalogos(DAOEncuestas daoListaCatalogos) {
        this.daoListaCatalogos = daoListaCatalogos;
    }

    @Override
    public List<Preguntas> listaPreguntaOrigen(String seccionPadreOID, Integer encuestaId, int ordenSeccion, int ordenPregunta) {
        return daoListaPreguntaOrigen.listaPreguntaOrigen(seccionPadreOID, encuestaId, ordenSeccion, ordenPregunta);
    }

    /**
     * @return the daoListaPreguntaOrigen
     */
    public DAOEncuestas getDaoListaPreguntaOrigen() {
        return daoListaPreguntaOrigen;
    }

    /**
     * @param daoListaPreguntaOrigen the daoListaPreguntaOrigen to set
     */
    public void setDaoListaPreguntaOrigen(DAOEncuestas daoListaPreguntaOrigen) {
        this.daoListaPreguntaOrigen = daoListaPreguntaOrigen;
    }

    @Override
    public List<Encuestas> listaEncuestas(int idEncuesta, String nombreEncuesta, String claveEncuesta, String gpsEncuesta, int statusId, boolean esPlantilla, boolean activa, Date fechaCreacionEncuesta, String observacionEncuesta, int proyectoId, int unidadesNegocioEncuestaId) {
        return daoEncuestas.listaEncuestas(idEncuesta, nombreEncuesta, claveEncuesta, gpsEncuesta, statusId, esPlantilla, activa, fechaCreacionEncuesta, observacionEncuesta, proyectoId, unidadesNegocioEncuestaId);
    }

    /**
     * @return the daoListaSecciones
     */
    public DAOEncuestas getDaoListaSecciones() {
        return daoListaSecciones;
    }

    /**
     * @param daoListaSecciones the daoListaSecciones to set
     */
    public void setDaoListaSecciones(DAOEncuestas daoListaSecciones) {
        this.daoListaSecciones = daoListaSecciones;
    }

    @Override
    public List<Secciones> listaSecciones(Integer idEncuestaPlantilla) {
        return daoTraePlantilla.listaSecciones(idEncuestaPlantilla);
    }

    @Override
    public Encuestas traerEncuesta(Integer idEncuestaPlantilla) {
            // TODO Auto-generated method stub
            return daoTraeEncuesta.traerEncuesta(idEncuestaPlantilla);
    }
        
    public Encuestas traerEncuestaSeccionesPregunta(Integer encuestasId, String preguntasOID){
        return daoEncuestaCondicionesJsonUps.traerEncuestaSeccionesPregunta(encuestasId, preguntasOID);
    }

    public DAOEncuestas getDaoTraeEncuesta() {
        return daoTraeEncuesta;
    }

    public void setDaoTraeEncuesta(DAOEncuestas daoTraeEncuesta) {
        this.daoTraeEncuesta = daoTraeEncuesta;
    }

    /**
     * @return the daoGuardaEncuestaComoPlantilla
     */
    public DAOEncuestas getDaoGuardaEncuestaComoPlantilla() {
        return daoGuardaEncuestaComoPlantilla;
    }

    /**
     * @param daoGuardaEncuestaComoPlantilla the daoGuardaEncuestaComoPlantilla to set
     */
    public void setDaoGuardaEncuestaComoPlantilla(DAOEncuestas daoGuardaEncuestaComoPlantilla) {
        this.daoGuardaEncuestaComoPlantilla = daoGuardaEncuestaComoPlantilla;
    }

    @Override
    public void guardaEncuestaComoPlantilla(Integer idEncuesta, String nombrePlantilla, boolean esPlantilla) {
        daoGuardaEncuestaComoPlantilla.guardaEncuestaComoPlantilla(idEncuesta, nombrePlantilla, esPlantilla);
    }

    public DAOEncuestas getDaoEliminaEncuesta() {
        return daoEliminaEncuesta;
    }

    public void setDaoEliminaEncuesta(DAOEncuestas daoEliminaEncuesta) {
        this.daoEliminaEncuesta = daoEliminaEncuesta;
    }

    @Override
    public String eliminaEncuesta(Integer idEncuesta) {
        return daoEliminaEncuesta.eliminaEncuesta(idEncuesta);
    }
    
    public DAOEncuestas getDaoEncuestaCondicionesJsonUps() {
        return daoEncuestaCondicionesJsonUps;
    }

    public void setDaoEncuestaCondicionesJsonUps(DAOEncuestas daoEncuestaCondicionesJsonUps) {
        this.daoEncuestaCondicionesJsonUps = daoEncuestaCondicionesJsonUps;
    }

    public DAOCondiciones getDaoCondiciones() {
        return daoCondiciones;
    }

    public void setDaoCondiciones(DAOCondiciones daoCondiciones) {
        this.daoCondiciones = daoCondiciones;
    }
    
    @Override
    public List<Condiciones> getListCondiciones(String seccionesOID, String preguntasOID, String opcionesOID, Boolean seleccionadas){
        return this.daoCondiciones.getListCondiciones(seccionesOID, preguntasOID, opcionesOID, seleccionadas);
    }
    
    @Override
    public void crearCondicion(Condiciones o){
        this.daoCondiciones.crearCondicion(o);
    }
    
    @Override
    public String eliminarCondicion(Condiciones o){
        return this.daoCondiciones.eliminarCondicion(o);
    }
    
    @Override
    public List<Preguntas> getListasPreguntasControl(Integer encuestasId, Integer ordenSeccion){
        return daoListasPreguntasControlSel.getListasPreguntasControl(encuestasId, ordenSeccion);
    }

    public DAOEncuestas getDaoListasPreguntasControlSel() {
        return daoListasPreguntasControlSel;
    }

    public void setDaoListasPreguntasControlSel(DAOEncuestas daoListasPreguntasControlSel) {
        this.daoListasPreguntasControlSel = daoListasPreguntasControlSel;
    }
    
}
