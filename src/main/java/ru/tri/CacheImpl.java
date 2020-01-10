package ru.tri;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheImpl<K, V> implements Cache<K, V> {
    private Map<K, CacheEntry<V>> cache;
    private Queue<K> queue;
    private int maxSize;
    private AtomicInteger cacheSize = new AtomicInteger();

    public CacheImpl(int maxSize) {
        this.maxSize = maxSize;
        cache = new ConcurrentHashMap<>(maxSize);
        queue = new ConcurrentLinkedQueue<>();
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        return entry.getEntry();
    }

    public V removeAndGet(K key) {
        if (key == null) {
            return null;
        }
        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            cacheSize.decrementAndGet();
            return cache.remove(key).getEntry();
        }
        return null;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Invalid Value.");
        }

        boolean exists = cache.containsKey(key);
        if (!exists) {
            cacheSize.incrementAndGet();
            while (cacheSize.get() > maxSize) {
                remove(queue.poll());
            }
        }
        cache.put(key, new CacheEntry<V>(value));
        queue.add(key);
    }

    public boolean remove(K key) {
        return removeAndGet(key) != null;
    }

    public int size() {
        return cacheSize.get();
    }

    public Map<K, V> getAll(Collection<K> collection) {
        Map<K, V> ret = new HashMap<K, V>();
        for (K o : collection) {
            ret.put(o, get(o));
        }
        return ret;
    }

    public void clear() {
        cache.clear();
        queue.clear();
        cacheSize.lazySet(0);
    }

    public int mapSize() {
        return cache.size();
    }

    public int queueSize() {
        return queue.size();
    }

    public Collection<K> getKeys() {
        return queue;
    }

    private class CacheEntry<V> {
        private V entry;

        CacheEntry(V entry) {
            super();
            this.entry = entry;
        }

        V getEntry() {
            return entry;
        }
    }

    public Map<K, CacheEntry<V>> getCache() {
        return cache;
    }
}
