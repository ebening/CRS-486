
package com.crossmark.collector.business.services;

import com.crossmark.collector.business.domain.Variable;
import com.crossmark.collector.persistence.daos.DAOJobs;
import com.crossmark.collector.persistence.daos.DAOVariables;
import java.util.List;

/**
 *
 * @author jdominguez
 */
public class ServiceJobsImpl implements ServiceJobs{
    
    DAOJobs daoJobs;

    @Override
    public void execDWEncuestaInstancia() {
        daoJobs.execDWEncuestaInstancia();
    }

    public DAOJobs getDaoJobs() {
        return daoJobs;
    }

    public void setDaoJobs(DAOJobs daoJobs) {
        this.daoJobs = daoJobs;
    }
    
    
    
}
