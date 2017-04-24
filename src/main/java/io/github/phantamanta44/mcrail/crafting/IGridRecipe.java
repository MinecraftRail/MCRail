package io.github.phantamanta44.mcrail.crafting;

import org.bukkit.inventory.ItemStack;

public interface IGridRecipe {

    ItemStack mapToResult(ItemStack[] mat);

    ItemStack[] matches(ItemStack[] mat);

}
