/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usss
 */
public class Preguntas implements Serializable {
    
    private static long serialVersionUID = 1L;
    
    private Integer idPregunta;
    private String preguntaOID;
    private String descripcionPregunta;
    private int orden;
    private String mensajesPregunta;
    private boolean activa;
    private boolean enabled;
    private boolean visible;
    private boolean dinamica;
    private boolean condicionada;
    private String variable;
    private int idTipoPregunta;
    private String miTipoPregunta;
    private int idSubCategoria;
    private String alerta;
    private boolean banco;
    public List<Listas> misListas = new ArrayList<Listas>();
    private List<Opciones> misOpciones = new ArrayList<Opciones>();
    private String seccionOID;
    
    private String seccionOIDSelect;//Campo para ser utilizado al seleccionar una seccion en el banco de preguntas.
    
    private int longitudMaxima;
    private int valorMaximo;
    private int valorMinimo;
    private int numeroDecimales;
    private String archivoImagen;
    private String valorAlerta;
    private boolean requerida;
    
    //Variables para campos adicionales de acuerdo al tipo de lista
    private Integer tipoOrdenId;
    private boolean seleccionadas;
    private String preguntasDominanteOID;
    private boolean otros;
    private Integer tipoListasOpcionesId;
    private Integer variableFiltro;
    private String listasFiltroOID;
    private boolean recorridoTotal;
    private boolean controlUsuario;
    private boolean codigoBarra;
    private boolean horientacionVertical;
    
    private String listaOID;
    
    //variables para el control de los checks de configuracion de opciones.
    private boolean habilitadoOk;
    private boolean deshabilitadoOk;
    
    /*P.PreguntasOID, --String
P.SeccionesOID, --String
P.Descripcion, --String
P.Orden, --tyint
P.Activa, --tyint
P.Enabled, --tyint
P.Visible, --tyint
P.Variable, --tyint
P.TiposPreguntasId, --tyint
P.SubCategoriasId,  --tyint
P.Mensajes,  --String
P.Alerta,  --String
P.Banco,  --Bit
P.Minimo,  --tyint
P.Maximo,  --tyint
P.NroDecimales,  --tyint
P.ArchivoImagen --String
    */
    /*    
    LP.TipoOrdenId, --tyint
    LP.Seleccionadas, --bit
    LP.PreguntasDominanteOID, --String
    LP.Otros, --bit
    LP.TipoListasOpcionesId, --smallint
    LP.Variable AS VariableFiltro,--String
    LP.ListasFiltroOID, --String
    LP.RecorridoTotal,  --bit
    LP.ControlUsuario,  --bit
    LP.CodigoBarra,  --bit
    LP.HorientacionVertical --bit
*/
    /*
    P.PreguntasOID, P.SeccionesOID, P.Descripcion, P.Orden, P.Activa, P.Enabled, P.Visible, P.Variable, P.TiposPreguntasId, P.SubCategoriasId, 
    P.Mensajes, P.Alerta, P.ArchivoImagen,
P.Banco, P.Minimo, P.Maximo, P.NroDecimales, LP.ListasOID, LP.TipoOrdenId, LP.Seleccionadas, LP.PreguntasDominanteOID, LP.Otros, LP.TipoListasOpcionesId, 
LP.ListasFiltroOID, LP.Variable AS VariableFiltro, LP.RecorridoTotal, LP.ControlUsuario, LP.CodigoBarra, LP.HorientacionVertical*/
    
    
    /**
     * @return the idPregunta
     */
    public Integer getIdPregunta() {
        return idPregunta;
    }

    /**
     * @param idPregunta the idPregunta to set
     */
    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    /**
     * @return the preguntaOID
     */
    public String getPreguntaOID() {
        return preguntaOID;
    }

    /**
     * @param preguntaOID the preguntaOID to set
     */
    public void setPreguntaOID(String preguntaOID) {
        this.preguntaOID = preguntaOID;
    }

    /**
     * @return the descripcionPregunta
     */
    public String getDescripcionPregunta() {
        return descripcionPregunta;
    }

