package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import io.github.phantamanta44.mcrail.util.TriPredicate;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class ItemHandler implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        delegate(event, PlayerInteractEvent::getItem, IItemBehaviour::onUse);
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        delegate(event, PlayerItemConsumeEvent::getItem, IItemBehaviour::onEat);
    }

    @EventHandler
    public void onDamage(PlayerItemDamageEvent event) {
        delegate(event, PlayerItemDamageEvent::getItem, IItemBehaviour::onDamage);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        delegate(event, e -> event.getItem().getItemStack(), IItemBehaviour::onPickup);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        delegate(event, BlockPlaceEvent::getItemInHand, IItemBehaviour::onPlace);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        delegate(event, e -> e.getPlayer().getItemInHand(), IItemBehaviour::onBlockBreak);
    }

    private static <T extends Event & Cancellable> void delegate(T event, Function<T, ItemStack> stackGetter, TriPredicate<IItemBehaviour, T, ItemStack> delFunc) {
        ItemStack stack = stackGetter.apply(event);
        if (ItemUtils.isNotNully(stack)) {
            IItemBehaviour item = Rail.itemRegistry().get(stack);
            if (item != null && !delFunc.test(item, event, stack))
                event.setCancelled(true);
        }
    }
    
}
