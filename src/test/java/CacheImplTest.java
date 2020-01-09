import org.junit.jupiter.api.Test;
import ru.tri.CacheImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CacheImplTest {



    @Test
    void get() {
        String s = "This is a string I want to cache...";
        String st = "This is unexpected string";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, s, 5);
        assertEquals(s, cache.get(1));
    }

    @Test
    void removeAndGet() {
        String s = "This is a string I want to cache...";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, s, 5);
        Object obj = cache.removeAndGet(1);
        assertEquals(obj, s);
    }


    @Test
    void remove() {
        String s = "This is a string I want to cache...";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, s, 5);
        cache.remove(1);
        assertTrue(true);
    }

    @Test
    void size() {
        String s = "This is a string I want to cache...";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, s, 5);
        assertEquals(1, cache.size());
    }

    @Test
    void getAll() {
        String a = "a";
        String b = "b";
        String c = "c";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, a, 1);
        cache.put(2, b, 1);
        cache.put(3, c, 1);
        Collection collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);
        collection.add(3);

        HashMap<Integer, String> expected = new HashMap<>();
        expected.put(1, "a");
        expected.put(2, "b");
        expected.put(3, "c");

        Map actual = cache.getAll(collection);
        assertEquals(expected, actual);
    }

    @Test
    void clear() {
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        CacheImpl cache = new CacheImpl(100);
        cache.put(1, a, 1);
        cache.put(2, b, 1);
        cache.put(3, c, 1);
        cache.clear();
        assertEquals(0, cache.mapSize());
    }

    @Test
    void testOverage(){
        CacheImpl cache = new CacheImpl(100);
        for(int i = 0; i < 110; i++){
            cache.put(i+"", i, 500);
        }
        assertEquals(100, cache.size());
        assertEquals(100, cache.mapSize());
        assertEquals(100, cache.queueSize());
    }

}