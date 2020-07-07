package architect_training_camp.consistent_hash;

import net.jcip.annotations.NotThreadSafe;

/**
 * 哈希环数组实现，内存需求过大
 */
@NotThreadSafe
public class ArrayHashCircle implements HashCircle {
    private static final int SIZE = (int) Math.pow(2, 31);
    //接近需要16G内存(2^31-1)*8/1024/1024/1024
    private static final Cache[] CIRCLE = new Cache[SIZE];

    public ArrayHashCircle() {
        for (int i = 0; i < SIZE; i++) {
            CIRCLE[i] = null;
        }
    }

    @Override
    public void insertNode(Cache cache) {
        CIRCLE[cache.hashCode()] = cache;
    }

    @Override
    public Cache getNode(String key) {
        for (int i = key.hashCode(); i < SIZE; i++) {
            if (CIRCLE[i] != null) {
                return CIRCLE[i];
            }
        }

        for (int i = 0; i < key.hashCode(); i++) {
            if (CIRCLE[i] != null) {
                return CIRCLE[i];
            }
        }
        return null;
    }

    @Override
    public void removeNode(Cache cache) {
        CIRCLE[cache.hashCode()] = null;
    }
}
