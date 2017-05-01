package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.model.IContainerItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class VanillaContainerItemAdapter implements Function<ItemStack, IContainerItem> {

    @Override
    public IContainerItem apply(ItemStack stack) {
        return stack.getType() == Material.LAVA_BUCKET
                || stack.getType() == Material.WATER_BUCKET
                || stack.getType() == Material.MILK_BUCKET
                ? () -> new ItemStack(Material.BUCKET) : null;
    }

}
