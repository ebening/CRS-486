package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Cadena;

import java.util.List;

/**
 *
 * @author jdominguez
 */
public interface DAOCadena extends AbstractDAO<Cadena>{
    List getCadenas();
}
