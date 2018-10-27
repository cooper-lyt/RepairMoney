package cc.coopersoft.framework.data;

public class KeyAndCount implements Comparable<KeyAndCount>{

    private String key;
    private String name;
    private long count;
    private int pri;

    public KeyAndCount(String key, String name, long count) {
        this.key = key;
        this.name = name;
        this.count = count;
    }

    public KeyAndCount(String key, long count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPri() {
        return pri;
    }

    public void setPri(int pri) {
        this.pri = pri;
    }

    @Override
    public int compareTo(KeyAndCount o) {
        return Integer.valueOf(pri).compareTo(o.getPri());
    }
}
