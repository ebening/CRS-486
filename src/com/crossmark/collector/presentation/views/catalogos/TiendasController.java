package com.crossmark.collector.presentation.views.catalogos;

import com.crossmark.collector.business.domain.LazyTiendaDataModel;
import com.crossmark.collector.business.domain.Tienda;
import com.crossmark.collector.business.services.ServiceCatalogos;
import com.crossmark.collector.business.services.ServiceTiendas;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.MBTiendas;
import com.crossmark.collector.presentation.views.visitas.objects.*;

import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.*;
import org.primefaces.context.RequestContext;

/**
 * Created by christian on 05/12/2014.
 */
public class TiendasController extends AbstractCatalogs<Tienda> {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList(Utileria.getString("id"),
            Utileria.getString("nombre"), Utileria.getString("activo"));
    private ServiceCatalogos serviceCatalogos;
    private ServiceTiendas serviceTiendas;

    private List<Cadena> listaCadenas;
    private List<Formato> listaFormatos;
    private List<Estado> listaEstados;
    private List<Ciudad> listaCiudades;
    private List<Territorio> listaTerritorios;

    // **************** Filtros Busqueda ********* //
    private int estadosId;
    private int ciudadesId;
    private int formatoId;
    private int cadenasId;
    private int grupoSelected;
    private String nombreTienda;
    private String codigoTienda;

    private LazyTiendaDataModel lazyModel;


    public ServiceCatalogos getServiceCatalogos() {
        return serviceCatalogos;
    }

    public void setServiceCatalogos(ServiceCatalogos serviceCatalogos) {
        this.serviceCatalogos = serviceCatalogos;
    }

    public ServiceTiendas getServiceTiendas() {
        return serviceTiendas;
    }

    public void setServiceTiendas(ServiceTiendas serviceTiendas) {
        this.serviceTiendas = serviceTiendas;
    }

    public List<Cadena> getListaCadenas() {
        return listaCadenas;
    }

    public List<Formato> getListaFormatos() {
        return listaFormatos;
    }

    public List<Estado> getListaEstados() {
        return listaEstados;
    }

    public List<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    public List<Territorio> getListaTerritorios() {
        return listaTerritorios;
    }

