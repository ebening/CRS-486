package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Coordinador;
import java.util.List;

public interface DAOCoordinador {
    
    public List<Coordinador> listaCoordinadores(int equiposId, int territoriosId);
    
    public boolean guardarCoordinador(int equiposId, int territoriosId, String usuariosOID);
    
}
