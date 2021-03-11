package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.crossmark.collector.presentation.views.visitas.objects.Ruta;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.dao.DuplicateKeyException;

public class DAORutasImpl implements DAORutas, Serializable {
	DatabaseStoredProc spRutasTiendasUsuarios;
        DatabaseStoredProc spGuardaRuta;
        DatabaseStoredProc spRutasUsuarios;
        DatabaseStoredProc spRutasTiendas;
        DatabaseStoredProc spRutasUsuariosSel;
        DatabaseStoredProc spRutasTiendasSel;
        DatabaseStoredProc spRutasUsuariosDel;
        DatabaseStoredProc spRutasTiendasDel;
        DatabaseStoredProc spRutasSel;
        DatabaseStoredProc spRutasDel;
        DatabaseStoredProc spRutasTiendasVal;
        DatabaseStoredProc spRutasUsuariosVal;

    @Override
    public Ruta getRutaById(int rutaId) {
		int equipoId = 0;
		Ruta rutaName = new Ruta();
		
		// TODO Auto-generated method stub
		Map<String, Object> inputs=new TreeMap<>();
		inputs.put("RutaId", rutaId);
		//inputs.put("EquipoId",equipoId);
		
		Map out = spRutasTiendasUsuarios.execute(inputs);
		
        //out.get("#result-set-1");
		
		if(out!=null ){
			int tamanio = out.size();
			
			if(tamanio == 0 || tamanio == -1){
				
			}else{
				Set keySet= out.keySet();
				Iterator it= keySet.iterator();
				String key;
				Object val;
				while(it.hasNext()) {
				   key=(String)it.next();
							   
				   rutaName.setId(rutaId);
				   rutaName.setNombre((String)out.get("Nombre"));
				}   
			}
		}else{
			//System.out.println("out is null ");
		}
		return rutaName;
	}
    
