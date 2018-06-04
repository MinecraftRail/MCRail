package io.github.phantamanta44.mcrail.tile;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Sign;

public class RailTileHandler implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            Rail.tileManager().onTileClick(event);
    }

    private boolean breakCheck(Block block) {
        return Rail.tileManager().breakCheck(block);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (breakCheck(event.getBlock()))
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
                    && Rail.tileManager().breakCheck(event.getBlock())) {
                event.setCancelled(true);
            }
        }
    }

}
