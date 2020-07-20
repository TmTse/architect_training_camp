package performance_testing_utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load Property File
 */
public class PropertiesLoadUtil {
    public static Properties loadAsResource(String location) {
        Properties properties = null;
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location)){
            if(is!=null){
                properties = new Properties();
                properties.load(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