    @Override
    public HashMap<String, Object> eliminaRutasTiendas(int RutaId, int TiendaId) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("RutasId", RutaId);
        inputs.put("TiendasId", TiendaId);
        List<Map<Integer, Object>> response = getSpRutasTiendasDel().execSPI(inputs);
        r.put("Exito", true);
        r.put("Msj", "Tienda Eliminada");
        return r;
    }

    @Override
    public HashMap<String, Object> eliminaRutasUsuarios(int RutaId, String usuarioOID) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("RutasId", RutaId);
        inputs.put("UsuariosOID", usuarioOID);
        List<Map<Integer, Object>> response = getSpRutasUsuariosDel().execSPI(inputs);
        r.put("Exito", true);
        r.put("Msj", "Usuarios Eliminado");
        return r;
    }

    @Override
    public HashMap<String, Object> eliminaRuta(int RutaId) {
        HashMap<String, Object> r = new HashMap<>();
        Map<String, Object> inputs = new TreeMap();
        inputs.put("RutasId", RutaId);
        List<Map<String, Object>> response = getSpRutasDel().execSP(inputs);
        String resp = (String)response.get(0).get("");
        if(resp.equals("0")){
            r.put("Exito", true);
            r.put("Msj", "Ruta Eliminada");
        }else{
            r.put("Exito", false);
            r.put("Msj", "Las rutas no pueden ser eliminadas debido a que es posible que tengan visitas programadas.");
        }
        return r;
    }
    
    @Override
    public List<Map<String, Object>> listaRutasXTerritorio(int territorioId, int equipoId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("RutasId", null);
        inputs.put("Nombre", null);
        inputs.put("TerritoriosId", territorioId);
        inputs.put("EquiposId", equipoId);
        listado = getSpRutasTiendasUsuarios().execSP(inputs);
            return listado;
    }

    @Override
    public HashMap<String, Object> guardaRuta(int territorioId, String nombreRuta,
            int equipoId, int RutaId) {
		
	HashMap<String, Object> insertado = new HashMap<>();
		
	Map<String, Object> inputs=new TreeMap<>();
	inputs.put("RutasId", RutaId);
	inputs.put("Nombre", nombreRuta);
	inputs.put("EquiposId", equipoId);
        inputs.put("TerritoriosId", territorioId);
	
	List<Map<Integer, Object>> out;
		
	try{
            out = getSpGuardaRuta().execSPI(inputs);
            BigDecimal bd = (BigDecimal) (out == null ? new BigDecimal(RutaId) : out.get(0).get("InsertedID"));
            int idRuta = bd.intValue();
            insertado.put("Exito", true);
            insertado.put("Msj", "Ruta Guardada - Id: " + idRuta);
            insertado.put("RutaId", idRuta);
	}catch(Exception se){
            //System.out.println(se.getMessage());
	}

	return insertado;
    }
    
    @Override
    public HashMap<String, Object> guardaTiendasRutas(int RutaId, List<Tienda> tiendas){
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        List<Map<Integer, Object>> response;
        String msj = "Tiendas Guardadas";
        if(tiendas.isEmpty()){
            r.put("Exito", false);
            r.put("Msj", "Lista Tiendas Vacia");
        }else{
            for(Tienda t : tiendas){
                inputs.put("TiendasId", t.getId());
                inputs.put("RutasId", RutaId);
                try{
                    response = getSpRutasTiendas().execSPI(inputs);
                    r.put("Exito", true);
                    r.put("Msj", msj);
                }catch(DuplicateKeyException ex){
                    r.put("Exito", false);
                    r.put("Msj", ex.getMessage());
                    return r;
                }
            }
        }
        return r;
    }
    
    @Override
    public HashMap<String, Object> guardaPromotorRutas(int RutaId, List<Promotor> promotores){
        Map<String, Object> inputs = new TreeMap<>();
        HashMap<String, Object> r = new HashMap<>();
        String msj = "Promotores Guardados";
        for(Promotor p : promotores){
            inputs.put("UsuariosOID", p.getOID());
            inputs.put("RutasId", RutaId);
            List<Map<Integer, Object>> response = getSpRutasUsuarios().execSPI(inputs);
        }
        r.put("Exito", true);
        r.put("Msj", msj);
        return r;
    }

    public DatabaseStoredProc getSpRutas() {
	return spRutasTiendasUsuarios;
    }

    public void setSpRutas(DatabaseStoredProc spRutas) {
	this.spRutasTiendasUsuarios = spRutas;
    }
    
    @Override
    public List<Map<String, Object>> listaTiendasXRuta(int RutaId, int TerritoriosId, int UnidadesNegociosId, int EquiposId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("RutasId", RutaId == 0 ? null : RutaId);
        inputs.put("UnidadesNegociosId", UnidadesNegociosId);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);
        listado = getSpRutasTiendasSel().execSP(inputs);
        return listado;
    }

    @Override
    public List<Map<String, Object>> listaUsuariosXRuta(int RutaId, int TerritoriosId, int UnidadesNegociosId, int EquiposId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs=new TreeMap<>();
        inputs.put("RutasId", RutaId == 0 ? null : RutaId);
        inputs.put("UnidadesNegociosId", UnidadesNegociosId);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);
        inputs.put("Pertenece", null);
        listado = getSpRutasUsuariosSel().execSP(inputs);
        return listado;
    }

    @Override
    public List<Map<String, Object>> listaRutasSel(int RutaId, String Nombre, int TerritoriosId, int EquiposId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RutasId", RutaId == 0 ? null : RutaId);
        inputs.put("Nombre", Nombre);
        inputs.put("TerritoriosId", TerritoriosId);
        inputs.put("EquiposId", EquiposId);
        listado = getSpRutasSel().execSP(inputs);
        return listado;
    }
    
    @Override
    public List<Map<String, Object>> listaTiendasVal(int RutasId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RutasId", RutasId);
        listado = getSpRutasTiendasVal().execSP(inputs);
        return listado;
    }

    @Override
    public List<Map<String, Object>> listaUsuariosVal(int RutasId) {
        List<Map<String, Object>> listado;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("RutasId", RutasId);
        listado = getSpRutasUsuariosVal().execSP(inputs);
        return listado;
    }
    
    /**
     * @return the spGuardaProyecto
     */
    public DatabaseStoredProc getSpGuardaRuta() {
        return spGuardaRuta;
    }

    /**
     * @param spGuardaRuta
     */
    public void setSpGuardaRuta(DatabaseStoredProc spGuardaRuta) {
        this.spGuardaRuta = spGuardaRuta;
    }

    public DatabaseStoredProc getSpRutasUsuarios() {
        return spRutasUsuarios;
    }

    public void setSpRutasUsuarios(DatabaseStoredProc spRutasUsuarios) {
        this.spRutasUsuarios = spRutasUsuarios;
    }

    public DatabaseStoredProc getSpRutasTiendas() {
        return spRutasTiendas;
    }

    public void setSpRutasTiendas(DatabaseStoredProc spRutasTiendas) {
        this.spRutasTiendas = spRutasTiendas;
    }

    public DatabaseStoredProc getSpRutasUsuariosSel() {
        return spRutasUsuariosSel;
    }

    public void setSpRutasUsuariosSel(DatabaseStoredProc spRutasUsuariosSel) {
        this.spRutasUsuariosSel = spRutasUsuariosSel;
    }

    public DatabaseStoredProc getSpRutasTiendasSel() {
        return spRutasTiendasSel;
    }

    public void setSpRutasTiendasSel(DatabaseStoredProc spRutasTiendasSel) {
        this.spRutasTiendasSel = spRutasTiendasSel;
    }

    public DatabaseStoredProc getSpRutasSel() {
        return spRutasSel;
    }

    public void setSpRutasSel(DatabaseStoredProc spRutasSel) {
        this.spRutasSel = spRutasSel;
    }

    public DatabaseStoredProc getSpRutasDel() {
        return spRutasDel;
    }

    public void setSpRutasDel(DatabaseStoredProc spRutasDel) {
        this.spRutasDel = spRutasDel;
    } 

    public DatabaseStoredProc getSpRutasTiendasUsuarios() {
        return spRutasTiendasUsuarios;
    }

    public void setSpRutasTiendasUsuarios(DatabaseStoredProc spRutasTiendasUsuarios) {
        this.spRutasTiendasUsuarios = spRutasTiendasUsuarios;
    }

    public DatabaseStoredProc getSpRutasUsuariosDel() {
        return spRutasUsuariosDel;
    }

    public void setSpRutasUsuariosDel(DatabaseStoredProc spRutasUsuariosDel) {
        this.spRutasUsuariosDel = spRutasUsuariosDel;
    }

    public DatabaseStoredProc getSpRutasTiendasDel() {
        return spRutasTiendasDel;
    }

    public void setSpRutasTiendasDel(DatabaseStoredProc spRutasTiendasDel) {
        this.spRutasTiendasDel = spRutasTiendasDel;
    }

    public DatabaseStoredProc getSpRutasTiendasVal() {
        return spRutasTiendasVal;
    }

    public void setSpRutasTiendasVal(DatabaseStoredProc spRutasTiendasVal) {
        this.spRutasTiendasVal = spRutasTiendasVal;
    }

    public DatabaseStoredProc getSpRutasUsuariosVal() {
        return spRutasUsuariosVal;
    }

    public void setSpRutasUsuariosVal(DatabaseStoredProc spRutasUsuariosVal) {
        this.spRutasUsuariosVal = spRutasUsuariosVal;
    }
 
}

