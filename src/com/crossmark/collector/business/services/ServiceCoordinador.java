package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.visitas.objects.Coordinador;
import java.util.List;

public interface ServiceCoordinador {

    public List<Coordinador> listaCoordinadores(int equiposId, int territoriosId);
    
    public boolean guardarCoordinador(int equiposId, int territoriosId, String usuariosOID);
}
