package com.crossmark.collector.persistence.daos;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.object.StoredProcedure;

public class DatabaseStoredProc extends StoredProcedure implements Serializable{

    public List<Map<String, Object>> execSP(Map inputs){
    	 List<Map<String, Object>> lstResult=null;
    	 Map out=null;
    	try{
          out=super.execute(inputs);
          
  		if(out!=null){
			Set keySet= out.keySet();
			Iterator it= keySet.iterator();
			String key;
			Object val;
			
			if(it.hasNext()){
			   key=(String)it.next();
			   val=out.get(key);
			   //List<Map<String,Object>> arrValues=(List)val;
			   lstResult=(List<Map<String, Object>>)val;  
			}
		}else{
			//System.out.println("out is null ");
		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return lstResult;
    }
    
    public List<Map<String, Object>> executeSP(Map inputs) throws Exception{
        List<Map<String, Object>> lstResult=null;
    	Map out=null;
        out=super.execute(inputs);
        if(out!=null){
            Set keySet= out.keySet();
            Iterator it= keySet.iterator();
            String key;
            Object val;
			
            if(it.hasNext()){
		key=(String)it.next();
		val=out.get(key);
		//List<Map<String,Object>> arrValues=(List)val;
		lstResult=(List<Map<String, Object>>)val;  
            }
	}else{
            //System.out.println("out is null ");
	}
        return lstResult;
    }
    
    
    
    
    public List<Map<Integer, Object>> execSPI(Map inputs){
    	 List<Map<Integer, Object>> lstResult=null;
    	 Map out=null;
    	try{
          out=super.execute(inputs);
          
  		if(out!=null){
			Set keySet= out.keySet();
			Iterator it= keySet.iterator();
			String key;
			Object val;
			
			if(it.hasNext()){
			   key=(String)it.next();
			   //System.out.println(key)	;
			   val=out.get(key);
			   //System.out.println(val);
			   //List<Map<String,Object>> arrValues=(List)val;
			   lstResult=(List<Map<Integer, Object>>)val;
			 
			   
			}
			
			
		}else{
			//System.out.println("out is null ");
		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return lstResult;
    }

 
}