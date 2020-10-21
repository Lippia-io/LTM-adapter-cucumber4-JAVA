package io.lippia.reporter.service;

import java.io.Serializable;
import java.util.Properties;

public class PropertiesService implements Serializable {

    private static final long serialVersionUID = -5008231199972325650L;
    
    private static Properties properties;
    
    public static Object getProperty(String key) {
        String sys = System.getProperty(key);
        
        if(sys != null) {
        	return sys;
        }
        
        if(properties != null) {
        	return properties.get(key);
        }
        
        return null;
    }
    
}
