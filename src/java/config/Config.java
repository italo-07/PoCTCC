/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leoomoreira
 */
public final class Config {

    public static final String UPLOAD_TEMP = getPropertyValue("upload.temp");
    public static final String UPLOAD_DATARAW = getPropertyValue("upload.dataraw");
    public static final String RESULT_SAVE = getPropertyValue("result.save");
    
    
    private Config() {

    }
    
    private static final String getPropertyValue(String key) {
        Properties properties = new Properties();
        try {
            properties.load(Config.class.getResourceAsStream("config.properties"));
            return properties.getProperty(key);
        } catch (IOException ex) {
            return null;
        }
    }
}
