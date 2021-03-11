package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Coordinador;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.springframework.dao.DataAccessException;


public class DAOCoordinadorImpl implements DAOCoordinador {
    
    private DatabaseStoredProc spCoordinadorUps;
    private DatabaseStoredProc spCoordinadorSel;

    public DAOCoordinadorImpl() {
    }

    @Override
    public List<Coordinador> listaCoordinadores(int equiposId, int territoriosId) {
        
        Utileria.logger(this.getClass()).info("Obteniendo lista de coordinadores... equipos:" + equiposId + " , territorios:" + territoriosId);
        
        List<Coordinador> coordinadores = new ArrayList<>();

        Map<String, Object> inputs = new HashMap<>();

        inputs.put("EquiposId", equiposId == 0 ? null : equiposId);
        inputs.put("TerritoriosId", territoriosId == 0 ? null : territoriosId);

        Map<String, Object> out = getSpCoordinadorSel().execute(inputs);

        Object resultset = null;

        if (out.isEmpty() == false) {
            // Obtiene primer resultset
            resultset = out.values().iterator().next();
        }
        if (resultset != null && resultset instanceof List) {
            for (Object object : (List) resultset) {
                if (object instanceof Map) {
                    Map registro = (Map) object;
                    Coordinador coordinador = new Coordinador();

                    coordinador.setUsuariosOID(MapUtils.getString(registro, "UsuariosOID", ""));
                    coordinador.setNombreUsuario(MapUtils.getString(registro, "nombreUsuario", ""));
                    coordinador.setPerfilesId(MapUtils.getIntValue(registro, "PerfilesId", 0));
                    coordinador.setNombrePerfil(MapUtils.getString(registro, "nombrePerfil", ""));
                    coordinador.setEquiposId(MapUtils.getIntValue(registro, "EquiposId", 0));
                    coordinador.setTerritoriosId(MapUtils.getIntValue(registro, "TerritoriosId", 0));
                    coordinador.setSelected(MapUtils.getBooleanValue(registro, "selected", false));
                    
                    coordinadores.add(coordinador);
                }
            }
        }

        return coordinadores;
    }

    @Override
    public boolean guardarCoordinador(int equiposId, int territoriosId, String usuariosOID) {
        Utileria.logger(this.getClass()).info("Modificando coordinador...");
        
        Map<String, Object> inputs = new HashMap<>();

        inputs.put("EquiposId", equiposId);
        inputs.put("TerritoriosId", territoriosId);
        inputs.put("UsuariosOID", usuariosOID);

        try {
            getSpCoordinadorUps().execute(inputs);
            return true;
        } catch (DataAccessException ex) {
            Utileria.logger(this.getClass()).info(ex);
            return false;
        }
    }

    public DatabaseStoredProc getSpCoordinadorUps() {
        return spCoordinadorUps;
    }

    public void setSpCoordinadorUps(DatabaseStoredProc spCoordinadorUps) {
        this.spCoordinadorUps = spCoordinadorUps;
    }

    public DatabaseStoredProc getSpCoordinadorSel() {
        return spCoordinadorSel;
    }

    public void setSpCoordinadorSel(DatabaseStoredProc spCoordinadorSel) {
        this.spCoordinadorSel = spCoordinadorSel;
    }
    
    
}
