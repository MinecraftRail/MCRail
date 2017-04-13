package io.github.phantamanta44.mcrail.gui.slot;

import io.github.phantamanta44.mcrail.gui.GuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class FilteredInventorySlot extends InventorySlot {

    public static InventorySlot.Provider provider(Predicate<ItemStack> filter) {
        return (gui, index) -> new FilteredInventorySlot(gui, index, filter);
    }

    private final Predicate<ItemStack> filter;

    public FilteredInventorySlot(GuiInventory gui, int index, Predicate<ItemStack> filter) {
        super(gui, index);
        this.filter = filter;
    }

    @Override
    public boolean click(Player player, ItemStack stack) {
        return filter.test(stack) && super.click(player, stack);
    }

}
