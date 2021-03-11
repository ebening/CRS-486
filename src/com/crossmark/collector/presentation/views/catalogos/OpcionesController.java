package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.*;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceVariables;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.crossmark.collector.presentation.views.utils.Utileria.*;

/**
 * Created by christian on 13/12/2014.
 */
public class OpcionesController extends AbstractCatalogs<Lista> {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
            Utileria.getString("nombre"), Utileria.getString("clave"));
    private final int CATALOGO = 1;
    private final int CRUZADA = 2;
    private ServiceCatalogos serviceCatalogos;
    private ServiceVariables serviceVariables;
    private List<TipoLista> tipoListaCombo;
    private boolean principal;
    private boolean catalogo;
    private boolean cruzada;
    private boolean saveAdd;
    private String accion;
    private String tipo;
    private OpcionCruzada currentCruzada;
    private OpcionPlana currentPlana;
    private boolean editPhase;
    private List<OpcionCruzada> listCruzadaAux;
    private List<OpcionPlana> listPlanaAux;
    private List<Variable> listVariable;
    private boolean change;

    public ServiceVariables getServiceVariables() {
        return serviceVariables;
    }

    public void setServiceVariables(ServiceVariables serviceVariables) {
        this.serviceVariables = serviceVariables;
    }

    public List<Variable> getListVariable() {
        return listVariable;
    }

    public void setListVariable(List<Variable> listVariable) {
        this.listVariable = listVariable;
    }
    
    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public List<TipoLista> getTipoListaCombo() {
        return tipoListaCombo;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public boolean isCatalogo() {
        return catalogo;
    }

    public void setCatalogo(boolean catalogo) {
        this.catalogo = catalogo;
    }

    public boolean isCruzada() {
        return cruzada;
    }

    public void setCruzada(boolean cruzada) {
        this.cruzada = cruzada;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isEditPhase() {
        return editPhase;
    }

    public void setEditPhase(boolean editPhase) {
        this.editPhase = editPhase;
    }

    public OpcionCruzada getCurrentCruzada() {
        return currentCruzada;
    }

    public void setCurrentCruzada(OpcionCruzada currentCruzada) {
        this.currentCruzada = currentCruzada;
    }

    public OpcionPlana getCurrentPlana() {
        return currentPlana;
    }

    public void setCurrentPlana(OpcionPlana currentPlana) {
        this.currentPlana = currentPlana;
    }

    public List<OpcionCruzada> getListCruzadaAux() {
        return listCruzadaAux;
    }

    public void setListCruzadaAux(List<OpcionCruzada> listCruzadaAux) {
        this.listCruzadaAux = listCruzadaAux;
    }

    public List<OpcionPlana> getListPlanaAux() {
        return listPlanaAux;
    }

    public void setListPlanaAux(List<OpcionPlana> listPlanaAux) {
        this.listPlanaAux = listPlanaAux;
    }

    public boolean isSaveAdd() {
        return saveAdd;
    }

    public void setSaveAdd(boolean saveAdd) {
        this.saveAdd = saveAdd;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }
    

    @Override
    protected void init() {
        setChange(false);
        setPrincipal(true);
        setCatalogo(true);
        setCruzada(false);
        setCurrent(new Lista());
        setCurrentPlana(new OpcionPlana());
        setCurrentCruzada(new OpcionCruzada());
        setList(serviceCatalogos.getAllLista());
        setSelected(new ArrayList<Lista>());
        tipoListaCombo = serviceCatalogos.getAllTipoListas();
        listaVariable();
        setSaveAdd(false);
        setClase(Lista.class);
        //Columnas dinamicas
        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("clave") + " " + Utileria.getString("nombre"));
        createDynamicColumns();

    }

    @Override
    public void crear() {
        try {
            serviceCatalogos.crear(getCurrent());
            setList(serviceCatalogos.getAllLista());
            Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
            setCurrent(new Lista());
            setChange(false);
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void editar() {

        try {

            Utileria.logger(getClass()).info("Cruzada: "+getCurrent().getOpcionCruzadasList().size());
            Utileria.logger(getClass()).info("Plana: "+getCurrent().getOpcionPlanaList().size());

            serviceCatalogos.editar(getCurrent());
            setList(serviceCatalogos.getAllLista());
            Utileria.mensajeSatisfactorio(Utileria.getString("update_success"));
            setChange(false);
        } catch (Exception e) {
            Utileria.logger(getClass()).error(e);
        }
    }

    @Override
    public void eliminar() {

    }

    @Override
    public void comprobarSeleccionCrear() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        accion = params.get(Utileria.ACCION);
        if (accion.equals(Utileria.CREAR)) {
            setCurrent(new Lista());
            setSaveAdd(true);
            setPrincipal(false);
        } else if (accion.equals(Utileria.EDITAR)) {
            if (!getSelected().isEmpty()) {
                if (!(getSelected().size() > 1)) {
                    setSaveAdd(false);
                    setPrincipal(false);
                } else {
                    Utileria.mensajeAlerta(Utileria.getString("fail_edit_selection"));
                }
            } else {
                Utileria.mensajeAlerta(Utileria.getString("fail_selection_2"));
            }
        }
    }

    @Override
    public void cerrarDialogCrear() {

        if(isChange()){
            RequestContext.getCurrentInstance().execute("PF('dialog-opciones-salir').show();");
        }else{
            salir();
        }

    }
    
    public void listaVariable() {
        this.listVariable = serviceVariables.getAllVariables();
    }
    
    public String nameVariable(String var) {
        this.listVariable = serviceVariables.getAllVariables();
        if(this.listVariable != null && !this.listVariable.isEmpty()){
            for(Variable item : this.listVariable){
                if(String.valueOf(item.getVariablesId()).equals(var)){
                    return item.getNombre();
                }
            }
        }
        return var;
    }
    
    public void comprobarPlanaCruzada() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        tipo = params.get("tipo");
        setCurrentPlana(new OpcionPlana());
        setCurrentCruzada(new OpcionCruzada());
        if (tipo != null && tipo.equals(String.valueOf(CATALOGO))) {
            RequestContext.getCurrentInstance().update("center-form:catalogo-formulario");
            RequestContext.getCurrentInstance().execute("PF('dialog-catalogo').show()");
        } else if (tipo != null && tipo.equals(String.valueOf(CRUZADA))) {
            RequestContext.getCurrentInstance().update("center-form:cruzada-formulario");
            RequestContext.getCurrentInstance().execute("PF('dialog-cruzada').show()");
        }
    }

    public void salir() {
        setCurrent(null);
        getSelected().clear();
        setList(serviceCatalogos.getAllLista());
        RequestContext.getCurrentInstance().reset("center-form:catalogo-formulario");
        RequestContext.getCurrentInstance().reset("center-form:cruzada-formulario");
        RequestContext.getCurrentInstance().reset("center-form:opciones-panel-formulario");
        RequestContext.getCurrentInstance().update("center-form");
        setCatalogo(true);
        setCruzada(false);
        setPrincipal(true);
    }

    public void cambiarTabla(AjaxBehaviorEvent event) {
        cambiarTablaPt2();
    }

    private void cambiarTablaPt2() {
        String oid = getCurrent().getOid();
        if (!oid.equals("noOid")) {
            if (getCurrent().getTipoLista().getId() == CATALOGO) {
                getCurrent().setOpcionPlanaList(serviceCatalogos.getOpcionesPlanasByOid(getCurrent().getOid()));
                setCatalogo(true);
                setCruzada(false);
            } else if (getCurrent().getTipoLista().getId() == CRUZADA) {
                getCurrent().setOpcionCruzadasList(serviceCatalogos.getOpcionesCruzadasByOid((getCurrent().getOid())));
                setCatalogo(false);
                setCruzada(true);
            }
        } else {
            if (getCurrent().getTipoLista().getId() == CATALOGO) {
                getCurrent().setOpcionPlanaList(new ArrayList<OpcionPlana>());
                setCatalogo(true);
                setCruzada(false);
            } else if (getCurrent().getTipoLista().getId() == CRUZADA) {
                getCurrent().setOpcionCruzadasList(new ArrayList<OpcionCruzada>());
                setCatalogo(false);
                setCruzada(true);
            }
        }

    }

    public void agregarRowPlana() {
        Utileria.logger(getClass()).info("agregarRowPlana");
       if (getCurrent().getOpcionPlanaList().isEmpty()) {
            getCurrent().getOpcionPlanaList().add(getCurrentPlana());
        } else {
            getCurrent().getOpcionPlanaList().add(0, getCurrentPlana());
        }
    }

    public void agregarRowCruzada() {
        Utileria.logger(getClass()).info("agregarRowCruzada");
        if (getCurrent().getOpcionCruzadasList().isEmpty()) {
            getCurrent().getOpcionCruzadasList().add(getCurrentCruzada());
        } else {
            getCurrent().getOpcionCruzadasList().add(0, getCurrentCruzada());
        }
    }

    @Override
    public void seleccionar(SelectEvent event) {
        super.seleccionar(event);
        cambiarTablaPt2();
    }

    public void onRowEditPlano(RowEditEvent event) {
        OpcionPlana tmp =((OpcionPlana) event.getObject());
        imprimir(tmp);
    }

    public void onRowCancelPlano(RowEditEvent event) {
        OpcionPlana tmp =((OpcionPlana) event.getObject());
        imprimir(tmp);
    }

    public void onRowEditCruzado(RowEditEvent event) {
        OpcionCruzada tmp =((OpcionCruzada) event.getObject());
        imprimir(tmp);
    }

    public void onRowCancelCruzado(RowEditEvent event) {
        OpcionCruzada tmp =((OpcionCruzada) event.getObject());
        imprimir(tmp);
    }


    public void cambio(){
        setChange(true);
    }


    public void imprimir(OpcionPlana o) {
        Utileria.logger(getClass()).info("*******OpcionPlana************");
        Utileria.logger(getClass()).info("Clave: " + o.getClave());
        Utileria.logger(getClass()).info("Texto: " + o.getTexto());
        Utileria.logger(getClass()).info("Orden: " + o.getOrden());
        Utileria.logger(getClass()).info("CodigoBarras: " + o.getCodigoBarra());
        Utileria.logger(getClass()).info("getValor: " + o.getValor());
        Utileria.logger(getClass()).info("Visible: " + o.isVisible());
        Utileria.logger(getClass()).info("******************************");
    }

    public void imprimir(OpcionCruzada o) {
        Utileria.logger(getClass()).info("----------OpcionCruzada----------");
        Utileria.logger(getClass()).info("Valor: " + o.getValor());
        Utileria.logger(getClass()).info("Variable: " + o.getVariable());
        Utileria.logger(getClass()).info("---------------------------------");
    }

    public void imprimir(Lista o) {
        Utileria.logger(getClass()).info("----------Lista----------");
        Utileria.logger(getClass()).info("OId: " + o.getOid());
        Utileria.logger(getClass()).info("Nombre: " + o.getNombre());
        Utileria.logger(getClass()).info("Variable: " + o.getClave());
        Utileria.logger(getClass()).info("TipoListaId: " + o.getTipoLista().getId());
        Utileria.logger(getClass()).info("--------------------------");

    }

}
