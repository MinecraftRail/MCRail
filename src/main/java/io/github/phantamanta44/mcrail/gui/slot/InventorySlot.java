package io.github.phantamanta44.mcrail.gui.slot;

import io.github.phantamanta44.mcrail.gui.GuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventorySlot extends GuiSlot {

    private final GuiInventory gui;
    private final int index;

    public InventorySlot(GuiInventory gui, int index) {
        this.gui = gui;
        this.index = index;
    }

    @Override
    public ItemStack stack() {
        return gui.get(index);
    }

    @Override
    public boolean click(Player player, ItemStack stack) {
        gui.set(index, stack);
        return true;
    }

    @FunctionalInterface
    public interface Provider {

        InventorySlot generate(GuiInventory gui, int index);

    }

}
