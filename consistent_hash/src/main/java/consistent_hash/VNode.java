package consistent_hash;


import net.jcip.annotations.NotThreadSafe;

/**
 * 缓存虚拟节点
 */
@NotThreadSafe
public class VNode extends AbstractCache{

    private Node node;

    public VNode(Node node, String ip, String port) {
        super(ip,port);
        this.node = node;
    }

    @Override
    public String get(String key) {
        return node.get(key);
    }

    @Override
    public void add(String key, String value) {
        node.add(key, value);
    }

    @Override
    public void set(String key, String value) {
        node.set(key,value);
    }

    @Override
    public void remove(String key) {
        node.remove(key);
    }

    @Override
    public int size() {
        return node.size();
    }
}
