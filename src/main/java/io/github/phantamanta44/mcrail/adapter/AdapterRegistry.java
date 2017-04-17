package io.github.phantamanta44.mcrail.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AdapterRegistry<T> {

    private final Map<Class<?>, List<Function<T, ?>>> registry;

    public AdapterRegistry() {
        this.registry = new HashMap<>();
    }

    public <V> void register(Class<V> type, Function<T, V> adapter) {
        registry.computeIfAbsent(type, t -> new ArrayList<>()).add(adapter);
    }

    @SuppressWarnings("unchecked")
    public <V> V adapt(Class<V> type, T object) {
        List<Function<T, ?>> adapters = registry.get(type);
        if (adapters != null) {
            for (int i = adapters.size() - 1; i >= 0; i++) {
                V adapted = (V)adapters.get(i).apply(object);
                if (adapted != null)
                    return adapted;
            }
        }
        return null;
    }

}
