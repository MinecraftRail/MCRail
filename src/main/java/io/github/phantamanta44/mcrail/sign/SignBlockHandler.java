package io.github.phantamanta44.mcrail.sign;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SignBlockHandler implements Listener {

    private final Set<UUID> placing = new HashSet<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.SIGN) {
            // TODO Implement
        }
    }

    @EventHandler
    public void onSignEdit(SignChangeEvent event) {
        if (placing.contains(event.getPlayer().getUniqueId())) {
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
