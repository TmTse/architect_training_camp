package performance_testing_utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class Client {
    private static ExecutorService executorService;
    private static final Properties config;
    private static final String url;
    private static final int total;
    private static final int concurrent;
    private static final ThreadPoolExecutor tpe;
    private static final int MAX_QUEUE_SIZE;

    static {
        //load config
        config = PropertiesLoadUtil.loadAsResource("config.properties");
        url = (String) config.get("url");
        total = Integer.valueOf(config.getProperty("total"));
        concurrent = Integer.valueOf(config.getProperty("concurrentNumber"));
        //init thread pool
        executorService = Executors.newFixedThreadPool(concurrent);
        tpe=(ThreadPoolExecutor)executorService;
        MAX_QUEUE_SIZE=Integer.valueOf(config.getProperty("MAX_QUEUE_SIZE"));

    }

    public static void main(String[] args) {

        if(total<=0){
            throw new IllegalArgumentException("total must to greater 0");
        }

        if(concurrent<=0){
            throw new IllegalArgumentException("concurrentNumber must to greater 0");
        }

        List<Future<Response>> futures = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            Future<Response> future = executorService.submit(() -> HttpClientFactory.getHttpClient().get(url, null));
            futures.add(future);

            //forbid insert queue too large to out of memory
            while (tpe.getQueue().size()>MAX_QUEUE_SIZE){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        executorService.shutdown();

        long totalTime = 0;
        long[] responseTimes = new long[total];
        int i = 0;
        for (Future<Response> future : futures) {
            try {
                long responseTime = future.get().getResponseTime();
                totalTime += responseTime;
                responseTimes[i++] = responseTime;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println(String.format("访问地址：%s",url));
        System.out.println(String.format("并发数：%s，总访问次数：%s:", concurrent, total));
        System.out.println(String.format("平均响应时间：%sms", totalTime / total));
        Arrays.sort(responseTimes);
        int offset = 95;
        int index = (total * offset / 100 - 1) < 0 ? 0 : total * offset / 100 - 1;
        System.out.println(String.format("%s%%响应时间：%sms", offset, responseTimes[index]));
        System.out.println(String.format("吞吐量：%s/s",1000/(totalTime / total)*concurrent));


    }
}
