package io.github.phantamanta44.mcrail.sign;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SignPlacementHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.SIGN) {
            // TODO Implement
        }
    }

}
