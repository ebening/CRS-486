
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.persistence.daos.DAOVariables;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class ServiceVariablesImpl implements ServiceVariables{
    
    DAOVariables daoVariables;
    
    public DAOVariables getDaoVariables() {
        return daoVariables;
    }

    public void setDaoVariables(DAOVariables daoVariables) {
        this.daoVariables = daoVariables;
    }

    @Override
    public Variable getVariableById(Integer id) {
        return getDaoVariables().getById(id);
    }

    @Override
    public List<Variable> getAllVariables() {
        return getDaoVariables().getAll();
    }
    
}
