package consistent_hash;

import net.jcip.annotations.NotThreadSafe;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 使用sortedMap实现哈希环
 */
@NotThreadSafe
public class SortedMapCircle implements HashCircle {
    private static final SortedMap<Integer, Cache> CIRCLE = new TreeMap<>();
    private Integer[] keys = null;


    @Override
    public void insertNode(Cache cache) {
        CIRCLE.put(Math.abs(cache.hashCode()), cache);
        keys = null;
    }

    /*public Cache getNode(String key) {
        Set<Integer> keys = CIRCLE.keySet();
        int keyHash = Math.abs(key.hashCode());
        for (Integer tmp : keys) {
            if (keyHash <= tmp) {
                return CIRCLE.get(tmp);
            }
        }
        return CIRCLE.get(CIRCLE.firstKey());
    }*/

    /**
     * 二分查找实现根据key找到对应节点。
     * @param key
     * @return
     */
    @Override
    public Cache getNode(String key) {
        //避免每次都进行耗时的toArray操作
        if (keys == null) {
            keys = CIRCLE.keySet().toArray(new Integer[0]);
        }
        //二分查找
        int left = 0;
        int right = keys.length - 1;
        int keyHash = Math.abs(key.hashCode());
        while (left <= right) {
            int mid = (left + right) / 2;
            if (keyHash <= keys[mid]) {
                if (mid - 1 >= 0 && keys[mid - 1] <= keyHash) {
                    return CIRCLE.get(keys[mid]);
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return CIRCLE.get(CIRCLE.firstKey());
    }

    @Override
    public void removeNode(Cache cache) {
        CIRCLE.remove(Math.abs(cache.hashCode()));
        keys = null;
    }
}
