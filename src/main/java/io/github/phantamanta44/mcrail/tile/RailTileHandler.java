package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.TileUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Sign;

public class RailTileHandler implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            Rail.tileManager().onTileClick(event);
    }

    private boolean breakCheck(Block block, boolean dropTile) {
        return Rail.tileManager().breakCheck(block, dropTile);
    }

    private boolean breakCheck(Block block) {
        return breakCheck(block, true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (breakCheck(event.getBlock(), event.getPlayer().getGameMode() != GameMode.CREATIVE))
            event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(this::breakCheck);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(this::breakCheck);
    }

    @EventHandler
    public void onPhysicsUpdate(BlockPhysicsEvent event) {
        if (event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN) {
            Sign sign = (Sign)event.getBlock().getState().getData();
            if (!event.getBlock().getRelative(sign.getAttachedFace()).getType().isSolid()
                    && Rail.tileManager().breakCheck(event.getBlock(), true)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPistonPush(BlockPistonExtendEvent event) {
        if (event.getBlocks().stream().anyMatch(TileUtils::existsAt))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPistonPull(BlockPistonRetractEvent event) {
        if (event.isSticky() && event.getBlocks().stream().anyMatch(TileUtils::existsAt))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEvent(BlockDamageEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(BlockDispenseEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(BlockExpEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(BlockIgniteEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(BlockRedstoneEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(BrewEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

    @EventHandler
    public void onEvent(NotePlayEvent event) {
        Rail.tileManager().dispatchEvent(event);
    }

}
