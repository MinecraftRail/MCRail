package io.github.phantamanta44.mcrail.gui.slot;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class GuiSlot {

    public abstract ItemStack stack();

    public boolean onInteract(Player player, InventoryClickEvent event) {
        return false;
    }

}
