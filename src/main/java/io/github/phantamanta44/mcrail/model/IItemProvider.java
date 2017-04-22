package io.github.phantamanta44.mcrail.model;

import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public interface IItemProvider {

    ItemStack request(Predicate<ItemStack> filter, int amount);

    boolean contains(Predicate<ItemStack> filter, int amount);

    int quantity(Predicate<ItemStack> filter);

}