    /**
     * @param descripcionPregunta the descripcionPregunta to set
     */
    public void setDescripcionPregunta(String descripcionPregunta) {
        this.descripcionPregunta = descripcionPregunta;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * @return the mensajesPregunta
     */
    public String getMensajesPregunta() {
        return mensajesPregunta;
    }

    /**
     * @param mensajesPregunta the mensajesPregunta to set
     */
    public void setMensajesPregunta(String mensajesPregunta) {
        this.mensajesPregunta = mensajesPregunta;
    }

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param activa the activa to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the dinamica
     */
    public boolean isDinamica() {
        return dinamica;
    }

    /**
     * @param dinamica the dinamica to set
     */
    public void setDinamica(boolean dinamica) {
        this.dinamica = dinamica;
    }

    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * @return the idTipoPregunta
     */
    public int getIdTipoPregunta() {
        return idTipoPregunta;
    }

    /**
     * @param idTipoPregunta the idTipoPregunta to set
     */
    public void setIdTipoPregunta(int idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }

    /**
     * @return the idSubCategoria
     */
    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    /**
     * @param idSubCategoria the idSubCategoria to set
     */
    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    /**
     * @return the alerta
     */
    public String getAlerta() {
        return alerta;
    }

    /**
     * @param alerta the alerta to set
     */
    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    /**
     * @return the banco
     */
    public boolean isBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(boolean banco) {
        this.banco = banco;
    }

    /**
     * @return the misListas
     */
    public List<Listas> getMisListas() {
        return misListas;
    }

    /**
     * @param misListas the misListas to set
     */
    public void setMisListas(List<Listas> misListas) {
        this.misListas = misListas;
    }

    /**
     * @return the misOpciones
     */
    public List<Opciones> getMisOpciones() {
        return misOpciones;
    }

    /**
     * @param misOpciones the misOpciones to set
     */
    public void setMisOpciones(List<Opciones> misOpciones) {
        this.misOpciones = misOpciones;
    }

    /**
     * @return the seccionOID
     */
    public String getSeccionOID() {
        return seccionOID;
    }

    /**
     * @param seccionOID the seccionOID to set
     */
    public void setSeccionOID(String seccionOID) {
        this.seccionOID = seccionOID;
    }

    /**
     * @return the longitudMaxima
     */
    public int getLongitudMaxima() {
        return longitudMaxima;
    }

    /**
     * @param longitudMaxima the longitudMaxima to set
     */
    public void setLongitudMaxima(int longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    /**
     * @return the valorMaximo
     */
    public int getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param valorMaximo the valorMaximo to set
     */
    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * @return the valorMinimo
     */
    public int getValorMinimo() {
        return valorMinimo;
    }

    /**
     * @param valorMinimo the valorMinimo to set
     */
    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    /**
     * @return the numeroDecimales
     */
    public int getNumeroDecimales() {
        return numeroDecimales;
    }

    /**
     * @param numeroDecimales the numeroDecimales to set
     */
    public void setNumeroDecimales(int numeroDecimales) {
        this.numeroDecimales = numeroDecimales;
    }

    public String getMiTipoPregunta() {
            return miTipoPregunta;
    }

    public void setMiTipoPregunta(String miTipoPregunta) {
            this.miTipoPregunta = miTipoPregunta;
    }

    public String getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(String archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public String getSeccionOIDSelect() {
        return seccionOIDSelect;
    }

    public void setSeccionOIDSelect(String seccionOIDSelect) {
        this.seccionOIDSelect = seccionOIDSelect;
    }

    public Integer getTipoOrdenId() {
        return tipoOrdenId;
    }

    public void setTipoOrdenId(Integer tipoOrdenId) {
        this.tipoOrdenId = tipoOrdenId;
    }

    public boolean isSeleccionadas() {
        return seleccionadas;
    }

    public void setSeleccionadas(boolean seleccionadas) {
        this.seleccionadas = seleccionadas;
    }

    public String getPreguntasDominanteOID() {
        return preguntasDominanteOID;
    }

    public void setPreguntasDominanteOID(String preguntasDominanteOID) {
        this.preguntasDominanteOID = preguntasDominanteOID;
    }

    public boolean isOtros() {
        return otros;
    }

    public void setOtros(boolean otros) {
        this.otros = otros;
    }

    public Integer getTipoListasOpcionesId() {
        return tipoListasOpcionesId;
    }

    public void setTipoListasOpcionesId(Integer tipoListasOpcionesId) {
        this.tipoListasOpcionesId = tipoListasOpcionesId;
    }

    public Integer getVariableFiltro() {
        return variableFiltro;
    }

    public void setVariableFiltro(Integer variableFiltro) {
        this.variableFiltro = variableFiltro;
    }

    public String getListasFiltroOID() {
        return listasFiltroOID;
    }

    public void setListasFiltroOID(String listasFiltroOID) {
        this.listasFiltroOID = listasFiltroOID;
    }

    public boolean isRecorridoTotal() {
        return recorridoTotal;
    }

    public void setRecorridoTotal(boolean recorridoTotal) {
        this.recorridoTotal = recorridoTotal;
    }

    public boolean isControlUsuario() {
        return controlUsuario;
    }

    public void setControlUsuario(boolean controlUsuario) {
        this.controlUsuario = controlUsuario;
    }

    public boolean isCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(boolean codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public boolean isHorientacionVertical() {
        return horientacionVertical;
    }

    public void setHorientacionVertical(boolean horientacionVertical) {
        this.horientacionVertical = horientacionVertical;
    }

    public String getListaOID() {
        return listaOID;
    }

    public void setListaOID(String listaOID) {
        this.listaOID = listaOID;
    }

    public boolean isHabilitadoOk() {
        return habilitadoOk;
    }

    public void setHabilitadoOk(boolean habilitadoOk) {
        this.habilitadoOk = habilitadoOk;
    }

    public boolean isDeshabilitadoOk() {
        return deshabilitadoOk;
    }

    public void setDeshabilitadoOk(boolean deshabilitadoOk) {
        this.deshabilitadoOk = deshabilitadoOk;
    }

    public boolean isCondicionada() {
        return condicionada;
    }

    public void setCondicionada(boolean condicionada) {
        this.condicionada = condicionada;
    }

    public String getValorAlerta() {
        return valorAlerta;
    }

    public void setValorAlerta(String valorAlerta) {
        this.valorAlerta = valorAlerta;
    }

    public boolean isRequerida() {
        return requerida;
    }

    public void setRequerida(boolean requerida) {
        this.requerida = requerida;
    }
}
