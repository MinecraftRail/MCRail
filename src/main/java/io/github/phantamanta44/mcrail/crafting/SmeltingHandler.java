package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public class SmeltingHandler implements Listener { // TODO Workaround for smelted stuff not stacking with placeholder stone (maybe a proxy for ItemStack?)

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getView().getTopInventory());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getView().getTopInventory());
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        if (event.getSource().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getSource());
        else if (event.getDestination().getType() == InventoryType.FURNACE)
            checkFurnaceLater((FurnaceInventory)event.getDestination());
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
            if (recipe != null)
                event.setResult(recipe.mapToResult(event.getSource()));
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

}
