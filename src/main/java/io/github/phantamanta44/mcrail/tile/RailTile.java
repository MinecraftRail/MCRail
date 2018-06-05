package io.github.phantamanta44.mcrail.tile;

import com.google.gson.JsonObject;
import io.github.phantamanta44.mcrail.util.BlockPos;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class RailTile {

    private final BlockPos pos;
    private final String id;
    private final Map<Class<? extends BlockEvent>, Consumer<? extends BlockEvent>> events;

    public RailTile(Block block, String id) {
        this.pos = new BlockPos(block);
        this.id = id;
        this.events = new HashMap<>();
    }

    public BlockPos pos() {
        return pos;
    }

    public Block block() {
        return pos.block();
    }

    public Location location() {
        return block().getLocation();
    }

    public String id() {
        return id;
    }

    public abstract void init();

    public void destroy() {
        // NO-OP
    }

    public void modifyDrops(Collection<ItemStack> drops) {
        // NO-OP
    }

    public void tick() {
        // NO-OP
    }

    public void onInteract(PlayerInteractEvent event) {
        // NO-OP
    }

    protected <T extends BlockEvent> void on(Class<T> event, Consumer<T> handler) {
        events.put(event, handler);
    }

    @SuppressWarnings("unchecked")
    <T extends BlockEvent> void consumeEvent(T event) {
        Consumer handler = events.get(event.getClass());
        if (handler != null)
            handler.accept(event);
    }

    public abstract JsonObject serialize();

    public abstract void deserialize(JsonObject dto);

}
