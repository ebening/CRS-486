package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceCatalogos;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.crossmark.collector.business.services.ServiceRutas;
import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import com.crossmark.collector.presentation.views.visitas.objects.Ruta;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.io.Serializable;
import java.util.HashMap;
import org.primefaces.context.RequestContext;

//@ManagedBean
public class MBRutas implements Serializable{
	private ServiceRutas serviceRutas;
        private ServiceTerritorios serviceTerritorios;
        private ServiceCatalogos serviceCatalogos;
        
	private Territorio territorioSeleccionado;
	
        private String urlQuery;
	private String rutaSelec;
	private Integer rutaId;
	private String nombreRuta;
        private String territorioNombre;
        private String nombreEquipo;
	private int territorioId;
        private int EquiposId;
        private int UnidadesNegociosId;
        private int ClienteId;
        private int RegionesId;
        
	private List<Tienda> tiendas;
        private List<Tienda> tiendasAll;
	private List<Promotor> promotores;
        private List<Promotor> promotoresAll;
        private List<Ruta> listaRutas;

	private List<Ruta> rutaSelected;
	private boolean editarRuta = false;
        
        private List<Promotor> promotoresFinal;
        private List<Tienda> tiendasFinal;
	
    public MBRutas(){
		
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
                        this.setTerritorioId(Integer.parseInt(element.getValue()));
                        break;
                    case "EquiposId":
                        this.setEquiposId(Integer.parseInt(element.getValue()));
                        break;
                    case "UnidadesNegocios":
                        this.setUnidadesNegociosId(Integer.parseInt(element.getValue()));
                        break;
                    case "ClienteId":
                        this.setClienteId(Integer.parseInt(element.getValue()));
                        break;
                    default:
                        this.setRegionesId(0);
                        this.setTerritorioId(0);
                        this.setEquiposId(0);
                        this.setUnidadesNegociosId(0);
                        this.setClienteId(0);
                        break;
                }
            }
        }
    }
    
    public void RutasListByTerritorio() {
        territorioNombre = getServiceTerritorios().nombreTerritorio(territorioId);
        Equipo eq = getServiceCatalogos().getEquipoById(EquiposId);
        nombreEquipo = eq.getNombre();
    	listaRutas = parseRutaObject(getServiceRutas().listaRutasXTerritorio(territorioId, EquiposId));
    }
    
    public static List<Ruta> parseRutaObject(List<Map<String, Object>> lista) {
    	List<Ruta> lr = new ArrayList<>();
    	if(!lista.isEmpty()){
          for(Map m : lista) {
             Ruta r;
              if(m.get("RutasTempId") == null){
                  r = new Ruta(
                          String.valueOf(m.get("Nombre")),
                          Integer.valueOf(String.valueOf(m.get("RutasId"))),
                          Integer.valueOf(String.valueOf(m.get("TerritoriosId"))),
                          Integer.valueOf(String.valueOf(m.get("EquiposId"))));
              }else{
                    r = new Ruta(String.valueOf(m.get("Rutas") == null ? m.get("Nombre") : m.get("Rutas") ),
    				String.valueOf(m.get("Promotores") == null ? "0" : m.get("Promotores")),
    				String.valueOf(m.get("Tiendas") == null ? "0" : m.get("Tiendas")));
                    // r.setNombre(String.valueOf(m.get("Nombre") == null ? "0" : m.get("Nombre")));

                     if(m.get("RutasTempId") != null){
                         r.setId(Integer.valueOf(String.valueOf(m.get("RutasTempId"))));
                     }
                     else if(m.get("RutasId") != null){
                         r.setId(Integer.valueOf(String.valueOf(m.get("RutasId"))));
                     }
              }
                lr.add(r);
            }  
        }
        return lr;
    }
    
    public void checkSaveData(){
        boolean pass = true;
        if(nombreRuta.equals("")){
            pass = false;
            Utileria.mensajeAlerta("Capture el nombre de la ruta.");
        }
        if(tiendas == null || tiendas.isEmpty()){
            pass = false;
            Utileria.mensajeAlerta("Seleccione al menos una tienda para la ruta.");
        }
        if(promotores == null || promotores.isEmpty()){
            pass = false;
            Utileria.mensajeAlerta("Seleccione al menos un promotor para la ruta.");
        }
        if(pass){
            guardarRuta();
        }
    }
    
    public void guardarRuta (){
        HashMap<String, Object> rTiendas;
        HashMap<String, Object> rPromo;
        int lastRutaId;
        HashMap<String, Object> r = getServiceRutas()
                .guardaRuta(territorioId, nombreRuta, EquiposId,editarRuta ? rutaSelected.get(0).getId() : 0);
        
        if((boolean)r.get("Exito")){
            lastRutaId = editarRuta ? rutaSelected.get(0).getId() : (int) r.get("RutaId");
            if(editarRuta){
                rutaSelected.get(0).setNombre(nombreRuta);
                actualizarTiendas(lastRutaId);
                actualizaPromotores(lastRutaId);
            }else{
                rTiendas = getServiceRutas().guardaTiendasRutas(lastRutaId, tiendas);
                rPromo = getServiceRutas().guardaPromotoresRutas(lastRutaId, promotores);
                if(!(boolean)rTiendas.get("Exito") || (!(boolean)rPromo.get("Exito"))){
                    Utileria.mensajeErroneo("Error al guardar Tiendas y/o Promotores");
                }
                Ruta rt = new Ruta();
                rt.setNombre(nombreRuta);
                rt.setId(lastRutaId);
                rt.setPromotores(promotores);
                rt.setTiendas(tiendas);
                listaRutas.add(rt);
            }
            Utileria.mensajeSatisfactorio("La ruta ha sido guardada correctamente.");
            RequestContext ctxt = RequestContext.getCurrentInstance();
            ctxt.execute("PF('editarDialog').hide()");
        }else{
            Utileria.mensajeErroneo("Error al guardar Ruta",(String)r.get("Msj"));
        }
    }
    
    private void actualizarTiendas(int idRuta){
        List<Tienda> joker = new ArrayList<>(tiendas);
        tiendasFinal = new ArrayList<>(rutaSelected.get(0).getTiendas());
        for(Tienda r : tiendasFinal){
            joker.remove(r);
        }
        if(!joker.isEmpty()){
            HashMap<String, Object> r = getServiceRutas().guardaTiendasRutas(idRuta, joker);
            System.out.println("Response -> " + r);
        }
        
        joker = new ArrayList<>(tiendasFinal);
        joker.removeAll(tiendas);
        if(!joker.isEmpty()){
            for(Tienda t : joker){
                HashMap<String, Object> r = getServiceRutas().eliminaRutasTiendas(idRuta, t.getId());
                System.out.println("Elimina Tienda " + t.getId() + " -> " + r );
            }
        }
        rutaSelected.get(0).setTiendas(tiendas);
    }
    
    private void actualizaPromotores(int idRuta){
        List<Promotor> joker = new ArrayList<>(promotores);
        for(Promotor p : promotoresFinal){
            joker.remove(p);
        }
        if(!joker.isEmpty()){
            HashMap<String, Object> r = getServiceRutas().guardaPromotoresRutas(idRuta, joker);
            System.out.println("Agrega Promotor -> " + r);
        }
        joker = new ArrayList<>(promotoresFinal);
        joker.removeAll(promotores);
        if(!joker.isEmpty()){
            for(Promotor p : joker){
                HashMap<String, Object> r = getServiceRutas().eliminaRutasUsuario(idRuta, p.getOID());
                System.out.println("Elimina Promotor -> " + r);
            }
        }
        rutaSelected.get(0).setPromotores(promotores);
    }
    
    public void eliminarRuta(){
        if(rutaSelected != null && !rutaSelected.isEmpty()){
            //confirmDelete();
            RequestContext.getCurrentInstance().execute("PF('deleteConfirmation').show();");
        }else{
            Utileria.mensajeAlerta("Para eliminar debe seleccionar al menos una ruta.");
        } 
    }
    
    public void confirmDelete(){
        RequestContext.getCurrentInstance().execute("PF('deleteConfirmation').hide();");
        boolean exito = true;
        String msj = "";
        for(Ruta ruta : rutaSelected){
            HashMap<String, Object> r = getServiceRutas().eliminaRuta(ruta.getId());
            if((boolean)r.get("Exito")){
                listaRutas.remove(ruta);
                //Utileria.mensajeSatisfactorio(r.get("Msj"));
            }else{
                exito = false;
                msj = msj + "Ruta: " + ruta.getNombre() + " -> " + r.get("Msj");
               // Utileria.mensajeErroneo(r.get("Msj"));
            }
        }
        if(exito){
            Utileria.mensajeSatisfactorio("Las rutas han sido eliminadas del territorio.");
        }else{
            Utileria.mensajeErroneo("Error al eliminar rutas", msj);
        }
        rutaSelected = new ArrayList<>();
    }
    
    public void showEditDialog(){
        if(rutaSelected == null || rutaSelected.size() > 1 || rutaSelected.isEmpty()){
            Utileria.mensajeAlerta("Para editar debe seleccionar sólo una ruta.");
        }else{
            nombreRuta = rutaSelected.get(0).getNombre();
            editarRuta = true;
            initListaTiendas();
            initListaPromotores();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('editarDialog').show();");  
        }   
    }
    
    public void showNewDialog(){
        resetRutaNombre();
        initListaTiendas();
        initListaPromotores();
        editarRuta = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('editarDialog').show();"); 
    }
    
    public void initListaTiendas(){
        tiendasAll = new ArrayList<>();
        tiendas = new ArrayList<>();
        List<Tienda> r = MBTiendas.parseTiendaObject(getServiceRutas().listaTiendasXRuta(rutaSelected == null ? 0 : rutaSelected.get(0).getId(), 
                territorioId, UnidadesNegociosId, EquiposId));

        if(rutaSelected != null){
            List<Map<String, Object>> lr = getServiceRutas().listaTiendasVal(rutaSelected.get(0).getId());
        }
        
        for(Tienda t : r){
            if(t.isPertenece()){
                tiendas.add(t);
            }
            if(t.isEnable() && !tiendasAll.contains(t)){
                tiendasAll.add(t);
            }
        }
        tiendasFinal = new ArrayList<>(tiendas);
    }
    
    public void initListaPromotores(){
//        promotoresAll = new ArrayList<>();
        promotores = new ArrayList<>();
//        List<Promotor> r = MBPromotores.parsePromotorObject(getServiceRutas().listaUsuariosXRuta(rutaSelected == null ? 0 : rutaSelected.get(0).getId(),
//                territorioId, UnidadesNegociosId, EquiposId));
        
        promotoresAll = MBPromotores.parsePromotorObject(getServiceRutas().listaUsuariosXRuta(rutaSelected == null ? 0 : rutaSelected.get(0).getId(),
                territorioId, UnidadesNegociosId, EquiposId));
        
        if(rutaSelected != null){
            List<Map<String, Object>> lr = getServiceRutas().listaUsuariosVal(rutaSelected.get(0).getId());
        }
        
        for(Promotor p : promotoresAll){
            if(p.isPertenece()){
                promotores.add(p);
            }
//            if(p.isUnselect() && !promotoresAll.contains(p)){
//                promotoresAll.add(p);
//            }
        }
        
        promotoresFinal = new ArrayList<>(promotores);
    }
    
    public void isPromotorSelectable(){
        for(Promotor p : promotores){
            if(p.isUnselect()){
                String msj = "El promotor no se puede seleccionar porque forma parte de la ruta " + p.getRuta()
                        + " y del equipo " + p.getEquipo();
                Utileria.mensajeAlerta(msj);
                promotores.remove(p);
                RequestContext.getCurrentInstance().update("center-form:tablePromotores");
                break;
            }
        }
    }
    
    public void resetRutaNombre(){
        nombreRuta = "";
        tiendas = new ArrayList<>();
        promotores = new ArrayList<>();
        rutaSelected = null;
    }
    
    public ServiceRutas getServiceRutas() {
	return serviceRutas;
    }
	
    public void setServiceRutas(ServiceRutas serviceRutas) {
	this.serviceRutas = serviceRutas;
    }

    public String getRutaSelec() {
	return rutaSelec;
    }

    public void setRutaSelec(String rutaSelec) {
	this.rutaSelec = rutaSelec;
    }

    public Integer getRutaId() {
	return rutaId;
    }

    public void setRutaId(Integer rutaId) {
	this.rutaId = rutaId;
    }

    public Territorio getTerritorioSeleccionado() {
        return territorioSeleccionado;
    }
	
    public void setTerritorioSeleccionado(Territorio territorioSeleccionado) {
	this.territorioSeleccionado = territorioSeleccionado;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public List<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public List<Promotor> getPromotores() {
        return promotores;
    }

    public void setPromotores(List<Promotor> promotores) {
        this.promotores = promotores;
    }

    public List<Ruta> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(List<Ruta> listaRutas) {
        this.listaRutas = listaRutas;
    }

    public List<Ruta> getRutaSelected() {
        return rutaSelected;
    }

    public void setRutaSelected(List<Ruta> rutaSelected) {
        this.rutaSelected = rutaSelected;
    }

    public List<Tienda> getTiendasAll() {
        return tiendasAll;
    }

    public void setTiendasAll(List<Tienda> tiendasAll) {
        this.tiendasAll = tiendasAll;
    }

    public List<Promotor> getPromotoresAll() {
        return promotoresAll;
    }

    public void setPromotoresAll(List<Promotor> promotoresAll) {
        this.promotoresAll = promotoresAll;
    }

    public int getTerritorioId() {
        return territorioId;
    }

    public void setTerritorioId(int TerritorioId) {
        this.territorioId = TerritorioId;
    }

    public int getEquiposId() {
        return EquiposId;
    }

    public void setEquiposId(int EquiposId) {
        this.EquiposId = EquiposId;
    }

    public int getUnidadesNegociosId() {
        return UnidadesNegociosId;
    }

    public void setUnidadesNegociosId(int UnidadesNegociosId) {
        this.UnidadesNegociosId = UnidadesNegociosId;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int ClienteId) {
        this.ClienteId = ClienteId;
    }

    public int getRegionesId() {
        return RegionesId;
    }

    public void setRegionesId(int RegionesId) {
        this.RegionesId = RegionesId;
    }

    public String getTerritorioNombre() {
        return territorioNombre;
    }

    public void setTerritorioNombre(String territorioNombre) {
        this.territorioNombre = territorioNombre;
    }

    public ServiceTerritorios getServiceTerritorios() {
        return serviceTerritorios;
    }

    public void setServiceTerritorios(ServiceTerritorios serviceTerritorios) {
        this.serviceTerritorios = serviceTerritorios;
    }

    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getUrlQuery() {
        return urlQuery;
    }

    public void setUrlQuery(String urlQuery) {
        this.urlQuery = urlQuery;
    }
    
    
}
