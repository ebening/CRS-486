package com.crossmark.collector.persistence.daos;

import com.crossmark.collector.business.domain.Lista;
import com.crossmark.collector.business.domain.OpcionCruzada;
import com.crossmark.collector.business.domain.OpcionPlana;
import com.crossmark.collector.presentation.views.utils.Utileria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.crossmark.collector.presentation.views.utils.Utileria.logger;

/**
 * Created by christian on 29/12/2014.
 */
public class DAOListaImpl implements DAOLista {


    private DatabaseStoredProc spListaSel;
    private DatabaseStoredProc spListaUps;
    private DatabaseStoredProc spGetListaByOID;
    private DAOTipoLista daoTipoLista;

    public DatabaseStoredProc getSpListaSel() {
        return spListaSel;
    }

    public void setSpListaSel(DatabaseStoredProc spListaSel) {
        this.spListaSel = spListaSel;
    }

    public DatabaseStoredProc getSpListaUps() {
        return spListaUps;
    }

    public void setSpListaUps(DatabaseStoredProc spListaUps) {
        this.spListaUps = spListaUps;
    }

    public DatabaseStoredProc getSpGetListaByOID() {
        return spGetListaByOID;
    }

    public void setSpGetListaByOID(DatabaseStoredProc spGetListaByOID) {
        this.spGetListaByOID = spGetListaByOID;
    }

    @Override
    public DAOTipoLista getDaoTipoLista() {
        return daoTipoLista;
    }

    public void setDaoTipoLista(DAOTipoLista daoTipoLista) {
        this.daoTipoLista = daoTipoLista;
    }

    @Override
    public void crear(Lista o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ListasOID", null);
        inputs.put("Nombre", o.getNombre());
        inputs.put("Clave", o.getClave());
        inputs.put("TipoListasId", o.getTipoLista().getId().intValue());
        if (o.getTipoLista().getId().intValue() == 1) {
            inputs.put("Catalogo", 1);
        } else if (o.getTipoLista().getId().intValue() == 2) {
            inputs.put("Catalogo", 0);
        }
        o.setOid(getOID(spListaUps.execute(inputs)));
        editarMiembros(o,false);
    }


    private String getOID(Map out) {
        String resultado = "";
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                Map element = (Map) object;
                resultado = element.get("InsertedID").toString();
            }
        } catch (Exception e) {
            logger(getClass()).error(e);
        }
        return resultado;
    }

    private void editarMiembros(Lista o,boolean esEditar) {


        if(esEditar){
            if (o.getTipoLista().getId().intValue() == 1) {
                for (OpcionPlana e : o.getOpcionPlanaList()) {
                    e.setOidLista(o.getOid());
                    daoTipoLista.getDaoOpcionPlana().editar(e);
                }
            } else if (o.getTipoLista().getId().intValue() == 2) {
                for (OpcionCruzada e : o.getOpcionCruzadasList()) {
                    e.setOidLista(o.getOid());
                    daoTipoLista.getDaoOpcionCruzada().editar(e);
                }
            }

        }else{
            if (o.getTipoLista().getId().intValue() == 1) {
                for (OpcionPlana e : o.getOpcionPlanaList()) {
                    e.setOidLista(o.getOid());
                    daoTipoLista.getDaoOpcionPlana().crear(e);
                }
            } else if (o.getTipoLista().getId().intValue() == 2) {
                for (OpcionCruzada e : o.getOpcionCruzadasList()) {
                    e.setOidLista(o.getOid());
                    daoTipoLista.getDaoOpcionCruzada().crear(e);
                }
            }
        }

    }

    @Override
    public void editar(Lista o) {
        Map<String, Object> inputs = new TreeMap<>();
        inputs.put("ListasOID", o.getOid());
        inputs.put("Nombre", o.getNombre());
        inputs.put("Clave", o.getClave());
        inputs.put("TipoListasId", o.getTipoLista().getId().intValue());
        if (o.getTipoLista().getId().intValue() == 1) {
            inputs.put("Catalogo", 1);
        } else if (o.getTipoLista().getId().intValue() == 2) {
            inputs.put("Catalogo", 0);
        }
        spListaUps.execute(inputs);
        editarMiembros(o,true);
    }

    @Override
    public String eliminar(Lista o) {
        return null;
    }

    @Override
    public Lista getById(Integer id) {
        return null;
    }


    @Override
    public List<Lista> getAll() {
        Map<String, Object> inputs = new TreeMap<>();
        List<Lista> listado = new ArrayList<>();
        Lista o;
        inputs.put("ListasOID", null);
        inputs.put("Catalogo", null);

        inputs.put("TipoListasId", 1);
        Map out = spListaSel.execute(inputs);
        inputs.put("TipoListasId", 2);
        Map out2 = spListaSel.execute(inputs);
        try {
            List list = (List) out.get("#result-set-1");
            List list2 = (List) out2.get("#result-set-1");

            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);
            }

            for (Object object : list2) {
                o = genericObject(object);
                listado.add(o);
            }

        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }

    @Override
    public List<Lista> getAllActivated() {
        List<Lista> listado = new ArrayList<>();
        Lista o;
        Map out = spListaSel.execute();
        try {
            List list = (List) out.get("#result-set-1");
            for (Object object : list) {
                o = genericObject(object);
                listado.add(o);

            }
        } catch (NumberFormatException e) {
            Utileria.logger(getClass()).info(e.getMessage());
        }
        return listado;
    }


    @Override
    public void editar2(Lista o) {
        Lista tmp;
        if((o.getOid().equals("noOid"))) {
            crear(o);
        }else{

            Map<String, Object> inputs = new TreeMap<>();
            List<Lista> listado = new ArrayList<>();
            Lista o2;
            inputs.put("ListasOID", o.getOid());
            inputs.put("Catalogo", null);

            inputs.put("TipoListasId", 1);
            Map out = spListaSel.execute(inputs);
            inputs.put("TipoListasId", 2);
            Map out2 = spListaSel.execute(inputs);
            try {
                List list = (List) out.get("#result-set-1");
                List list2 = (List) out2.get("#result-set-1");

                for (Object object : list) {
                    o2 = genericObject(object);
                    listado.add(o2);
                }

                for (Object object : list2) {
                    o2 = genericObject(object);
                    listado.add(o2);
                }

            } catch (Exception e) {
                Utileria.logger(getClass()).info(e.getMessage());
            }
            tmp =  listado.get(0);
            o.setOid(tmp.getOid());
            editar(o);
        }
    }

    @Override
    public Lista genericObject(Object args) {
        Map element = (Map) args;
        Lista o = new Lista();

        o.setOid(Utileria.validarStringNull(element.get("ListasOID")));
        o.setNombre(Utileria.validarStringNull(element.get("Nombre")));
        o.setClave(Utileria.validarStringNull(element.get("Clave")));
        o.getTipoLista().setId(Integer.valueOf(element.get("TipoListasId").toString()));
        o.getTipoLista().setNombre(Utileria.validarStringNull(element.get("Tipo Lista")));
        // o.setOpcionCruzadasList(daoOpcionCruzada.getByOIDLista(o.getId()));
        // o.setOpcionPlanaList(daoOpcionPlana.getByOIDLista(o.getId()));
        return o;
    }
}
