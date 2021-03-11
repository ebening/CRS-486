/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossmark.collector.presentation.views.utils;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;

/**
 *
 * @author Usss
 */
public class ResourceBundleManager {
	public static ResourceBundle BUNDLE_SQL= ResourceBundle.getBundle("ConfigurationDB");
	Logger LOG = Logger.getLogger(ResourceBundleManager.class);
	public ResourceBundleManager()
	{
		/**this.BUNDLE_SQL = ResourceBundle.getBundle("ConfigurationDB");*/
	}
	
	public String getValue(String key)
	{
		String message = "";
		try
		{ 
			message = BUNDLE_SQL.getString(key);
		}
		catch(Exception ex)
		{
			LOG.error(ex);	
		}
		
		return message;
	}
	
	public static void main(String[] args) {
		
		/**ResourceBundleManager bundleManager = new ResourceBundleManager();
		System.out.println(bundleManager.getValue("db2.driver"));*/
	}
}
