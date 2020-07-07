package architect_training_camp.consistent_hash;

/**
 * 哈希环
 */
public interface HashCircle {
    /**
     * 插入节点
     * @param cache
     */
    void insertNode(Cache cache);

    /**
     * 获取节点
     * @param key
     * @return
     */
    Cache getNode(String key);

    /**
     * 删除节点
     * @param cache
     */
    void removeNode(Cache cache);
}
