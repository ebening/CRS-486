package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceRutas;
import com.crossmark.collector.business.services.ServiceTerritorios;
import com.crossmark.collector.business.services.ServiceVisitas;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.utils.Codificacion;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import com.crossmark.collector.presentation.views.visitas.objects.Ruta;
import com.crossmark.collector.presentation.views.visitas.objects.Visita;
import com.crossmark.collector.presentation.views.visitas.objects.VisitaTienda;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;


/**
 *
 * @author jdominguez
 */
@ManagedBean
public class MBVisitas implements Serializable {
    private ServiceVisitas serviceVisitas;
    private ServiceRutas serviceRutas;
    private ServiceTerritorios serviceTerritorios;
    private ServiceCatalogos serviceCatalogos;
    
    private final Date today = new Date();
    
    private List<Visita> visitas;
    private List<Ruta> rutas;
    private List<Promotor> promotores;
    private List<VisitaTienda> visitasTiendas;
    private List<VisitaTienda> visitasTiendasSelected;
    private List<VisitaTienda> visitasFiltered;

   
    
    private Visita visitaSelected;
    private int rutaSelected;
    private String promotorSelected;
    private String nombreTerritorio;
    private String nombreVisita = "";
    private String nombreEquipo;
    private Date fechaVisita;
    
    private int TiendaId;
    private int TerritorioId;
    private int EquiposId;
    private int UnidadesNegociosId;
    private int ClienteId;
    private int RegionesId;
    
    private String urlQuery;
    
