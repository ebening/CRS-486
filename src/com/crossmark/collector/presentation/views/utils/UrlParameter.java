/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crossmark.collector.presentation.views.utils;

import java.io.Serializable;

/**
 *
 * @author christian
 */
public class UrlParameter implements Serializable{

        private String key;
        private String value;

        public UrlParameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public UrlParameter() {
        }
        
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }
        
        
    }
