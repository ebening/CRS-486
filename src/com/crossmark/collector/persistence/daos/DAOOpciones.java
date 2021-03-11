/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Opciones;
import com.crossmark.collector.business.domain.TipoLista;

import java.util.List;

/**
 *
 * @author RIGG
 */
public interface DAOOpciones{

    public List<Opciones> traerOpciones(String unCatalogoOID, String opcionesOID);

    public DAOLista getDaoLista();
}
