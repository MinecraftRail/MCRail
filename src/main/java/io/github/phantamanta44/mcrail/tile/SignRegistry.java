package io.github.phantamanta44.mcrail.tile;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
    }

    public boolean isValidId(String id) {
        return registry.containsKey(id.toLowerCase());
    }

    public SignEntity createEntity(Block block) {
        Sign sign = (Sign)block.getState();
        String header = sign.getLine(0).toLowerCase();
        if (header.startsWith("&")) {
            Function<Block, SignEntity> provider = registry.get(header.substring(1));
            if (provider != null) {
                sign.setLine(0, header);
                sign.update();
                return provider.apply(block);
            }
        }
        return null;
    }

}
