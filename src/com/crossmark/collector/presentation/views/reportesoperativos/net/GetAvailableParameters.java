/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.reportesoperativos.net;

import java.util.ArrayList;

/**
 *
 * @author Francisco Mora
 */
/*
*Clase para definicion de parametros que se incluiran en la url al hacer la peticion al servidor de reportes (Reporting services)
*/
public final class GetAvailableParameters {
    
    public static ArrayList getParameters(String reporte, String type){
        ArrayList params = new ArrayList();
        params.add("rs:Format");
        if(reporte != null ){
            if(reporte.equals("Dashboard") && type.equals("reporte") ){
                //Parametros especificos para Dashboard
                params.add("ClientesId");
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("FechaIni");
                params.add("FechaFin");
            }

            if((reporte.equals("ReporteVisitas") || reporte.equals("ReporteIncidencias") ) && type.equals("reporte") ){
                //Parametros especificos para ReporteVisitas
                params.add("ClientesId");
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("formato");
            }
            
            if((reporte.equals("ReporteCumplimiento") || reporte.equals("ReporteCumplimientoDetalle")) && type.equals("reporte") ){
                //Parametros especificos para ReporteCumplimiento y ReporteCumplimientoDetalle
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("ClientesId");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("Detalle");
                params.add("formato");
            }

            if(reporte.equals("ReporteCumplimiento") && type.equals("reporte") ){
                //Parametros especificos para ReporteCumplimiento
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("Detalle");
                params.add("formato");
                
            }

            if((reporte.equals("ReporteResultadosPlana") || reporte.equals("ReporteResultadosCiclica")) && type.equals("reporte") ){
                //Parametros especificos para ReporteResultadosPlana y ReporteResultadosCiclica
                params.add("ClientesId");
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("ProyectosId");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("SeccionesOID");
                params.add("UsuariosOID:isnull=true");
                params.add("formato");
            }
            
            if((reporte.equals("ReporteResultadosCompleto")) && type.equals("reporte") ){
                //Parametros especificos para ReporteResultadosPlana y ReporteResultadosCiclica
                params.add("ClientesId");
                params.add("EquiposId");
                params.add("TerritoriosId");
                params.add("RegionesId");
                params.add("ProyectosId");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("encuestasid");
                params.add("UsuariosOID:isnull=true");
                params.add("formato");
            }
            
            if((reporte.equals("Dashboard") || reporte.equals("ReporteVisitas") || reporte.equals("ReporteCumplimiento") || reporte.equals("ReporteCumplimientoDetalle") || reporte.equals("ReporteResultadosPlana") || reporte.equals("ReporteResultadosCiclica") || reporte.equals("ReporteResultadosCompleto"))  && type.equals("image") ){
                //Parametros especificos para accesar a las imagenes que se incluyen en el control image de las plantillas de reportes operativos
                params.add("rs:SessionID");
                params.add("rs:ImageID");
            }


            //Reportes de clientes
            if((reporte.equals("ReporteCliCumplimientoProyecto") ) && type.equals("reporte") ){
                //Parametros especificos para ReporteCliCumplimientoProyecto
                params.add("RegionesId");
                params.add("ClientesId:isnull=true");
                params.add("TerritoriosId:isnull=true");
                params.add("EquiposId:isnull=true");
                params.add("CadenasId:isnull=true");
                params.add("ProyectosId:isnull=true");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("UsuariosOID");
                params.add("Detalle");
                params.add("formato");
            }

            if((reporte.equals("ReporteCliCumplimientoProyectoDetalle") ) && type.equals("reporte") ){
                //Parametros especificos para ReporteCliCumplimientoProyectoDetalle
                params.add("RegionesId");
                params.add("ClientesId:isnull=true");
                params.add("TerritoriosId:isnull=true");
                params.add("EquiposId:isnull=true");
                params.add("CadenasId:isnull=true");
                params.add("ProyectosId:isnull=true");
                params.add("UnidadesNegociosID");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("formato");
            }

            if((reporte.equals("ReporteCliEjecucionGps") ) && type.equals("reporte") ){
                //Parametros especificos para ReporteCliEjecucionGps
                params.add("RegionesId");
                params.add("ClientesId:isnull=true");
                params.add("TerritoriosId:isnull=true");
                params.add("EquiposId:isnull=true");
                params.add("CadenasId:isnull=true");
                params.add("ProyectosId:isnull=true");
                params.add("TiendasId:isnull=true");
                //params.add("EncuestasId:isnull=true");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("UsuariosOID");
                params.add("formato");

            }

            if((reporte.equals("ReporteResultadosClienteCiclica")  || reporte.equals("ReporteResultadosClientePlanos") ) && type.equals("reporte") ){
                //Parametros especificos para ReporteResultadosClienteCiclica y ReporteResultadosClientePlanos
                params.add("ClientesId");
                params.add("RegionesId");
                params.add("TerritoriosId:isnull=true");
                params.add("EquiposId:isnull=true");
                params.add("ProyectosId:isnull=true");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("SeccionesOID");
                params.add("UsuariosOID");
                params.add("UsuariosPromotorOID:isnull=true");
                params.add("formato");
                
            }

            if((reporte.equals("ReporteCliImagenes")) && type.equals("reporte") ){
                //Parametros especificos para ReporteCliImagenes
                params.add("UsuariosOID");
                params.add("UsuariosPromotorOID:isnull=true");
                params.add("FechaIni");
                params.add("FechaFin");
                params.add("RegionesId");
                params.add("ClientesId:isnull=true");
                params.add("TerritoriosId:isnull=true");
                params.add("EquiposId:isnull=true");
                params.add("TiendasId:isnull=true");
                params.add("CadenasId:isnull=true");
                params.add("ProyectosId:isnull=true");
                //params.add("CategoriasOId");
                params.add("SortBy");
                params.add("formato");

            }
            
            if((reporte.equals("ReporteCliCumplimientoProyecto") || reporte.equals("ReporteCliCumplimientoProyectoDetalle") || 
                    reporte.equals("ReporteCliEjecucionGps") || reporte.equals("ReporteResultadosClienteCiclica") || reporte.equals("ReporteResultadosClientePlanos") 
                    || reporte.equals("ReporteCliImagenes") || reporte.equals("ReporteCliImagenes") )  && type.equals("image") ){
                //Parametros especificos para accesar a las imagenes que se incluyen en el control image de las plantillas de reportes del cliente.
                params.add("rs:SessionID");
                params.add("rs:ImageID");

            }
        }
        return params;
    }
}
