package performance_testing_utils;

/**
 * http response
 */
public class Response {
    private int responseCode;
    private String message;
    private long responseTime;

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long reponseTime) {
        this.responseTime = reponseTime;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}