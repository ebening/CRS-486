/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.ModulosPerfiles;
import com.crossmark.collector.business.domain.Perfiles;
import java.util.List;

/**
 *
 * @author Francisco Mora
 */
public interface DAOPerfiles {
    
    public List<Perfiles> listaPerfiles(Integer perfilesId, Boolean activo);
    
    public Perfiles perfilPorId(Integer perfilesId);
    
    String nombrePerfil(Integer perfilesId);
    
    public void crearPerfil(Perfiles perfil);

    public void actualizarPerfil(Perfiles perfil);

    public String eliminarPerfil(Perfiles perfil);
    
    public List<ModulosPerfiles> listaModulosPorPerfil(Integer perfilesId);
    
    public void crearModuloPerfiles(Integer moduloPerfilId, Integer perfilesId);
    
    public String eliminarModulosPerfiles(Integer perfilesId);
    
}
