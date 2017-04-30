package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import io.github.phantamanta44.mcrail.util.JsonUtils;
import io.github.phantamanta44.mcrail.util.SignUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SmeltingHandler implements Listener {

    private static final String PLACEHOLDER =
            ChatColor.LIGHT_PURPLE + ChatColor.RESET.toString() + ChatColor.GREEN + "Smelting Placeholder";

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getView().getTopInventory());
        placeholderCheckLater((Player)event.getWhoClicked(), event.getView().getBottomInventory());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getView().getTopInventory());
        placeholderCheckLater((Player)event.getWhoClicked(), event.getView().getBottomInventory());
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        if (event.getSource().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getSource());
        else if (event.getDestination().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getDestination());
        placeholderCheck(event::getItem, event::setItem);
    }

    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        placeholderCheck(event.getItem()::getItemStack, event.getItem()::setItemStack);
    }

    private void placeholderCheckLater(Player player, Inventory inventory) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
            placeholderCheck(player::getItemOnCursor, player::setItemOnCursor);
            ItemStack[] inv = inventory.getContents();
            for (int i = 0; i < inv.length; i++) {
                final int index = i;
                placeholderCheck(() -> inv[index], stack -> inv[index] = stack);
            }
            inventory.setContents(inv);
        });
    }

    private void placeholderCheck(Supplier<ItemStack> getter, Consumer<ItemStack> setter) {
        ItemStack in = getter.get();
        if (ItemUtils.isNotNully(in)) {
            ItemMeta meta = in.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(PLACEHOLDER) && meta.hasLore()) {
                ItemStack result = fromPlaceholder(meta.getLore());
                result.setAmount(result.getAmount() * in.getAmount());
                setter.accept(result);
            }
        }
    }

    @EventHandler
    public void onLight(FurnaceBurnEvent event) {
        if (!isValid(((Furnace)event.getBlock().getState()).getInventory().getSmelting()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onCook(FurnaceSmeltEvent event) {
        if (!isValid(event.getSource())) {
            event.setCancelled(true);
        } else {
            RailSmeltRecipe recipe = Rail.recipes().getSmelting(event.getSource());
            if (recipe != null) {
                ItemMeta meta = event.getResult().getItemMeta();
                meta.setDisplayName(PLACEHOLDER);
                meta.setLore(toPlaceholder(recipe.mapToResult(event.getSource())));
                event.getResult().setItemMeta(meta);
            }
        }
    }

    private void checkFurnaceLater(FurnaceInventory inv) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> checkFurnace(inv));
    }

    private void checkFurnace(FurnaceInventory inv) {
        if (ItemUtils.isNotNully(inv.getSmelting()) && isValid(inv.getSmelting())) {
            // TODO Custom fuel behaviour
        }
    }

    private static boolean isValid(ItemStack stack) {
        if (Rail.recipes().getSmelting(stack) != null)
            return true;
        Iterator<Recipe> iter = Bukkit.getServer().recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (recipe instanceof FurnaceRecipe && ((FurnaceRecipe)recipe).getInput().isSimilar(stack))
                return true;
        }
        return false;
    }

    private static ItemStack fromPlaceholder(List<String> meta) {
        return JsonUtils.deserItemStack(JsonUtils.JSONP.parse(new String(Base64.getDecoder().decode(
                meta.stream()
                .skip(1)
                .map(s -> s.substring(ChatColor.DARK_GRAY.toString().length()))
                .collect(Collectors.joining())))));
    }

    private static List<String> toPlaceholder(ItemStack result) {
        List<String> lore = new LinkedList<>();
        if (result.hasItemMeta() && result.getItemMeta().hasDisplayName())
            lore.add(ChatColor.RESET + result.getItemMeta().getDisplayName());
        else
            lore.add(ChatColor.RESET + SignUtils.formatName(result.getType().name()));
        String ser = Base64.getEncoder()
                .encodeToString(JsonUtils.GSONC.toJson(JsonUtils.serItemStack(result)).getBytes());
        while (ser.length() > 0) {
            int len = Math.min(ser.length(), 32);
            lore.add(ChatColor.DARK_GRAY + ser.substring(0, len));
            ser = ser.substring(len);
        }
        return lore;
    }

}
