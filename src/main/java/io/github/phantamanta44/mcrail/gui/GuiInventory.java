package io.github.phantamanta44.mcrail.gui;

import io.github.phantamanta44.mcrail.gui.slot.InventorySlot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public abstract class GuiInventory extends Gui {

    private final ItemStack[] inv;
    private final InventorySlot.Provider slotProvider;

    public GuiInventory(InventoryType type, String title, Player player, ItemStack[] inv, InventorySlot.Provider slotProvider) {
        super(type, title, player);
        this.inv = inv;
        this.slotProvider = slotProvider;
    }

    public GuiInventory(InventoryType type, String title, Player player, ItemStack[] inv) {
        this(type, title, player, inv, InventorySlot::new);
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
