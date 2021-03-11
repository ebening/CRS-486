
package com.crossmark.collector.presentation.views.visitas.objects;

/**
 *
 * @author jdominguez
 */
public class Estado {
    private int id;
    private String nombre;
    private String Clave;
    private boolean Activo;

    public Estado() {
    }

    public Estado(int id, String Nombre, String Clave, boolean Activo){
        this.id = id;
        this.nombre = Nombre;
        this.Clave = Clave;
        this.Activo = Activo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    /**
     * @return the Clave
     */
    public String getClave() {
        return Clave;
    }

    /**
     * @param Clave the Clave to set
     */
    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    /**
     * @return the Activo
     */
    public boolean isActivo() {
        return Activo;
    }

    /**
     * @param Activo the Activo to set
     */
    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }
}
