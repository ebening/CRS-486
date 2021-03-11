/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.presentation.views.utils.Utileria;
import com.crossmark.collector.presentation.views.visitas.objects.Ciudad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Francisco Mora
 */
public class DAOVariablesImpl implements DAOVariables{
    DatabaseStoredProc spVariablesSel;
    

    @Override
    public void crear(Variable o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar(Variable o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminar(Variable o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable getById(Integer id) {
        Utileria.logger(getClass()).info(" Estoy en getCiudadesByEstado");
        List<Variable> listado = new ArrayList<>();
        Variable variable;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("VariablesId", id);
        Map out =getSpVariablesSel().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                variable = genericObject(object);
                    listado.add(variable);
                }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<Variable> getAll() {
        Utileria.logger(getClass()).info(" Estoy en getAll variables");
        List<Variable> listado = new ArrayList<>();
        Variable variable;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("VariablesId", null);
        Map out =getSpVariablesSel().execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                variable = genericObject(object);
                listado.add(variable);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Variable> getAllActivated() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable genericObject(Object args) {
        Variable variable = new Variable();
        Map element = (Map)args;
        variable.setVariablesId(Integer.valueOf(String.valueOf(element.get("VariablesId"))));
        variable.setNombre(String.valueOf(element.get("Nombre")));
        
        return variable;
    }

    public DatabaseStoredProc getSpVariablesSel() {
        return spVariablesSel;
    }

    public void setSpVariablesSel(DatabaseStoredProc spVariablesSel) {
        this.spVariablesSel = spVariablesSel;
    }
    
}
