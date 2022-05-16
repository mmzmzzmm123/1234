package study.Demo.GC;

import java.util.LinkedHashMap;
import java.util.Map;

class _6LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;
    private int cacheSize;  //缓存大小
    public _6LRUCache(int cacheSize) {
        super(10, 0.75f, true);   //第三个参数true是关键
        this.cacheSize = cacheSize;
    }
    //缓存是否已满
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        boolean r = size() > cacheSize;
        if(r)
            System.out.println("清除缓存key："+eldest.getKey());
        return r;
    }

    //测试
    public static void main(String[] args) {
        _6LRUCache<String, String> cache = new _6LRUCache<String, String>(5);
        cache.put("1", "1");
        cache.put("2", "2");
        cache.put("3", "3");
        cache.put("4", "4");
        cache.put("5", "5");

        System.out.println("初始化："+cache.keySet());

        cache.get("3");
        System.out.println("访问3："+cache.keySet());

        cache.get("2");
        System.out.println("访问2："+cache.keySet());

        System.out.println("增加数据6,7：");
        cache.put("5", "5");
        cache.put("7", "7");
        System.out.println(cache.keySet());
    }
}