    public MBVisitas(){

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
    
    public void checkSaveData(){
        boolean save = true;
        if(nombreVisita.equals("")){
            save = false;
            Utileria.mensajeAlerta("Capture el nombre de la visita.");
        }
        if(rutaSelected == 0){
            save = false;
            Utileria.mensajeAlerta("Seleccione una ruta.");
        }
        if(fechaVisita == null){
            save = false;
            Utileria.mensajeAlerta("Ingrese la fecha de la visita.");
        }
        if(promotorSelected.equals("")){
            save = false;
            Utileria.mensajeAlerta("Seleccione el promotor que va a realizar la visita.");
        }
        if(visitasTiendasSelected.isEmpty() || visitasTiendasSelected == null){
            save = false;
            Utileria.mensajeAlerta("Seleccione al menos un proyecto para la visita.");
        }
        if(save){
            guardarVisita();
            visitasByTerritorioId();
            visitaSelected = new Visita();
            RequestContext.getCurrentInstance().execute("PF('nvDialog').hide()");
        }
    }
    
    public void guardarVisita(){
        HashMap<String, Object> r;
        boolean isAct = false;
        if(visitaSelected == null){
           r= getServiceVisitas().guardaVisita(0, fechaVisita, rutaSelected, nombreVisita, promotorSelected); 
        }else{
            isAct = true;
            r=getServiceVisitas().guardaVisita(visitaSelected.getVisitasId(), fechaVisita, rutaSelected, nombreVisita, promotorSelected);
        }
        if((boolean) r.get("Exito")){
            //Utileria.mensajeSatisfactorio(r.get("Msj"));
            int insertedId = (int)r.get("VisitaId");
            boolean exito;
            String nombrePromotor = "";
            for(Promotor p : promotores){
                if(p.getOID().equals(promotorSelected)){
                    nombrePromotor = p.getNombre() + " " + p.getApellidoPaterno() + " " + p.getApellidoMaterno();
                }
            }
            if(!isAct){
                exito = guardarProyectosTiendas(insertedId, nombrePromotor, visitasTiendasSelected);
            }else{
                exito = actProyectosTiendas(insertedId, nombrePromotor);
            }
            
            if(exito){ 
                Utileria.mensajeSatisfactorio("La visita ha sido guardada correctamente.");
            }else{
                Utileria.mensajeErroneo("Error al guardar la visita");
            }
        }else{
            Utileria.mensajeErroneo(r.get("Msj"));
        }
    }
    
    public boolean actProyectosTiendas(int insertedId, String nombrePromotor){
        boolean exito = true;
        visitaSelected.setPromotor(nombrePromotor);
        List<VisitaTienda> actual = visitaSelected.getVisitasTiendas();
        List<VisitaTienda> selected = new ArrayList<>(visitasTiendasSelected);
        for(VisitaTienda vt : actual){
            selected.remove(vt);
        }
        if(!selected.isEmpty()){
            exito = guardarProyectosTiendas(insertedId, nombrePromotor, selected);
        }
        actual.removeAll(visitasTiendasSelected);
        if(!actual.isEmpty()){
            for(VisitaTienda vt : actual){
                HashMap<String, Object> r = getServiceVisitas().eliminaVisita(vt.getTareasId());
                if(!((boolean)r.get("Exito"))){
                    exito = false;
                }
            }
        }
        return exito;
    }
    
    private void removeVisitaDeleted(){
        
    }
    
    public boolean guardarProyectosTiendas(int insertedId, String nombrePromotor, List<VisitaTienda> seleccion){
        boolean exito = true;
        String msjFailed = "";
        for (VisitaTienda vt : seleccion){
            HashMap<String, Object> ret = getServiceVisitas()
                        .guardaTiendasVisitas(insertedId, vt.getTiendasId(), vt.getProyectosId(), 0);
            if(!(boolean) ret.get("Exito")){
                exito = false;
                msjFailed = msjFailed + "Tienda no agregada: " + vt.getNombre() + "\n";
            }else{
                String nRuta = "";
                for(Ruta r : rutas){
                    if(r.getId() == rutaSelected){
                        nRuta = r.getNombre();
                    }
                }
                Visita v = new Visita();
                v.setVisitas(nombreVisita);
                v.setFechaIni(fechaVisita);
                v.setPromotor(nombrePromotor);
                v.setRutasId(rutaSelected);
                v.setTiendas(vt.getNombre());
                v.setProyectos(vt.getDescripcion());
                v.setRuta(nRuta);
                visitas.add(v);
            }
        }
        if(!exito){
            Utileria.mensajeErroneo("Error al guardar proyectos",msjFailed);
        }
        return exito;
    }
    
    public void eliminaVisita(){
        if(visitaSelected != null){
            if(confirmEdit()){
                RequestContext.getCurrentInstance().execute("PF('confirmDelete').show()");
            }
        }else{
            Utileria.mensajeAlerta("Para eliminar debe seleccionar al menos una visita.");
        }
    }
    
    public void confirmDelete(){
        RequestContext.getCurrentInstance().execute("PF('confirmDelete').hide()");
        HashMap<String, Object> r = getServiceVisitas().eliminaVisita(visitaSelected.getTareasId());
        if((boolean) r.get("Exito")){
            visitas.remove(visitaSelected);
            visitaSelected = null;
            Utileria.mensajeSatisfactorio("Las visitas han sido eliminadas del territorio.");
        }
        else{
            Utileria.mensajeErroneo(r.get("Msj"));
        } 
    }

    public void initListaPromotores(){
        if(rutaSelected != 0){
        //    promotores = MBPromotores.parsePromotorObject(getServiceRutas()
        //            .listaUsuariosXRuta(rutaSelected.getId(), TerritorioId, UnidadesNegociosId, EquiposId));
            
            promotores = MBPromotores.parsePromotorObject(getServiceVisitas()
                    .listaRutasUsuariosSel(rutaSelected, TerritorioId, EquiposId, UnidadesNegociosId));
            
            visitasRutasTiendas();
        }else{
            promotores = new ArrayList<>();
            visitasTiendas = new ArrayList<>();
        }
    }
    
    public void listaRutasSel(){
     //   rutas = MBRutas.parseRutaObject(getServiceRutas().listaRutasSel(0, null, TerritorioId, 1 ));
        rutas = MBRutas.parseRutaObject(getServiceVisitas().listaRutasSel(TerritorioId, EquiposId));
        if(visitaSelected != null){
            rutaSelected = visitaSelected.getRutasId();
            initListaPromotores();
            promotorSelected = visitaSelected.getUsuariosOID();
            RequestContext.getCurrentInstance().update("center-form:comboPromotor");
            RequestContext.getCurrentInstance().update("center-form:tableProyectosNV");
        }
    }
    
    public void visitasByTerritorioId(){
        nombreTerritorio = getServiceTerritorios().nombreTerritorio(TerritorioId);
        Equipo eq = getServiceCatalogos().getEquipoById(EquiposId);
        nombreEquipo = eq.getNombre();
        visitas = parseVisitaObject(getServiceVisitas().visitasByTerritorio(TerritorioId, EquiposId, UnidadesNegociosId));
    }
    
    public void getVisitasByTiendaId(){
        //TiendaId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("TiendaId"));
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String tid = params.get("TiendaId") != null ? params.get("TiendaId") : "";
        setVisitas(parseVisitaObject(getServiceVisitas().getVisitasByIdTienda(tid.equals("") ? 0 : Integer.valueOf(tid))));
    }
    
    public void visitasRutasTiendas(){
        List<VisitaTienda> temp = parseVisitaTiendaObject(getServiceVisitas().visitasTiendas(
                rutaSelected, 
                visitaSelected == null ? 0 : visitaSelected.getVisitasId()));
        visitasTiendasSelected = new ArrayList<>();
        visitasTiendas = new ArrayList<>();
        for(VisitaTienda vt : temp){
            if(visitaSelected != null){
                if(vt.getVisitaId() == visitaSelected.getVisitasId()){
                    visitasTiendasSelected.add(vt);
                    visitaSelected.getVisitasTiendas().add(vt);
                }
            }
            visitasTiendas.add(vt);
        }
    }
    
    public void showEditDialog(){
        if(visitaSelected != null){
            if(confirmEdit()){
                nombreVisita = visitaSelected.getVisitas();
                rutas = MBRutas.parseRutaObject(getServiceVisitas().listaRutasSel(TerritorioId, EquiposId));
                rutaSelected = visitaSelected.getRutasId();
                fechaVisita = visitaSelected.getFechaIni();
                initListaPromotores();
                promotorSelected = visitaSelected.getUsuariosOID();
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('nvDialog').show();");
            } 
        }else{
            Utileria.mensajeAlerta("Para editar debe seleccionar sólo una visita.");
        }     
    }
    
    private boolean confirmEdit (){
        HashMap<String, Object> r = getServiceVisitas().visitasVal(visitaSelected.getVisitasId());
        if((boolean)r.get("Exito")){
            if((boolean)r.get("Editar")){
                return true;
            }else{
                Utileria.mensajeAlerta(r.get("ValMsj"));
                return false;
            }
        }else{
            Utileria.mensajeErroneo(r.get("Msj"));
            return false;
        }
    }
    
    public void resetVisitaSelected(){
        resetView();
        rutas = MBRutas.parseRutaObject(getServiceVisitas().listaRutasSel(TerritorioId, EquiposId));
        visitaSelected = null;
        initListaPromotores();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('nvDialog').show();");
    }
    
    private List<VisitaTienda> parseVisitaTiendaObject(List<Map<String, Object>> l){
        List<VisitaTienda> list = new ArrayList<>();
        int contador = 1;
        if(l != null && !l.isEmpty()){
            for(Map m : l){
                VisitaTienda vt = new VisitaTienda();
                vt.setProyectosId((int)((long) m.get("ProyectosId")));
                vt.setDescripcion((String) m.get("Descripcion"));
                vt.setNombre((String) m.get("Nombre"));
                vt.setTiendasId((int) ((long)m.get("TiendasId")));
                vt.setFechaIni((Date)m.get("FechaIni"));
                vt.setSelectable(Integer.valueOf(String.valueOf(m.get("Enabled"))) != 0);
                vt.setNombreCliente(String.valueOf(m.get("NombreCliente")));
                vt.setClienteId(Integer.valueOf(String.valueOf(m.get("ClientesId"))));
                vt.setVisitaId(Integer.valueOf(String.valueOf(m.get("VisitasId") == null ? 0 : m.get("VisitasId"))));
                vt.setPertenece(Integer.valueOf(String.valueOf(m.get("Pertenece"))) != 0);
                vt.setTareasId(Integer.valueOf(m.get("TareasId") == null ? "-1" : m.get("TareasId").toString()));
                vt.setNumEncuestas((int)m.get("EncuestasCount"));
                vt.setOrdenTabla(contador);
                contador++;
                list.add(vt);
            }
        }
        return list;
    }
    
    private List<Visita> parseVisitaObject(List<Map<String, Object>> l){
        List<Visita> list = new ArrayList<>();
        if(l != null && !l.isEmpty()){
           for(Map m: l){
                Visita v = new Visita(
                        String.valueOf(m.get("Visitas")), 
                        String.valueOf(m.get("Ruta")),
                        String.valueOf(m.get("Proyectos")),
                        String.valueOf(m.get("Encuesta") == null ? "" : m.get("Encuesta")), 
                        String.valueOf(m.get("Nombre") == null ? "" : m.get("Nombre")), 
                        String.valueOf(m.get("ApellidoPaterno") == null ? "" : m.get("ApellidoPaterno")), 
                        String.valueOf(m.get("ApellidoMaterno") == null ? "" : m.get("ApellidoMaterno")), 
                        java.sql.Date.valueOf(String.valueOf(m.get("FechaIni"))));
                v.setTiendas(String.valueOf(m.get("Tiendas")));
                v.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                v.setProyectos(String.valueOf(m.get("Proyecto")));
                v.setVisitasId(Integer.valueOf(String.valueOf(m.get("VisitasId"))));
                v.setRutasId(Integer.valueOf(String.valueOf(m.get("RutasId"))));
                v.setProyectosId(Integer.valueOf(String.valueOf(m.get("ProyectosId"))));
                v.setUsuariosOID(String.valueOf(m.get("UsuariosOID")));
                v.setTareasId(Integer.valueOf(String.valueOf(m.get("TareasId"))));
                v.setEncuestasId(Integer.valueOf(String.valueOf(m.get("EncuestasId"))));
                list.add(v);
            } 
        }
        return list;
    }
    
    public void resetView(){
        promotorSelected = "";
        promotores = new ArrayList<>();
        visitasTiendasSelected = new ArrayList<>();
        visitasTiendas = new ArrayList<>();
        rutaSelected = 0;
        rutas = new ArrayList<>();
        nombreVisita = "";
        fechaVisita = new Date();
    }
    
    /**
     * @return the serviceVisitas
     */
    public ServiceVisitas getServiceVisitas() {
        return serviceVisitas;
    }

    /**
     * @param serviceVisitas the serviceVisitas to set
     */
    public void setServiceVisitas(ServiceVisitas serviceVisitas) {
        this.serviceVisitas = serviceVisitas;
    }

    /**
     * @return the TiendaId
     */
    public int getTiendaId() {
        return TiendaId;
    }

    /**
     * @param TiendaId the TiendaId to set
     */
    public void setTiendaId(int TiendaId) {
        this.TiendaId = TiendaId;
    }

    /**
     * @return the visitas
     */
    public List<Visita> getVisitas() {
        return visitas;
    }

    /**
     * @param visitas the visitas to set
     */
    public void setVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }

