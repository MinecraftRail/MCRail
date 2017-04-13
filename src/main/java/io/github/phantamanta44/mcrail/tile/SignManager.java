package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.RailMain;
import io.github.phantamanta44.mcrail.util.BlockPos;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SignManager {

    private final Map<BlockPos, SignEntity> entities;

    public SignManager() {
        this.entities = new HashMap<>();
        RailMain.INSTANCE.onTick(this::tick);
    }

    private void tick(long tick) {
        entities.entrySet().stream()
                .filter(e -> e.getKey().exists())
                .map(Map.Entry::getValue)
                .forEach(SignEntity::tick);
    }

    public void onSignClick(PlayerInteractEvent event) {
        SignEntity se = entities.get(new BlockPos(event.getClickedBlock()));
        if (se != null)
            se.onInteract(event);
    }

    public boolean breakCheck(Block block) {
        SignEntity se = entities.remove(new BlockPos(block));
        if (se != null) {
            se.destroy();
            Collection<ItemStack> drops = block.getDrops();
            se.modifyDrops(drops);
            Location loc = block.getLocation().add(0.5D, 0.5D, 0.5D);
            drops.forEach(d -> block.getWorld().dropItemNaturally(loc, d));
            return true;
        }
        return false;
    }

    public void register(Block block) {
        SignEntity se = RailMain.INSTANCE.registry().createEntity(block);
        se.init();
        entities.put(new BlockPos(block), se);
    }

    public void destroy(Block block) {
        SignEntity se = entities.remove(new BlockPos(block));
        if (se != null)
            se.destroy();
    }

    public SignEntity getAt(Block block) {
        return entities.get(new BlockPos(block));
    }

    public boolean existsAt(Block block) {
        return entities.containsKey(new BlockPos(block));
    }

}
