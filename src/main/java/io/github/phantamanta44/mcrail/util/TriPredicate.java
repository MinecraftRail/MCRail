package io.github.phantamanta44.mcrail.util;

@FunctionalInterface
public interface TriPredicate<T, U, V> {

    boolean test(T t, U u, V v);

}
