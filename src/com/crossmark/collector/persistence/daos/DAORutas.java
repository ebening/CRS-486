package com.crossmark.collector.persistence.daos;

import java.util.List;
import java.util.Map;

//import com.crossmark.collector.business.domain.Proyectos;
import com.crossmark.collector.presentation.views.visitas.objects.*;
import java.util.HashMap;

public interface DAORutas {
	
    Ruta getRutaById(int rutaId);

    List<Map<String, Object>> listaRutasXTerritorio(int territorioId, int equipoId);
    
    HashMap<String, Object> eliminaRuta(int RutaId);
    
    HashMap<String, Object> guardaRuta(int territorioId, String nombreRuta, 
                int equipoId, int RutaId);
        
    HashMap<String, Object> guardaTiendasRutas(int RutaId, List<Tienda> tiendas);
        
    HashMap<String, Object> guardaPromotorRutas(int RutaId, List<Promotor> promotores);
    
    HashMap<String, Object> eliminaRutasTiendas(int RutaId, int TiendaId);
    
    HashMap<String, Object> eliminaRutasUsuarios(int RutaId, String usuarioOID);
        
    List<Map<String, Object>> listaTiendasXRuta(int RutaId, int TerritoriosId, 
                int UnidadesNegociosId, int EquiposId);

    List<Map<String, Object>> listaUsuariosXRuta(int RutaId, int TerritoriosId,
                int UnidadesNegociosId, int EquiposId);
    
    List<Map<String, Object>> listaRutasSel(int RutaId, String Nombre, int TerritoriosId, int EquiposId);
    
    List<Map<String, Object>> listaTiendasVal(int RutasId);
    
    List<Map<String, Object>> listaUsuariosVal (int RutasId);
}
