/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Condiciones;
import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Equipo;
import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Territorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author franciscom
 */
public class DAOCondicionesImpl implements DAOCondiciones, Serializable {
    private DatabaseStoredProc spCondicionesSel;
    private DatabaseStoredProc spCondicionesUps;
    private DatabaseStoredProc spCondicionesDel;
    
    @Override
    public List<Condiciones> getListCondiciones(String seccionesOID, String preguntasOID, String opcionesOID, Boolean seleccionadas) {
        List<Condiciones> listado = new ArrayList<>();
        
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("SeccionesOID", seccionesOID);
        inputs.put("PreguntasOID", preguntasOID);
        inputs.put("OpcionesOID", opcionesOID);
        inputs.put("Seleccionadas", seleccionadas);
        Map out = spCondicionesSel.execute(inputs);
        
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                listado.add(genericObjectCondiciones(object));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }
    
    
    @Override
    public void crearCondicion(Condiciones o) {
        Map<String, Object> inputs = new TreeMap<>();
        
        inputs.put("SeccionesOID", o.getSeccionesOID());
        inputs.put("PreguntasOID", o.getPreguntasOID());
        inputs.put("OpcionesOID", o.getOpcionesOID());
        inputs.put("Seleccionadas", o.isSeleccionadas());
        inputs.put("SeccionesAccionesOID", o.getSeccionesAccionesOID());
        inputs.put("PreguntasAccionesOID", o.getPreguntasAccionesOID());
        inputs.put("Habilitar", o.getHabilitar());
        
        Map out =  spCondicionesUps.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                //Map element = (Map) object;
                //o.setId(Integer.valueOf(element.get("InsertedID").toString()));
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
    }
    
    /*GO
ALTER PROC [dbo].[udp_Condiciones_del]
	@SeccionesOID uniqueidentifier,
	@PreguntasOID uniqueidentifier,
	@OpcionesOID uniqueidentifier,
	@Seleccionadas bit =0
AS*/
    @Override
    public String eliminarCondicion(Condiciones o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("SeccionesOID", o.getSeccionesOID());
        inputs.put("PreguntasOID", o.getPreguntasOID());
        inputs.put("OpcionesOID", o.getOpcionesOID());
        inputs.put("Seleccionadas", o.isSeleccionadas());
        
        Map out = spCondicionesDel.execute(inputs);
        
        return resultado;
    }
    
    @Override
    public Condiciones genericObjectCondiciones(Object args) {
        Map element = (Map) args;
        Condiciones condicion = new Condiciones();
        
        condicion.setSeccionesOID( (element.get("SeccionesOID") == null ? "" : element.get("SeccionesOID")).toString() );
        condicion.setPreguntasOID( (element.get("PreguntasOID") == null ? "" : element.get("PreguntasOID")).toString() );
        condicion.setHabilitar( (int)(element.get("Habilitar") == null ? 0 : element.get("Habilitar")) );
        condicion.setNombre( (element.get("Nombre") == null ? "" : element.get("Nombre")).toString() );
        
        return condicion;
    }
    
    
    public DatabaseStoredProc getSpCondicionesSel() {
        return spCondicionesSel;
    }

    public void setSpCondicionesSel(DatabaseStoredProc spCondicionesSel) {
        this.spCondicionesSel = spCondicionesSel;
    }

    public DatabaseStoredProc getSpCondicionesUps() {
        return spCondicionesUps;
    }

    public void setSpCondicionesUps(DatabaseStoredProc spCondicionesUps) {
        this.spCondicionesUps = spCondicionesUps;
    }

    public DatabaseStoredProc getSpCondicionesDel() {
        return spCondicionesDel;
    }

    public void setSpCondicionesDel(DatabaseStoredProc spCondicionesDel) {
        this.spCondicionesDel = spCondicionesDel;
    }
    
    
    
    
}
