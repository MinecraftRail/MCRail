package io.github.phantamanta44.mcrail.adapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AdapterRegistry<T> {

    private final Map<Class<?>, List<Function<T, ?>>> registry;

    public AdapterRegistry() {
        this.registry = new HashMap<>();
    }

    public <V> void register(Class<V> type, Function<T, V> adapter) {
        registry.computeIfAbsent(type, t -> new LinkedList<>()).add(adapter);
    }

    @SuppressWarnings("unchecked")
    public <V> V adapt(Class<V> type, T object) {
        List<Function<T, ?>> adapters = registry.get(type);
        return adapters == null ? null : (V)adapters.stream().map(a -> a.apply(object)).findFirst().orElse(null);
    }

}
