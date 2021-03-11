package com.crossmark.collector.business.services;

import com.crossmark.collector.persistence.daos.DAOEjemplo;

public class ServiceEjemploImpl implements ServiceEjemplo {
	DAOEjemplo daoEjemplo;

	public DAOEjemplo getDaoEjemplo() {
		return daoEjemplo;
	}

	public void setDaoEjemplo(DAOEjemplo daoEjemplo) {
		this.daoEjemplo = daoEjemplo;
	}

	@Override
	public String getPerfil(int perfilId) {
		// TODO Auto-generated method stub
		return daoEjemplo.getPerfil(perfilId);
		
	}
	
	

}
