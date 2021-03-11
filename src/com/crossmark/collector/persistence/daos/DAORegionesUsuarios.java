/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportescliente.objects.RegionesUsuarios;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAORegionesUsuarios {
    
    List<RegionesUsuarios> getListaRegionesPorUsuario(String usuariosOID);
    
}
