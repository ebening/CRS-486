package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;

public interface DAOCatalogos {
	
    List<Map<String, Object>> listaCatClientes();
	
	Integer guardaClienteCat(String nombreCliente, String unidadNegocio, String nombreContacto);
	
	Boolean eliminaClienteCat(Integer clienteId);


}
