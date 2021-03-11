package com.crossmark.collector.presentation.views.mensajes;

import com.crossmark.collector.business.domain.Mensaje;
import com.crossmark.collector.business.domain.TipoMensaje;
import com.crossmark.collector.business.services.ServiceMensajes;
import com.crossmark.collector.presentation.views.catalogos.AbstractCatalogs;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Usuario;
import com.crossmark.collector.presentation.views.security.objects.UsuarioSession;
import com.crossmark.collector.presentation.views.utils.DateUtil;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by christian on 05/01/2015.
 */
public class MensajesController extends AbstractCatalogs<Mensaje>{

    private ServiceMensajes serviceMensajes;
    private List<TipoMensaje> comboTipoMensaje;
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ServiceMensajes getServiceMensajes() {
        return serviceMensajes;
    }

    public void setServiceMensajes(ServiceMensajes serviceMensajes) {
        this.serviceMensajes = serviceMensajes;
    }

    public List<TipoMensaje> getComboTipoMensaje() {
        return comboTipoMensaje;
    }

    public void setComboTipoMensaje(List<TipoMensaje> comboTipoMensaje) {
        this.comboTipoMensaje = comboTipoMensaje;
    }

    @Override
    protected void init() {
        UsuarioSession usSistema = null;
        usSistema = Utileria.getSessionAttribute("userLoged");
        setList(serviceMensajes.getAllMensajesByUsuarioOID(usSistema.getOID()));
        setCurrent(new Mensaje());
        setSelected(new ArrayList<Mensaje>());
        setClase(Mensaje.class);
        setComboTipoMensaje(serviceMensajes.getAllTipoMensajes());
    }

    @Override
    public void crear() {

    }

    @Override
    public void editar() {

    }

    @Override
    public void eliminar() {

    }

    public boolean filterByFecha(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        Date date1 = null;
        Date date2 = null;
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }


        String s = Utileria.validarStringNull(value);
        if (s.equals(" ")) {
            return false;
        }
        try {
            date1 = Utileria.stringToDate(s, Utileria.DATA_FORMAT_1);
            date2 = Utileria.stringToDate(filterText, Utileria.DATA_FORMAT_1);
        } catch (ParseException e) {
            Utileria.logger(getClass()).error(e.getCause());
            return false;
        }

        return DateUtil.dateEquals(date1, date2);
    }

    public String nombreCompleto(Usuario o){
        if(o == null){
            return null;
        }else {
            return o.getNombre() + " " + o.getApellidoPaterno() + " " + o.getApellidoMaterno();
        }
    }

}
