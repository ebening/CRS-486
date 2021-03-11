package com.crossmark.collector.presentation.views.utils;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Created by christian on 22/12/2014.
 */
public class DynamicField<T> implements Serializable {

    private T value;
    private boolean required;
    private List<SelectItem> selectItems;

    public DynamicField() {
    }

    public DynamicField(T value, boolean required, List<SelectItem> selectItems) {
        this.value = value;
        this.required = required;
        this.selectItems = selectItems;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

}
