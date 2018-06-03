package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.model.IContainerItem;
import io.github.phantamanta44.mcrail.util.AdapterUtils;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import io.github.phantamanta44.mcrail.util.JsonUtils;
import io.github.phantamanta44.mcrail.util.SignUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        ItemStack original = event.getItem();
        if (placeholderCheck(event::getItem, event::setItem)) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                int index;
                for (index = 0; index < event.getSource().getSize(); index++) {
                    if (ItemUtils.isMatch(event.getSource().getItem(index), original))
                        break;
                }
                ItemStack stack = event.getSource().getItem(index);
                System.out.println(String.format("%s - %s", stack, original));
                if (stack.getAmount() > original.getAmount()) {
                    stack.setAmount(stack.getAmount() - original.getAmount());
                    event.getSource().setItem(index, stack);
                } else {
                    event.getSource().setItem(index, new ItemStack(Material.AIR, 0));
                }
            });
        }
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
            player.updateInventory();
        });
    }

    private boolean placeholderCheck(Supplier<ItemStack> getter, Consumer<ItemStack> setter) {
        ItemStack in = getter.get();
        if (ItemUtils.isNotNully(in)) {
            ItemMeta meta = in.getItemMeta();
            if (meta.hasDisplayName() && meta.getDisplayName().equals(PLACEHOLDER) && meta.hasLore()) {
                ItemStack result = fromPlaceholder(meta.getLore());
                result.setAmount(result.getAmount() * in.getAmount());
                setter.accept(result);
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onLight(FurnaceBurnEvent event) {
        FurnaceInventory inv = ((Furnace)event.getBlock().getState()).getInventory();
        if (!canBurn(inv.getSmelting(), inv))
            event.setCancelled(true);
    }

    @EventHandler
    public void onCook(FurnaceSmeltEvent event) {
        if (!canBurn(event.getSource(), ((Furnace)event.getBlock().getState()).getInventory())) {
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
        if (ItemUtils.isNotNully(inv.getSmelting()) && canBurn(inv.getSmelting(), inv)) {
            if (ItemUtils.isNotNully(inv.getFuel())) {
                int burnTime = Rail.recipes().getBurnTime(inv.getFuel());
                if (burnTime > 0) {
                    IContainerItem containerItem = AdapterUtils.adapt(IContainerItem.class, inv.getFuel());
                    if (containerItem != null) {
                        inv.setFuel(containerItem.getContainer());
                    } else {
                        if (inv.getFuel().getAmount() > 1)
                            inv.getFuel().setAmount(inv.getFuel().getAmount() - 1);
                        else
                            inv.setFuel(null);
                    }
                }
            }
        }
    }
    
    private boolean canBurn(ItemStack stack, FurnaceInventory inv) {
        if (!isValid(stack))
            return false;
        if (ItemUtils.isNully(inv.getResult()))
            return true;
        RailSmeltRecipe recipe = Rail.recipes().getSmelting(stack);
        if (recipe == null)
            return ItemUtils.isMatch(inv.getResult(), bukkitRecipeFor(inv.getSmelting()).getResult());
        if (!inv.getResult().hasItemMeta())
            return false;
        ItemMeta meta = inv.getResult().getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals(PLACEHOLDER) && meta.hasLore()
                && ItemUtils.isMatch(
                        Rail.recipes().getSmelting(stack).mapToResult(stack), fromPlaceholder(meta.getLore()));
    }

    private static boolean isValid(ItemStack stack) {
        return Rail.recipes().getSmelting(stack) != null || bukkitRecipeFor(stack) != null;
    }

    private static FurnaceRecipe bukkitRecipeFor(ItemStack stack) {
        Iterator<Recipe> iter = Bukkit.getServer().recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            if (recipe instanceof FurnaceRecipe && ItemUtils.isMatch(((FurnaceRecipe)recipe).getInput(), stack))
                return (FurnaceRecipe)recipe;
        }
        return null;
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
