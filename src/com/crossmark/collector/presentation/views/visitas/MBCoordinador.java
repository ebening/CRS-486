package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.presentation.views.visitas.objects.Coordinador;
import com.crossmark.collector.business.services.ServiceCoordinador;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.SelectEvent;
import org.primefaces.context.RequestContext;

@ManagedBean
public class MBCoordinador {
    private ServiceCoordinador serviceCoordinador;
    
    private List<Coordinador> coordinadores;
    private Coordinador coordinadorSeleccionado;
    
    private String urlQuery;
    private int grupoSelectedId;
    private int territoriosId;
    private int regionesId;
    private int clienteId;
    private int unidadesNegociosId;
    private int equipoId;

    public MBCoordinador() {
    }
    
    public void initCoordinador(){
        Utileria.logger(this.getClass()).info("Obteniendo lista de coordinadores...");
        
        setCoordinadores(getServiceCoordinador().listaCoordinadores(getEquipoId(), getTerritoriosId()));
        
        for(Coordinador c : getCoordinadores())
        {
            if(c.isSelected())
            {
                setCoordinadorSeleccionado(c);
            }
        }
    }
    
    public void guardarCoordinador(){
        Utileria.logger(this.getClass()).info("Guardando coordinador...");
        
        if (getCoordinadorSeleccionado() != null){
            getServiceCoordinador().guardarCoordinador(getCoordinadorSeleccionado().getEquiposId(), getCoordinadorSeleccionado().getTerritoriosId(), getCoordinadorSeleccionado().getUsuariosOID());
        }else{
            Utileria.mensajeAlerta("Debe seleccionar un usuario para establecerlo como coordinador.");
        }
        
    }
    
    public void eliminarCoordinador(){
        Utileria.logger(this.getClass()).info("Eliminando coordinador...");
        
        if(getServiceCoordinador().guardarCoordinador(equipoId, territoriosId, null))
        {
            if(getCoordinadorSeleccionado() != null)
            {
                setCoordinadorSeleccionado(null);
            }
            Utileria.mensajeSatisfactorio("El corrdinador ha sido eliminado correctamente");
        }
        else
        {
            Utileria.mensajeErroneo("El coordinador no pudo ser eliminado.");
        }
    }
    
    public void processUrl(){
        Codificacion cs = new Codificacion();
        
        if(urlQuery != null){
            cs.proceso(urlQuery, true);
            Map<String,String> urlParameters = cs.obtenerParametros(cs.getResultado());
            for(Map.Entry<String,String> element : urlParameters.entrySet()){
                switch(element.getKey()){
                    case "RegionesId":
                        this.setRegionesId(Integer.parseInt(element.getValue()));
                        break;
                    case "TerritorioId":
                        this.setTerritoriosId(Integer.parseInt(element.getValue()));
                        break;
                    case "EquiposId":
                        this.setEquipoId(Integer.parseInt(element.getValue()));
                        break;
                    case "UnidadesNegocios":
                        this.setUnidadesNegociosId(Integer.parseInt(element.getValue()));
                        break;
                    case "ClienteId":
                        this.setClienteId(Integer.parseInt(element.getValue()));
                        break;
                    default:
                        this.setRegionesId(0);
                        this.setTerritoriosId(0);
                        this.setEquipoId(0);
                        this.setUnidadesNegociosId(0);
                        this.setClienteId(0);
                        break;
                }
            }
        }
    }

    public List<Coordinador> getCoordinadores() {
        return coordinadores;
    }

    public void setCoordinadores(List<Coordinador> coordinadores) {
        this.coordinadores = coordinadores;
    }

    public Coordinador getCoordinadorSeleccionado() {
        return coordinadorSeleccionado;
    }

    public void setCoordinadorSeleccionado(Coordinador coordinadorSeleccionado) {
        this.coordinadorSeleccionado = coordinadorSeleccionado;
    }
    
    public ServiceCoordinador getServiceCoordinador() {
        return serviceCoordinador;
    }

    public void setServiceCoordinador(ServiceCoordinador serviceCoordinador) {
        this.serviceCoordinador = serviceCoordinador;
    }

    public int getGrupoSelectedId() {
        return grupoSelectedId;
    }

    public void setGrupoSelectedId(int grupoSelectedId) {
        this.grupoSelectedId = grupoSelectedId;
    }

    public int getTerritoriosId() {
        return territoriosId;
    }

    public void setTerritoriosId(int territoriosId) {
        this.territoriosId = territoriosId;
    }

    public int getRegionesId() {
        return regionesId;
    }

    public void setRegionesId(int regionesId) {
        this.regionesId = regionesId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUnidadesNegociosId() {
        return unidadesNegociosId;
    }

    public void setUnidadesNegociosId(int unidadesNegociosId) {
        this.unidadesNegociosId = unidadesNegociosId;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }
    
    public String getUrlQuery() {
        return urlQuery;
    }

    public void setUrlQuery(String urlQuery) {
        this.urlQuery = urlQuery;
    }
}
