package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.item.ItemRailTile;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class RailTileRegistry {

    private final Map<String, Function<Block, RailTile>> registry;

    public RailTileRegistry() {
        this.registry = new HashMap<>();
    }

    public void register(ItemRailTile item, Function<Block, RailTile> provider) {
        String id = item.getTileId().toLowerCase();
        if (registry.containsKey(id))
            throw new IllegalArgumentException("Rail tile already exists: " + id);
        registry.put(id, provider);
        Rail.itemRegistry().register(id, item);
    }

    public boolean isValidId(String id) {
        return registry.containsKey(id.toLowerCase());
    }

    public RailTile createEntity(String id, String name, Block block) {
        Function<Block, RailTile> provider = registry.get(id);
        return provider != null ? provider.apply(block) : null;
    }

    public Function<Block, RailTile> providerFor(String id) {
        return registry.get(id);
    }

    public Stream<String> validIds() {
        return registry.keySet().stream();
    }

}