    public LazyTiendaDataModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyTiendaDataModel lazyModel) {
        this.lazyModel = lazyModel;
    }

    public int getEstadosId() {
        return estadosId;
    }

    public void setEstadosId(int estadosId) {
        this.estadosId = estadosId;
    }

    public int getCiudadesId() {
        return ciudadesId;
    }

    public void setCiudadesId(int ciudadesId) {
        this.ciudadesId = ciudadesId;
    }

    public int getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(int formatoId) {
        this.formatoId = formatoId;
    }

    public int getCadenasId() {
        return cadenasId;
    }

    public void setCadenasId(int cadenasId) {
        this.cadenasId = cadenasId;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public String getCodigoTienda() {
        return codigoTienda;
    }

    public void setCodigoTienda(String codigoTienda) {
        this.codigoTienda = codigoTienda;
    }



    @Override
    protected void init() {
//        setList(serviceCatalogos.getAllActivatedTiendas());
        HashMap<String, Object> filtros = MBTiendas.getFiltros();

        //lazyModel = new LazyTiendaDataModel(serviceCatalogos, serviceTiendas.tiendasTerritoriosNumRegSel(filtros));
        setSelected(new ArrayList<Tienda>());
        setCurrent(new Tienda());

        setClase(Tienda.class);
        setSeleccionCrear("PF('dialog-tiendas-crear').show()");
        setSeleccionEditar("PF('dialog-tiendas-editar').show()");
        setSeleccionEliminar("PF('dialog-tiendas-eliminar').show()");

        listaCadenas = serviceCatalogos.getAllActivatedCadenas();
        listaFormatos = serviceCatalogos.getAllActivatedFormatos();
        listaEstados = serviceCatalogos.getAllActivatedEstados();
        listaCiudades = new ArrayList<>();
        listaTerritorios = serviceCatalogos.getAllActivatedTerritories();

        //Columnas dinamicas
        setVALID_COLUMN_KEYS(VALID_COLUMN_KEYS);
        setColumnTemplate(Utileria.getString("nombre"));
        createDynamicColumns();
    }

    public void buscarTiendas(){

        HashMap<String, Object> filtros = MBTiendas.getFiltros();

        filtros.put("Nombre", nombreTienda == null ? null : nombreTienda);
        filtros.put("CadenasId", cadenasId );
        filtros.put("FormatosId", formatoId);
        filtros.put("EstadosId", estadosId);
        filtros.put("CiudadesId", ciudadesId);

        lazyModel = null;
        lazyModel = new LazyTiendaDataModel(serviceCatalogos, serviceTiendas.tiendasTerritoriosNumRegSel(filtros), filtros);

        setSelected(new ArrayList<Tienda>());
        setCurrent(new Tienda());
    }

    @Override
    public void crear() {
        
        boolean validateNumber = true;
        String mensaje = "Ha ocurrido un error al guardar la información.";

        try {
            Integer test = Integer.parseInt(getCurrent().getCp());
        } catch (Exception e) {
            validateNumber = false;
            mensaje = "El código postal debe tener un formato numérico.";
            Utileria.logger(getClass()).info("El código postal debe tener un formato numérico...");
        }

        if (validateNumber) {
            try {
                float test = Float.parseFloat(getCurrent().getCoordenadaX());
            } catch (Exception e) {
                validateNumber = false;
                mensaje = "La latitud debe tener un formato numérico.";
                Utileria.logger(getClass()).info("La latitud debe tener un formato numérico...");
            }
        }

        if (validateNumber) {
            try {
                float test = Float.parseFloat(getCurrent().getCoordenadaY());
            } catch (Exception e) {
                validateNumber = false;
                mensaje = "La longitud debe tener un formato numérico.";
                Utileria.logger(getClass()).info("La longitud debe tener un formato numérico...");
            }
        }

        Utileria.logger(getClass()).info("Crear Tienda: " + super.getCurrent().getNombre());

        if (validateNumber) {
            try {
                serviceCatalogos.crear(getCurrent());
                setList(serviceCatalogos.getAllActivatedTiendas());
                Utileria.mensajeSatisfactorio(Utileria.getString("create_success"));
                setCurrent(new Tienda());
            } catch (Exception e) {
                Utileria.logger(getClass()).error(e);
            }
        } else {
            Utileria.mensajeErroneo(mensaje);
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("validationFailed", true);
        }
    }

    @Override
    public void editar() {

        boolean validateNumber = true;
        String mensaje = "Ha ocurrido un error al guardar la información.";

        try {
            Integer test = Integer.parseInt(getCurrent().getCp());
        } catch (Exception e) {
            validateNumber = false;
            mensaje = "El código postal debe tener un formato numérico.";
            Utileria.logger(getClass()).info("El código postal debe tener un formato numérico...");
        }

        if (validateNumber) {
            try {
                float test = Float.parseFloat(getCurrent().getCoordenadaX());
            } catch (Exception e) {
                validateNumber = false;
                mensaje = "La latitud debe tener un formato numérico.";
                Utileria.logger(getClass()).info("La latitud debe tener un formato numérico...");
            }
        }

        if (validateNumber) {
            try {
                float test = Float.parseFloat(getCurrent().getCoordenadaY());
            } catch (Exception e) {
                validateNumber = false;
                mensaje = "La longitud debe tener un formato numérico.";
                Utileria.logger(getClass()).info("La longitud debe tener un formato numérico...");
            }
        }

        Utileria.logger(getClass()).info("Editar Tienda: Nombre Region" + super.getCurrent().getNombre());

        if (validateNumber)
        {
            try {
                serviceCatalogos.editar(getCurrent());
                setList(serviceCatalogos.getAllActivatedTiendas());
                Utileria.mensajeSatisfactorio(Utileria.getString("update_success"));
            } catch (Exception e) {
                Utileria.logger(getClass()).error(e);
            }
        }
        else
        {
            Utileria.mensajeErroneo(mensaje);
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("validationFailed", true);
        }
    }

    @Override
    public void eliminar() {
        Map<String, String> noEliminados = new HashMap<>();
        List<String> eliminados = new ArrayList<>();
        StringBuilder mensaje = null;
        Utileria.logger(getClass()).info("RemoveTerritorio: " + getCurrent().getNombre());
        try {
            String resultado = null;
            if (getSelected().size() > 1) {
                for (Tienda territorio : getSelected()) {
                    resultado = serviceCatalogos.eliminar(territorio);
                    if (!resultado.equals("0")) {
                        noEliminados.put(territorio.getNombre(), resultado);
                        Utileria.mensajeAlerta(Utileria.getString(resultado));
                    } else {
                        eliminados.add(territorio.getNombre());
                        Utileria.mensajeSatisfactorio(Utileria.getString("remove_success_p", territorio.getNombre()));
                    }

                    if (eliminados.size() == getSelected().size() && noEliminados.size() == 0) {
                        Utileria.mensajeSatisfactorio(Utileria.getString("removes_success"));
                    } else if ((eliminados.size() == 1) && (noEliminados.size() == 1)) {
                        mensaje = new StringBuilder();
                        mensaje.append(Utileria.getString("registros_eliminados"));
                        for (String e : eliminados) {
                            mensaje.append("\t" + e + "\n");
                        }
                        mensaje.append(Utileria.getString("registros_no_eliminados"));
                        for (Map.Entry<String, String> entry : noEliminados.entrySet()) {
                            mensaje.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
                        }
                        Utileria.mensajeAlerta(mensaje.toString());
                    }

                }
            } else if (getSelected().size() == 1) {
                resultado = serviceCatalogos.eliminar(getCurrent());
                if (!resultado.equals("0")) {
                    Utileria.mensajeAlerta(Utileria.getString(resultado));
                } else {
                    Utileria.mensajeSatisfactorio(Utileria.getString("remove_success"));
                }
            }
        } catch (Exception e) {
            Utileria.mensajeErroneo(Utileria.getString("fail_operation"));
            Utileria.logger(getClass()).error(e.getMessage());
            Utileria.logger(getClass()).error(e.getCause());
        }
        getSelected().clear();
        setList(serviceCatalogos.getAllActivatedTiendas());
    }


    public void preEditar(){
        Utileria.logger(getClass()).info("PreEditar");
        Utileria.logger(getClass()).info("Nombre: "+getCurrent().getNombre());
        Utileria.logger(getClass()).info("Cadena: "+getCurrent().getCadena().getId());
        Utileria.logger(getClass()).info("Formato: "+getCurrent().getFormato().getId());
        Utileria.logger(getClass()).info("Direccion: "+getCurrent().getDireccion());
        Utileria.logger(getClass()).info("Estado: "+getCurrent().getEstado().getId());
        Utileria.logger(getClass()).info("Ciudad: "+getCurrent().getCiudad().getId());
        Utileria.logger(getClass()).info("CP: "+getCurrent().getCp());
        Utileria.logger(getClass()).info("Latitud: "+Float.valueOf(getCurrent().getCoordenadaX()));
        Utileria.logger(getClass()).info("Longitud: "+Float.valueOf(getCurrent().getCoordenadaY()));
        Utileria.logger(getClass()).info("Territorio: "+getCurrent().getTerritorioNativo().getId());
    }

    public void cambiarListEstado(AjaxBehaviorEvent event) {
        Integer id = (Integer) ((UIOutput) event.getSource()).getValue();
        Utileria.logger(getClass()).info(id);
        if (id == 0) {
            listaCiudades.clear();
        } else {
            listaCiudades.clear();
            listaCiudades.addAll(serviceCatalogos.getCiudadesByEstado(id));
        }
    }

}
