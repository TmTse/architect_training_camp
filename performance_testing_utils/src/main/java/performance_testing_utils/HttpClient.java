package performance_testing_utils;

import java.util.Map;

/**
 * http protocol accessor
 */
public interface HttpClient {
    /**
     * get
     * @param url
     * @param heads can be null
     * @return
     */
    Response get(String url, Map<String, String> heads);

    /**
     * normal form post
     * @param url
     * @param params can be null
     * @param heads can be null
     * @return
     */
    Response post(String url, Map<String, String> params, Map<String, String> heads);
}
