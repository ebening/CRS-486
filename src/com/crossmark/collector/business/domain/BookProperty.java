/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.business.domain;

import java.io.Serializable;

/**
 *
 * @author RIGG
 */
public class BookProperty implements Serializable {

    private String name;
    private Object value;
    private boolean required;

    public BookProperty(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    public BookProperty(String name, Object value, boolean required) {
        this.name = name;
        this.value = value;
        this.required = required;
    }

    // getter // setter
}
