package io.github.phantamanta44.mcrail.model;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IItemProvider {

    List<ItemStack> request(ItemStack filter, int amount);

    boolean contains(ItemStack filter, int amount);

    int quantity(ItemStack filter);

}
