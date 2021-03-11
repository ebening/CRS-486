/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Cliente;
import com.crossmark.collector.business.domain.Clientes;
import java.util.List;

/**
 *
 * @author Usss
 */
public interface DAOClientes extends AbstractDAO<Cliente>{
    
    List<Clientes> listaClientes();
    
    public List<Clientes> listaClientesByParams(Integer clientesId, Integer unidadesNegociosId, Integer equiposId, Integer territoriosId, String usuariosOID);
}
