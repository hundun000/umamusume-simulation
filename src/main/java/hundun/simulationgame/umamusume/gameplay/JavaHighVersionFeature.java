package hundun.simulationgame.umamusume.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hundun
 * Created on 2022/05/16
 */
public class JavaHighVersionFeature {

    public static Map<String, Integer> mapOf() {
        return new HashMap<>(0);
    }
    
    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> map = new HashMap<>(2);
        map.put(k1, v1);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new HashMap<>(3);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> map = new HashMap<>(4);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> map = new HashMap<>(5);
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static <T> List<T> arraysAsList(T item) {
        List<T> list = new ArrayList<>(1);
        list.add(item);
        return list;
    }

    public static <T> List<T> arraysAsList(T item1, T item2) {
        List<T> list = new ArrayList<>(2);
        list.add(item1);
        list.add(item2);
        return list;
    }

    public static <T> List<T> arraysAsList(T item1, T item2, T item3) {
        List<T> list = new ArrayList<>(3);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        return list;
    }

    public static <T> List<T> arraysAsList(T item1, T item2, T item3, T item4) {
        List<T> list = new ArrayList<>(4);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        return list;
    }

    public static <T> List<T> arraysAsList(T item1, T item2, T item3, T item4, T item5) {
        List<T> list = new ArrayList<>(5);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        return list;
    }

    
}
