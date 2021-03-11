/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author franciscom
 */
public interface DAORegiones  extends  AbstractDAO<Region> {

    public List<Region> getRegionesByFind(Integer regionId);
}
