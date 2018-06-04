package io.github.phantamanta44.mcrail.model;

import org.bukkit.inventory.ItemStack;

public interface IItemAcceptor {

    ItemStack offer(ItemStack stack, int amount);

    boolean hasSpace(ItemStack stack, int amount);

    int space(ItemStack stack, int amount);

}
