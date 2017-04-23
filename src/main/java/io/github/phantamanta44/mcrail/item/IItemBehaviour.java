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

    default void onUse(PlayerInteractEvent event, ItemStack stack) {
        // NO-OP
    }

    default void onEat(PlayerItemConsumeEvent event, ItemStack stack) {
        // NO-OP
    }

    default void onDamage(PlayerItemDamageEvent event, ItemStack stack) {
        // NO-OP
    }

    default void onPickup(PlayerPickupItemEvent event, ItemStack stack) {
        // NO-OP
    }

    default void onPlace(BlockPlaceEvent event, ItemStack stack) {
        // NO-OP
    }

    default void onBlockBreak(BlockBreakEvent event, ItemStack stack) {
        // NO-OP
    }

}
