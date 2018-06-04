package io.github.phantamanta44.mcrail.tile;

import com.google.gson.JsonObject;
import io.github.phantamanta44.mcrail.util.BlockPos;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public abstract class RailTile {

    private final BlockPos pos;
    private final String id;

    public RailTile(Block block, String id) {
        this.pos = new BlockPos(block);
        this.id = id;
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

    public abstract JsonObject serialize();

    public abstract void deserialize(JsonObject dto);

}
