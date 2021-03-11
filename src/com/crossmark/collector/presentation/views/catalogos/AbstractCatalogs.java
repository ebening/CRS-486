package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.presentation.views.utils.ColumnModel;
import com.crossmark.collector.presentation.views.utils.Utileria;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 05/12/2014.
 * @param <T>
 */
public abstract class AbstractCatalogs<T> implements Serializable {

    private List<T> list;
    private List<T> selected;
    private List<T> filtered;
    private Class<T> clase;
    private T current;

    private String columnTemplate;
    private List<String> VALID_COLUMN_KEYS;
    private List<ColumnModel> columns;

    private String seleccionEditar;
    private String seleccionEliminar;
    private String seleccionCrear;

    private String nombreTable;

    @PostConstruct
    protected abstract void init();

    public abstract void crear();

    public abstract void editar();

    public abstract void eliminar();

    public void cerrarDialogCrear(){
        setCurrent((T)Utileria.nuevaInstancia(getClase()));
        getSelected().clear();
    }

    public void cerrarDialogEditar(){
        setCurrent((T)Utileria.nuevaInstancia(getClase()));
        getSelected().clear();
    }

    public void seleccionar(SelectEvent event) {
        setCurrent(getSelected().get(0));
    }

    public void comprobarSeleccionCrear() {
        setCurrent((T)Utileria.nuevaInstancia(getClase()));
        getSelected().clear();
        RequestContext.getCurrentInstance().execute(getSeleccionCrear());
    }

    public final void comprobarSeleccionEditar(){
        if (!getSelected().isEmpty()) {

            if(!(getSelected().size()> 1)){
                RequestContext.getCurrentInstance().execute(getSeleccionEditar());
            }else{
                Utileria.mensajeAlerta(Utileria.getString("fail_edit_selection"));
            }

        } else {
            Utileria.mensajeAlerta(Utileria.getString("fail_selection_2"));
        }
    }

    public final void comprobarSeleccionEliminar(){
        if (!getSelected().isEmpty()) {
            RequestContext.getCurrentInstance().execute(getSeleccionEliminar());
        } else {
            Utileria.mensajeAlerta(Utileria.getString("fail_remove_selection"));
        }
    }

    public final void quitarSeleccion(UnselectEvent event){
        setCurrent((T)Utileria.nuevaInstancia(getClase()));
    }

    public final void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(nombreTable);
        table.setValueExpression("sortBy", null);

        //update columns
        createDynamicColumns();
    }

    protected final void createDynamicColumns() {
        String[] columnKeys = columnTemplate.split(" ");
        columns = new ArrayList<>();

        for (String columnKey : columnKeys) {
            String key = columnKey.trim();

            if (VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(Utileria.nameColumnUpperCase(columnKey), columnKey));
            }
        }
    }

    public final String removeQuestion() {
        return getSelected().size() == 1 ? Utileria.getString("remove_question") :
                Utileria.getString("remove_question_2");
    }

    protected final ArrayList<T> newArrayList(){
        return new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getCurrent() {
        return current;
    }

    public void setCurrent(T current) {
        this.current = current;
    }

    public List<T> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<T> filtered) {
        this.filtered = filtered;
    }

    public List<T> getSelected() {
        return selected;
    }

    public void setSelected(List<T> selected) {
        this.selected = selected;
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public List<String> getVALID_COLUMN_KEYS() {
        return VALID_COLUMN_KEYS;
    }

    public void setVALID_COLUMN_KEYS(List<String> VALID_COLUMN_KEYS) {
        this.VALID_COLUMN_KEYS = VALID_COLUMN_KEYS;
    }

    public String getNombreTable() {
        return nombreTable;
    }

    public void setNombreTable(String nombreTable) {
        this.nombreTable = nombreTable;
    }

    public String getSeleccionEditar() {
        return seleccionEditar;
    }

    public void setSeleccionEditar(String seleccionEditar) {
        this.seleccionEditar = seleccionEditar;
    }

    public String getSeleccionEliminar() {
        return seleccionEliminar;
    }

    public void setSeleccionEliminar(String seleccionEliminar) {
        this.seleccionEliminar = seleccionEliminar;
    }

    public String getSeleccionCrear() {
        return seleccionCrear;
    }

    public void setSeleccionCrear(String seleccionCrear) {
        this.seleccionCrear = seleccionCrear;
    }

    private Class<T> getClase() {
        return clase;
    }

    public void setClase(Class<T> clase) {
        this.clase = clase;
    }
}
