package performance_testing_utils;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * JDK HttpUrlConnection implement
 */
public class HttpUrlConnection implements HttpClient {

    @Override
    public Response get(String url, Map<String, String> heads) {
        Response response = new Response();
        HttpURLConnection connection = null;
        try {
            URL urlAddr = new URL(url);
            connection = (HttpURLConnection) urlAddr.openConnection();
            //Don't intend to use the URL connection for output
            connection.setDoOutput(false);
            //No caching
            connection.setUseCaches(false);
            //30s timeout
            connection.setConnectTimeout(30000);
            if (heads != null) {
                for (String key : heads.keySet()) {
                    connection.setRequestProperty(key, heads.get(key));
                }
            }
            long begin = System.currentTimeMillis();
            connection.connect();
            //set responseCode
            response.setResponseCode(connection.getResponseCode());
            //set responseMessage
            response.setMessage(getResponse(connection.getInputStream()));
            long end = System.currentTimeMillis();

            response.setResponseTime(end - begin);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        //return
        return response;
    }

    @Override
    public Response post(String url, Map<String, String> params, Map<String, String> heads) {
        HttpURLConnection connection = null;
        Response response = new Response();
        try {
            URL urlAddr = new URL(url);
            connection = (HttpURLConnection) urlAddr.openConnection();
            connection.setRequestMethod("POST");
            //30s timeout
            connection.setConnectTimeout(30000);

            //No caching
            connection.setUseCaches(false);
            //normal form
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //add heads
            if (heads != null && !heads.isEmpty()) {
                for (String key : heads.keySet()) {
                    connection.setRequestProperty(key, heads.get(key));
                }
            }
            //add params
            if (params != null && !params.isEmpty()) {
                //intend to use the URL connection for output
                connection.setDoOutput(true);
                try (OutputStream out = connection.getOutputStream()) {
                    out.write(converParams(params).getBytes());
                    out.flush();
                }
            }
            long begin = System.currentTimeMillis();
            connection.connect();

            response.setResponseCode(connection.getResponseCode());

            //set responseMessage
            response.setMessage(getResponse(connection.getInputStream()));
            long end = System.currentTimeMillis();
            response.setResponseTime(end - begin);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }


    @NotNull
    private static String getResponse(InputStream inputStream) {
        StringBuilder msg = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                msg.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    @NotNull
    private static String converParams(Map<String, String> params) {
        StringBuilder tmp = new StringBuilder();
        for (String key : params.keySet()) {
            tmp.append(key).append("=").append(params.get(key)).append("&");
        }
        return tmp.substring(0, tmp.length() - 1);
    }

    public static void main(String[] args) {
       /* Map<String, String> map = new HashMap();

        map.put("a", "a");
        map.put("b", "b");

        System.out.println(converParams(map));

        HttpClient connection = new HttpUrlConnection();
        Response response = connection.get("https://www.sina.com.cn", null);
        System.out.println(response.getResponseCode());
        System.out.println(response.getMessage());
        System.out.println(response.getResponseTime());*/

        System.out.println(100*95/100);
    }
}
