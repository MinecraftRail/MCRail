package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.item.characteristic.IItemCharacteristic;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface IItemBehaviour {

    Material material();

    Collection<IItemCharacteristic> characteristics();

    default boolean onUse(PlayerInteractEvent event, ItemStack stack) {
        return true;
    }

    default boolean onEat(PlayerItemConsumeEvent event, ItemStack stack) {
        return false;
    }

    default boolean onDamage(PlayerItemDamageEvent event, ItemStack stack) {
        return false;
    }

    default boolean onPickup(PlayerPickupItemEvent event, ItemStack stack) {
        return true;
    }

    default boolean onPlace(BlockPlaceEvent event, ItemStack stack) {
        return false;
    }

    default boolean onBlockBreak(BlockBreakEvent event, ItemStack stack) {
        return true;
    }

}
