package com.kneel.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

public class PropertiesUtils {

	private static final Log log = LogFactory.getLog(PropertiesUtils.class);
	
	 /**
     * Matches .xml file extensions.
     */
    private static final Pattern dotXml = Pattern.compile(".+\\.[xX][mM][lL]");
	
	/**
	 * load Property file (class path) to Properties.
	 * 
	 * @param propFile
	 * @return
	 */
	public static Properties load(String propFile){
		Properties props = new Properties(); 
		InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(propFile);
		if (in == null) {
			try {
				in = new FileInputStream(propFile);
			} catch (FileNotFoundException e) {
				 throw new IllegalArgumentException(propFile + " not found.");
			} 
        }
		try { 
			 if (dotXml.matcher(propFile).matches()) {
	                props.loadFromXML(in);
	            } else {
	                props.load(in);
	            }
		} catch (IOException e) {
			log.error("can't load inputstream to properties, check file("+propFile+")");
		}finally {
            try {
				in.close();
			} catch (IOException e) {
				log.error("can't close InputStream, check file("+propFile+")");
			}
        }
		return props;
	}
	
	/**
	 * load Property as Java Map Class, easy to use.
	 * 
	 * @param propFile
	 * @return
	 */
	public static Map<String,String> loadForMap(String propFile){
		Map<String,String> propmap = new HashMap<String,String>();
		Properties props = load(propFile); 
		for(Entry<Object, Object> prop:props.entrySet()){
			propmap.put((String)prop.getKey(), (String)prop.getValue());
		}
		return propmap;
		
	}
}
