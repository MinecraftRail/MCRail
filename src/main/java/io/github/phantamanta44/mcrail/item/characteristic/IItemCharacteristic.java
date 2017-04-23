package io.github.phantamanta44.mcrail.item.characteristic;

import org.bukkit.inventory.ItemStack;

public interface IItemCharacteristic {

    void apply(ItemStack stack);

    boolean matches(ItemStack stack);

}
