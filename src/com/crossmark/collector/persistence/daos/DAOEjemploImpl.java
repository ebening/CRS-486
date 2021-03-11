package com.crossmark.collector.persistence.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

 

public class DAOEjemploImpl  implements DAOEjemplo{
	DatabaseStoredProc spPerfiles;

	public DatabaseStoredProc getSpPerfiles() {
		return spPerfiles;
	}

	public void setSpPerfiles(DatabaseStoredProc spPerfiles) {
		this.spPerfiles = spPerfiles;
	}

	@Override
	public String getPerfil(int perfilId) {
		// TODO Auto-generated method stub
		String perfilName="";
		List<Map<String, Object>> lstResult=null;
		Map<String, Object> inputs=new TreeMap<>();
		inputs.put("PerfilesId", perfilId);
		lstResult=spPerfiles.execSP(inputs);
		if(lstResult!=null){
                    
			Map record=(Map)lstResult.get(0);
			if( record!=null ){
				perfilName=(String)record.get("Nombre");
			}
	
		}else{
			//System.out.println("out is null ");
		}
		return perfilName;
		
	}
	
}
