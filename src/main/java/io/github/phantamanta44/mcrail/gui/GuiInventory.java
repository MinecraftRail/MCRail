package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.gui.slot.InventorySlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GuiInventory extends Gui {

    private final ItemStack[] inv;
    private final InventorySlot.Provider slotProvider;

    public GuiInventory(int rows, String title, Player player, ItemStack[] inv, InventorySlot.Provider slotProvider) {
        super(rows, title, player);
        this.inv = inv;
        this.slotProvider = slotProvider;
    }

    public GuiInventory(int rows, String title, Player player, ItemStack[] inv) {
        this(rows, title, player, inv, InventorySlot::new);
    }

    protected InventorySlot genSlot(int index) {
        return slotProvider.generate(this, index);
    }

    public int size() {
        return inv.length;
    }

    public ItemStack get(int index) {
        return inv[index];
    }

    public void set(int index, ItemStack stack) {
        inv[index] = stack;
    }

}
