package io.github.phantamanta44.mcrail.gui.slot;

import io.github.phantamanta44.mcrail.gui.GuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventorySlot extends GuiSlot {

    private final GuiInventory gui;
    private final int index;
    protected boolean dirty;

    public InventorySlot(GuiInventory gui, int index) {
        this.gui = gui;
        this.index = index;
        this.dirty = false;
    }

    @Override
    public ItemStack stack() {
        if (dirty) {
            gui.set(index, gui.inventory().getItem(gui.indexOf(this)));
            dirty = false;
        }
        return gui.get(index);
    }

    @Override
    public boolean onInteract(Player player, InventoryClickEvent event) {
        dirty = true;
        return true;
    }

    @FunctionalInterface
    public interface Provider {

        InventorySlot generate(GuiInventory gui, int index);

    }

}
