package io.github.phantamanta44.mcrail.sign;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.SignEntityItem;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class SignRegistry {

    private final Map<String, Function<Block, SignEntity>> registry;

    public SignRegistry() {
        this.registry = new HashMap<>();
    }

    public void register(String id, Function<Block, SignEntity> provider) {
        id = id.toLowerCase();
        if (registry.containsKey(id))
            throw new IllegalArgumentException("Sign entity already exists: " + id);
        registry.put(id, provider);
        Rail.itemRegistry().register(id, new SignEntityItem(id));
    }

    public boolean isValidId(String id) {
        return registry.containsKey(id.toLowerCase());
    }

    public SignEntity createEntity(String id, Block block) {
        Sign sign = (Sign)block.getState();
        sign.setLine(0, "[" + id + "]");
        sign.update();
        Function<Block, SignEntity> provider = registry.get(id);
        return provider != null ? provider.apply(block) : null;
    }

    public Function<Block, SignEntity> providerFor(String id) {
        return registry.get(id);
    }

    public Stream<String> validIds() {
        return registry.keySet().stream();
    }

}
