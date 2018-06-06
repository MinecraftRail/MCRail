package io.github.phantamanta44.mcrail.item;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import io.github.phantamanta44.mcrail.util.TriPredicate;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class ItemHandler implements Listener {

    @EventHandler
    public void onAnvil(InventoryClickEvent event) {
        if (event.getClickedInventory().getType() == InventoryType.ANVIL
                && event.getSlotType() == InventoryType.SlotType.RESULT
                && ItemUtils.isNotNully(event.getCurrentItem())
                && (ItemUtils.isRailItem(event.getClickedInventory().getContents()[0])
                || ItemUtils.isRailItem(event.getClickedInventory().getContents()[1]))) {
            event.setCancelled(true);
        }
    }

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

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            delegate(event, e ->
                    ((Player)e.getEntity()).getInventory().getChestplate(), IItemBehaviour::onPlayerDamage);
            if (!event.isCancelled()) {
                delegate(event, e ->
                        ((Player)e.getEntity()).getInventory().getLeggings(), IItemBehaviour::onPlayerDamage);
            }
            if (!event.isCancelled()) {
                delegate(event, e ->
                        ((Player)e.getEntity()).getInventory().getHelmet(), IItemBehaviour::onPlayerDamage);
            }
            if (!event.isCancelled()) {
                delegate(event, e ->
                        ((Player)e.getEntity()).getInventory().getBoots(), IItemBehaviour::onPlayerDamage);
            }
        }
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