    public int getTerritorioId() {
        return TerritorioId;
    }

    public void setTerritorioId(int TerritorioId) {
        this.TerritorioId = TerritorioId;
    }

    public Visita getVisitaSelected() {
        return visitaSelected;
    }

    public void setVisitaSelected(Visita visitaSelected) {
        this.visitaSelected = visitaSelected;
    }

    public ServiceRutas getServiceRutas() {
        return serviceRutas;
    }

    public void setServiceRutas(ServiceRutas serviceRutas) {
        this.serviceRutas = serviceRutas;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public int getEquiposId() {
        return EquiposId;
    }

    public void setEquiposId(int EquiposId) {
        this.EquiposId = EquiposId;
    }

    public int getRutaSelected() {
        return rutaSelected;
    }

    public void setRutaSelected(int rutaSelected) {
        this.rutaSelected = rutaSelected;
    }

    public List<Promotor> getPromotores() {
        return promotores;
    }

    public void setPromotores(List<Promotor> promotores) {
        this.promotores = promotores;
    }

    public int getUnidadesNegociosId() {
        return UnidadesNegociosId;
    }

    public void setUnidadesNegociosId(int UnidadesNegociosId) {
        this.UnidadesNegociosId = UnidadesNegociosId;
    }

    public String getPromotorSelected() {
        return promotorSelected;
    }

    public void setPromotorSelected(String promotorSelected) {
        this.promotorSelected = promotorSelected;
    }

    public String getNombreVisita() {
        return nombreVisita;
    }

    public void setNombreVisita(String nombreVisita) {
        this.nombreVisita = nombreVisita;
    }

    public List<VisitaTienda> getVisitasTiendas() {
        return visitasTiendas;
    }

    public void setVisitasTiendas(List<VisitaTienda> visitasTiendas) {
        this.visitasTiendas = visitasTiendas;
    }

    public List<VisitaTienda> getVisitasTiendasSelected() {
        return visitasTiendasSelected;
    }

    public void setVisitasTiendasSelected(List<VisitaTienda> visitasTiendasSelected) {
        this.visitasTiendasSelected = visitasTiendasSelected;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public Date getToday() {
        return today;
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

    public ServiceTerritorios getServiceTerritorios() {
        return serviceTerritorios;
    }

    public void setServiceTerritorios(ServiceTerritorios serviceTerritorios) {
        this.serviceTerritorios = serviceTerritorios;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
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
  
 public List<VisitaTienda> getVisitasFiltered() {
        return visitasFiltered;
    }

    public void setVisitasFiltered(List<VisitaTienda> visitasFiltered) {
        this.visitasFiltered = visitasFiltered;
    }    
}
