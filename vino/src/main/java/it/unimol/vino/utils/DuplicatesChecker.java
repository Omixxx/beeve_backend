package it.unimol.vino.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicatesChecker<T> {
    public static <T> boolean hasDuplicates(List<T> list) {
        Set<T> set = new HashSet<>(list);
        return list.size() != set.size();
    }
}
