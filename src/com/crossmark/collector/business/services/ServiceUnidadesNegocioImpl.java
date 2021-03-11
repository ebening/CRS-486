package com.crossmark.collector.business.services;

import java.util.List;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.persistence.daos.DAOUnidadesNegocio;

public class ServiceUnidadesNegocioImpl implements ServiceUnidadesNegocio{
	
	DAOUnidadesNegocio daoUnidadesNegocio;

	public DAOUnidadesNegocio getDaoUnidadesNegocio() {
		return daoUnidadesNegocio;
	}

	public void setDaoUnidadesNegocio(DAOUnidadesNegocio daoUnidadesNegocio) {
		this.daoUnidadesNegocio = daoUnidadesNegocio;
	}

	@Override
	public List<UnidadesNegocio> listaUnidadesNegocio() {
		// TODO Auto-generated method stub
		return daoUnidadesNegocio.listaUnidadesNegocio();
	}

}
