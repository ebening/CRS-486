package com.crossmark.collector.persistence.daos;

import java.util.List;

import com.crossmark.collector.business.domain.UnidadesNegocio;

public interface DAOUnidadesNegocio extends AbstractDAO<UnidadesNegocio> {
	
	List<UnidadesNegocio> listaUnidadesNegocio();



}
