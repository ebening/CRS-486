package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.UnidadesNegocio;
import com.crossmark.collector.presentation.views.utils.DateUtil;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.io.Serializable;
import java.util.*;

public class DAOUnidadesNegocioImpl implements DAOUnidadesNegocio, Serializable {

    private DatabaseStoredProc spUnidadesNegocio;
    private DatabaseStoredProc spUnidadesNegociosUps;
    private DatabaseStoredProc spUnidadesNegociosDel;

    public DatabaseStoredProc getSpUnidadesNegocio() {
        return spUnidadesNegocio;
    }

    public void setSpUnidadesNegocio(DatabaseStoredProc spUnidadesNegocio) {
        this.spUnidadesNegocio = spUnidadesNegocio;
    }

    public DatabaseStoredProc getSpUnidadesNegociosUps() {
        return spUnidadesNegociosUps;
    }

    public void setSpUnidadesNegociosUps(DatabaseStoredProc spUnidadesNegociosUps) {
        this.spUnidadesNegociosUps = spUnidadesNegociosUps;
    }

    public DatabaseStoredProc getSpUnidadesNegociosDel() {
        return spUnidadesNegociosDel;
    }

    public void setSpUnidadesNegociosDel(DatabaseStoredProc spUnidadesNegociosDel) {
        this.spUnidadesNegociosDel = spUnidadesNegociosDel;
    }

    @Override
    public List<UnidadesNegocio> listaUnidadesNegocio() {
        
        List<UnidadesNegocio> lista = new ArrayList<UnidadesNegocio>();

        UnidadesNegocio unidades;

		/*Map<String, Object> inputs=new TreeMap<>();

		

		
		inputs.put("unidadId", new Integer(1));	*/


        Map out;

        try {
            out = spUnidadesNegocio.execute();
            if (out != null) {
                int tamanio = out.size();

                if (tamanio == 0 || tamanio == -1) {

                } else {


                    Set keySet = out.keySet();
                    Iterator it = keySet.iterator();
                    String key;
                    Object val;
                    while (it.hasNext()) {
                        key = (String) it.next();
                        val = out.get(key);

                        if (val.equals("")) {

                        } else {
                            List arrValues = (List) val;
                            int tamanioLista = arrValues.size();
                            if (tamanioLista == 0) {

                            } else {
                                if (arrValues != null) {

                                    for (int i = 0; i < arrValues.size(); i++) {

                                        Map record = (Map) arrValues.get(i);
                                        if (record != null) {

                                            unidades = new UnidadesNegocio();

                                            unidades.setIdUnidadNegocio((Integer) record.get("UnidadesNegociosId"));
                                            unidades.setNombreUnidad((String) record.get("Nombre"));
                                            lista.add(unidades);
                                        }

                                    }


                                }

                            }


                        }


                    }

                }


            } else {
                //System.out.println("out is null ");
            }
        } catch (Exception se) {
            se.printStackTrace();
        }


        return lista;
    }
    /***
     *     <!--

     @UnidadesNegociosId
     @Nombre
     @HoraIni
     @HoraFin
     -->
     *
     * */


    @Override
    public void crear(UnidadesNegocio o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", 0);
        inputs.put("Nombre", o.getNombreUnidad());
        inputs.put("HoraIni", o.getdHoraIni());
        inputs.put("HoraFin", o.getdHoraFin());
        spUnidadesNegociosUps.execute(inputs);
    }

    @Override
    public void editar(UnidadesNegocio o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", o.getIdUnidadNegocio());
        inputs.put("Nombre", o.getNombreUnidad());
        inputs.put("HoraIni", o.getdHoraIni());
        inputs.put("HoraFin", o.getdHoraFin());
        spUnidadesNegociosUps.execute(inputs);
    }

    @Override
    public String eliminar(UnidadesNegocio o) {
        String resultado = "";
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", o.getIdUnidadNegocio());
        Map out = spUnidadesNegociosDel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("").toString();
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return resultado;
    }

    @Override
    public UnidadesNegocio getById(Integer id) {
        List<UnidadesNegocio> listado = new ArrayList<>();
        UnidadesNegocio unidadesNegocio;
        String horaInit = null;
        String horaFin = null;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", id);
        inputs.put("Nombre", null);
        inputs.put("TerritoriosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClienteId", null);

        Map out = spUnidadesNegocio.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                unidadesNegocio = genericObject(object);
                listado.add(unidadesNegocio);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado.get(0);
    }

    @Override
    public List<UnidadesNegocio> getAll() {
        List<UnidadesNegocio> listado = new ArrayList<>();
        UnidadesNegocio unidadesNegocio;
        String horaInit = null;
        String horaFin = null;
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", null);
        inputs.put("Nombre", null);
        inputs.put("TerritoriosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClienteId", null);

        Map out = spUnidadesNegocio.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {

                unidadesNegocio = genericObject(object);

                listado.add(unidadesNegocio);
            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;
    }

    @Override
    public List<UnidadesNegocio> getAllActivated() {
        List<UnidadesNegocio> listado = new ArrayList<>();
        UnidadesNegocio unidadesNegocio;

        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("UnidadesNegociosId", null);
        inputs.put("Nombre", null);
        inputs.put("TerritoriosId", null);
        inputs.put("EquiposId", null);
        inputs.put("ClienteId", null);

        Map out = spUnidadesNegocio.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
               unidadesNegocio = genericObject(object);
                if(unidadesNegocio.isActivo()){
                    listado.add(unidadesNegocio);
                }

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).error(e + " " + e.getMessage());
        }
        return listado;



    }


    @Override
    public UnidadesNegocio genericObject(Object args) {
        UnidadesNegocio nvo = new UnidadesNegocio();
        Map element = (Map) args;
        String horaInit = null;
        String horaFin = null;

        nvo.setIdUnidadNegocio(Integer.valueOf(element.get("UnidadesNegociosID").toString()));
        nvo.setNombreUnidad(element.get("Nombre").toString());
        nvo.setActivo(Boolean.valueOf(element.get("Activo").toString()));

        horaFin = (element.get("HoraFin") == null) ? "" : DateUtil.toString(element.get("HoraFin"));
        horaInit = (element.get("HoraIni") == null) ? "" : DateUtil.toString(element.get("HoraIni"));

        nvo.setHoraFin(horaFin);//para la tabla
        nvo.setHoraIni(horaInit);//para la tabla

        nvo.setdHoraFin(Utileria.horaFormat(horaFin)); //para crear / editar
        nvo.setdHoraIni(Utileria.horaFormat(horaInit)); //para crear / editar

        return nvo;
    }



}
