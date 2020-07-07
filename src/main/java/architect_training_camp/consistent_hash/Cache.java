package architect_training_camp.consistent_hash;

/**
 * 缓存接口
 */
public interface Cache {
    /**
     * 查询
     * @param key
     */
    String get(String key);

    /**
     * 添加
     * @param key
     * @param value
     */
    void add(String key, String value);

    /**
     * 添加（覆盖）
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 删除
     * @param key
     */
    void remove(String key);

    /**
     * 总数
     */
    int size();

}
