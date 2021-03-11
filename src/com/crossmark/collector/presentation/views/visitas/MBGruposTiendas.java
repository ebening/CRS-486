/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceGruposTiendas;
import com.crossmark.collector.business.services.ServiceTiendas;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.GrupoTiendas;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jdominguez
 */

@ManagedBean
public class MBGruposTiendas {
    
    private ServiceTiendas serviceTiendas;
    private ServiceGruposTiendas serviceGruposTiendas;
    
    private List<GrupoTiendas> grupos;
    private List<Tienda> tiendas;
    private List<Tienda> tiendasSelected;
    private List<Tienda> tiendasFiltered;
    
    private int grupoSelectedId;
    private int territoriosId;
    private int regionesId;
    private int clienteId;
    private int unidadesNegociosId;
    private int equipoId;
    
    private String nuevoNombre="";
    private String urlQuery;
    
    private final HashMap<String, Object> filtros = MBTiendas.getFiltros();
    
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
    
    public void guardarEstado(){
        
        if(grupoSelectedId == 0){
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('crearGrupoDialog').show();");
        }else{
            guardarTiendas(grupoSelectedId);
        }
    }
    
    private void guardarTiendas(int gpSelected){
        for(GrupoTiendas gp : grupos){
                if(gp.getId() == gpSelected){
                    boolean exito = true;
                    List<Tienda> selected = new ArrayList<>(tiendasSelected);
                    List<Tienda> actual = new ArrayList<>(gp.getTiendas());
                    for(Tienda t : gp.getTiendas()){
                        selected.remove(t);
                    }
                  //  selected.removeAll(gp.getTiendas());
                    if(!selected.isEmpty()){
                        for(Tienda t : selected){
                            HashMap<String, Object> r = getServiceGruposTiendas().gruposTiendasUps(gpSelected, t.getId());
                            if((boolean) r.get("Exito")){
                                gp.getTiendas().add(t);
                            }else{
                                exito = false;
                            }  
                        }
                    }
                    actual.removeAll(tiendasSelected);
                    if(!actual.isEmpty()){
                        for(Tienda t : actual){
                            HashMap<String, Object> r = getServiceGruposTiendas().gruposTiendasDel(gpSelected, t.getId());
                            if((boolean)r.get("Exito")){
                                gp.getTiendas().remove(t);
                            }else{
                                exito = false;
                            }
                        }
                    }
                    tiendasSelected = gp.getTiendas();
                    if(exito){
                        Utileria.mensajeSatisfactorio("El grupo de tiendas ha sido actualizado.");
                    }else{
                        Utileria.mensajeErroneo("Error al actualizar el grupo de tiendas");
                    }
                    break;
                }
            }
    }
    
    public void crearNuevoGrupo(){
        if(!nuevoNombre.equals("")){
            RequestContext.getCurrentInstance().execute("PF('crearGrupoDialog').hide();");
            HashMap r = getServiceGruposTiendas().gruposUps(0, nuevoNombre);
            if((boolean)r.get("Exito")){
                GrupoTiendas gpn = new GrupoTiendas((int) r.get("Id"), nuevoNombre);
                grupos.add(gpn);
                if(!tiendasSelected.isEmpty()){
                    guardarTiendas(gpn.getId());
                }
                grupoSelectedId = gpn.getId();
                nuevoNombre = "";
                Utileria.mensajeSatisfactorio("El grupo de tiendas ha sido creado correctamente.");
            }else{
                nuevoNombre = "";
                Utileria.mensajeErroneo("Error al Crear Nuevo Grupo \n" + r.get("Msj"));
            }
        }else{
            Utileria.mensajeAlerta("Capture el nombre del grupo.");
        }
    }
    
    public void resetNombre(){
        nuevoNombre = "";
        RequestContext.getCurrentInstance().execute("PF('crearGrupoDialog').show()");
    }
    
    public void initTiendas(){
        filtros.put("TerritoriosId", territoriosId);
        tiendas = MBTiendas.parseTiendaObject(getServiceTiendas().getTiendasList(filtros));
    }

    public void listaGrupos(){
        //0, null, clienteId, unidadesNegociosId
        grupos = parseGrupoObject(getServiceGruposTiendas().listaGrupos(0, null, 0, 0));
    }
    
    public void listaTiendasByGrupoId(){
        System.out.println("Grupo Selected: -> " + grupoSelectedId);
        if(grupoSelectedId != 0){
            tiendasSelected = MBTiendas.parseTiendaObject(getServiceGruposTiendas().listaTiendasByGrupoId(grupoSelectedId));
            for(GrupoTiendas gp : grupos){
                if(gp.getId() == grupoSelectedId){
                    gp.setTiendas(tiendasSelected);
                }
            }
        }else{
            tiendasSelected = new ArrayList<>();
        }
    }
    
    public static List<GrupoTiendas> parseGrupoObject(List<Map<String, Object>> l){
        List<GrupoTiendas> r = new ArrayList<>();
        if(l != null && !l.isEmpty()){
            for(Map m : l){
                int idgrupo;
                if(m.get("GruposId") == null){
                    idgrupo = (short)m.get("GruposProyectosId");  
                }else{
                    idgrupo = Integer.valueOf(String.valueOf(m.get("GruposId")));
                }
                GrupoTiendas gt = new GrupoTiendas(idgrupo, String.valueOf(m.get("Nombre")));
                r.add(gt);
            }
        }
        return r;
    }
    
// ******************************* Getters & Setters ************************* //
    
    public ServiceGruposTiendas getServiceGruposTiendas() {
        return serviceGruposTiendas;
    }

    public void setServiceGruposTiendas(ServiceGruposTiendas serviceGruposTiendas) {
        this.serviceGruposTiendas = serviceGruposTiendas;
    }

    public List<GrupoTiendas> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoTiendas> grupos) {
        this.grupos = grupos;
    }

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public List<Tienda> getTiendasSelected() {
        return tiendasSelected;
    }

    public void setTiendasSelected(List<Tienda> tiendasSelected) {
        this.tiendasSelected = tiendasSelected;
    }

    public ServiceTiendas getServiceTiendas() {
        return serviceTiendas;
    }

    public void setServiceTiendas(ServiceTiendas serviceTiendas) {
        this.serviceTiendas = serviceTiendas;
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

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
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

    public List<Tienda> getTiendasFiltered() {
        return tiendasFiltered;
    }

    public void setTiendasFiltered(List<Tienda> tiendasFiltered) {
        this.tiendasFiltered = tiendasFiltered;
    }

    public String getUrlQuery() {
        return urlQuery;
    }

    public void setUrlQuery(String urlQuery) {
        this.urlQuery = urlQuery;
    }
}
