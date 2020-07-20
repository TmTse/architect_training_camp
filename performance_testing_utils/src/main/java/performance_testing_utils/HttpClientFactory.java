package performance_testing_utils;

import java.util.Properties;

/**
 * HttpClient Factory
 */
public class HttpClientFactory {
    private static HttpClient httpClient;

    /**
     * get HttpClient(Singleton)
     * @return
     */
    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClientFactory.class) {
                if (httpClient == null) {
                    httpClient = genHttpClient();
                }
            }
        }
        return httpClient;
    }

    /**
     * return  one of the HttpClient implement you set in config.properties.The default is HttpUrlConnection.
     * @return
     */
    private static HttpClient genHttpClient() {
        //get from config.properties
        Properties config = PropertiesLoadUtil.loadAsResource("config.properties");
        try {
            Class clazz = Class.forName(config.getProperty("HttpClientImplement"));
            return (HttpClient) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //not config or find exception，return default implement
        return new HttpUrlConnection();
    }
}
