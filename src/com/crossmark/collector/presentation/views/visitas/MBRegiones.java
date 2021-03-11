/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.visitas;

import com.crossmark.collector.presentation.views.reportesoperativos.objects.Region;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author christian
 */
public class MBRegiones {

    private List<Region> lista;

    public MBRegiones() {
        lista = new ArrayList<>();
    }

    public List<Region> getLista() {
        return lista;
    }

}
