package com.crossmark.collector.business.services;

import com.crossmark.collector.presentation.views.visitas.objects.Promotor;
import java.util.List;
import java.util.Map;

import com.crossmark.collector.presentation.views.visitas.objects.Ruta;
import com.crossmark.collector.presentation.views.visitas.objects.Tienda;
import java.util.HashMap;

public interface ServiceRutas {
	
	Ruta getRutaById(int rutaId);

	List<Map<String, Object>> listaRutasXTerritorio(int territorioId, int equipoId);
        
        HashMap<String, Object> eliminaRuta(int RutaId);
        
        HashMap<String, Object> eliminaRutasTiendas(int RutaId, int TiendaId);
        
        HashMap<String, Object> eliminaRutasUsuario(int RutaId, String usuarioOID);
        
	HashMap<String, Object> guardaRuta(int territorioId, String nombreRuta, Integer equiposId, int RutaId);
        
        HashMap<String, Object> guardaTiendasRutas(int RutaId, List<Tienda> tiendas);
        
        HashMap<String, Object> guardaPromotoresRutas(int RutaId, List<Promotor> promotores);
        
        List<Map<String, Object>> listaTiendasXRuta(int RutaId, int TerritoriosId,
                int UnidadesNegociosId, int EquiposId);
        
        List<Map<String, Object>> listaUsuariosXRuta(int RutaId, int TerritoriosId,
                int UnidadesNegociosId, int EquiposId);
        
        List<Map<String, Object>> listaRutasSel(int RutaId, String Nombre,  int TerritoriosId, int EquiposId);
        
        List<Map<String, Object>> listaTiendasVal(int RutasId);
        
        List<Map<String, Object>> listaUsuariosVal(int RutasId);
}
