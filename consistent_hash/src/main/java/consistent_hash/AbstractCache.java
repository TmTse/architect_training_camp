package consistent_hash;

import java.util.Objects;

/**
 * Cache的抽象类
 */
public abstract class AbstractCache implements Cache {
    private String ip;
    private String port;
    //增加节点hash值的散列性
    private String md5;


    protected AbstractCache(String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.md5 = MD5Utils.encode(this.ip+":"+this.port);

    }


    @Override
    public abstract String get(String key);

    @Override
    public abstract void add(String key, String value) ;

    @Override
    public abstract void set(String key, String value) ;

    @Override
    public abstract void remove(String key) ;

    @Override
    public abstract int size();


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractCache that = (AbstractCache) o;
        return md5 == that.md5 &&
                ip.equals(that.ip) &&
                port.equals(that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, md5);
    }
}
