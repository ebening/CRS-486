package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.ModulosPerfiles;
import com.crossmark.collector.business.domain.Perfiles;
import java.util.List;


public interface ServicePerfiles {
    
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
