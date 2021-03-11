
package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOCiudades;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class ServiceCiudadesImpl implements ServiceCiudades{

    DAOCiudades daoCiudades;
    
    @Override
    public List getCiudades() {
        return getDaoCiudades().getCiudades();
    }

 /*   @Override
    public List getCiudadById(int ciudadId) {
        return getDaoCiudades().getCiudadById(ciudadId);
    } */

    /**
     * @return the daoCiudades
     */
    public DAOCiudades getDaoCiudades() {
        return daoCiudades;
    }

    /**
     * @param daoCiudades the daoCiudades to set
     */
    public void setDaoCiudades(DAOCiudades daoCiudades) {
        this.daoCiudades = daoCiudades;
    }

    @Override
    public List getCiudadesByEstado(int ciudadesId, int estadoId, String nombre, boolean activo) {
        return getDaoCiudades().getCiudadByEstado(ciudadesId, estadoId, nombre, activo);
    }

    
}
