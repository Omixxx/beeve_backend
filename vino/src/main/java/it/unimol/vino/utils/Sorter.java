package it.unimol.vino.utils;

import java.util.*;
import java.util.Map.Entry;

public class Sorter {

    public static <K, V extends Comparable<? super V>> void sortMapByValue(
            final Map<K, V> map) {
        List<Entry<K, V>> list = new LinkedList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        map.clear();
        for (Entry<K, V> entry : list) {
            map.put(entry.getKey(), entry.getValue());
        }
    }
}
