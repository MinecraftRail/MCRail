package io.github.phantamanta44.mcrail.gui.slot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GuiSlot {

    public abstract ItemStack stack();

    public boolean click(Player player, ItemStack stack) {
        return false;
    }

}
