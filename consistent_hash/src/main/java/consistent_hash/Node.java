package consistent_hash;

import net.jcip.annotations.NotThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存节点
 */
@NotThreadSafe
public class Node extends AbstractCache {
    //使用Map实现缓存功能
    private Map<String, String> cache;


    public Node(String ip, String port) {
        super(ip,port);
        cache = new HashMap<>();
    }


    public String get(String key) {
        return cache.get(key);
    }

    public void add(String key, String value) {
        if(!cache.containsKey(key)){
            cache.put(key, value);
        }
    }

    public void set(String key, String value) {
        cache.put(key, value);

    }

    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public int size() {
       return cache.size();
    }
}
