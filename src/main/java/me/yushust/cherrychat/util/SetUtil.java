package me.yushust.cherrychat.util;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SetUtil {

    public static <T> void add(Supplier<Set<T>> getter, Consumer<Set<T>> setter, T element) {
        Set<T> set = getter.get();
        set.add(element);
        setter.accept(set);
    }

}
