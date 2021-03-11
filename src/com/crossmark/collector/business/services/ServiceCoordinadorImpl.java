package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.visitas.objects.Coordinador;
import com.crossmark.collector.persistence.daos.DAOCoordinador;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.List;

public class ServiceCoordinadorImpl implements ServiceCoordinador{
    
    private DAOCoordinador daoCoordinador;

    public ServiceCoordinadorImpl() {
    }

    @Override
    public List<Coordinador> listaCoordinadores(int equiposId, int territoriosId) {
        
        Utileria.logger(this.getClass()).info("Obteniendo lista de coordinadores... equipos:" + equiposId + " , territorios:" + territoriosId);
        
        return daoCoordinador.listaCoordinadores(equiposId, territoriosId);
    }

    @Override
    public boolean guardarCoordinador(int equiposId, int territoriosId, String usuariosOID) {
        Utileria.logger(this.getClass()).info("Modificando coordinador...");
        
        return daoCoordinador.guardarCoordinador(equiposId, territoriosId, usuariosOID);
    }
    
    public DAOCoordinador getDaoCoordinador() {
        return daoCoordinador;
    }

    public void setDaoCoordinador(DAOCoordinador daoCoordinador) {
        this.daoCoordinador = daoCoordinador;
    }
    
    
}
