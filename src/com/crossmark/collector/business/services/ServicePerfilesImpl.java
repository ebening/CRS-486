package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.ModulosPerfiles;
import com.crossmark.collector.persistence.daos.DAOPerfiles;
import com.crossmark.collector.business.domain.Perfiles;
import com.crossmark.collector.presentation.views.utils.Utileria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServicePerfilesImpl implements ServicePerfiles, Serializable {
    
    private DAOPerfiles daoPerfiles;
    
    @Override
    public List<Perfiles> listaPerfiles(Integer perfilesId, Boolean activo) {
        return daoPerfiles.listaPerfiles(perfilesId, activo);
    }

    @Override
    public Perfiles perfilPorId(Integer perfilesId) {
        return daoPerfiles.perfilPorId(perfilesId);
    }

    @Override
    public String nombrePerfil(Integer perfilesId) {
        return daoPerfiles.nombrePerfil(perfilesId);
    }

    @Override
    public void crearPerfil(Perfiles perfil) {
        daoPerfiles.crearPerfil(perfil);
    }

    @Override
    public void actualizarPerfil(Perfiles perfil) {
        daoPerfiles.actualizarPerfil(perfil);
    }

    @Override
    public String eliminarPerfil(Perfiles perfil) {
        return daoPerfiles.eliminarPerfil(perfil);
    }
    
    @Override
    public List<ModulosPerfiles> listaModulosPorPerfil(Integer perfilesId){
        return daoPerfiles.listaModulosPorPerfil(perfilesId);
    }
    @Override
    public void crearModuloPerfiles(Integer moduloPerfilId , Integer perfilesId){
        daoPerfiles.crearModuloPerfiles(moduloPerfilId, perfilesId);
    }
    
    @Override
    public String eliminarModulosPerfiles(Integer perfilesId){
        return daoPerfiles.eliminarModulosPerfiles(perfilesId);
    }
    
    public DAOPerfiles getDaoPerfiles() {
        return daoPerfiles;
    }

    public void setDaoPerfiles(DAOPerfiles daoPerfiles) {
        this.daoPerfiles = daoPerfiles;
    }
    
    
    
}
