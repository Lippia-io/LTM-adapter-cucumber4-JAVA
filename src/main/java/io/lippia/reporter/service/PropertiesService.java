package io.lippia.reporter.service;

import java.io.Serializable;
import java.util.Properties;

public class PropertiesService 
    implements Serializable {

    private static final long serialVersionUID = -5008231199972325650L;
    
    private static Properties properties;
    
    public static Object getProperty(String key) {
        String sys = System.getProperty(key);
        return sys == null ? (properties == null ? null : properties.get(key)) : sys;
    }
    
}
