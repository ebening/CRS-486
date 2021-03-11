package com.crossmark.collector.persistence.daos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by christian on 05/12/2014.
 * @param <T>
 */
public interface AbstractDAO<T> extends Serializable {

    public void crear(T o);
    public void editar(T o);
    public String eliminar(T o);
    public T getById(Integer id);
    public List<T> getAll();
    public List<T> getAllActivated();

    public  T genericObject(Object args);
}
