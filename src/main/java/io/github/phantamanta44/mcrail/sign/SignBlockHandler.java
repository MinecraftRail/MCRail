package io.github.phantamanta44.mcrail.sign;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class SignBlockHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.SIGN) {
            // TODO Implement
        }
    }

    private void breakCheck() {
        // TODO Break check
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        breakCheck();
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        breakCheck();
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        breakCheck();
    }

    @EventHandler
    public void onPhysicsUpdate(BlockPhysicsEvent event) {
        breakCheck();
    }

}
