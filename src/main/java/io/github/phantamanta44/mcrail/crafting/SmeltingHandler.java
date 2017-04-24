package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.Bukkit;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;

public class SmeltingHandler implements Listener {

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
    public void onSmelt(FurnaceSmeltEvent event) {
        checkFurnaceLater(((Furnace)event.getBlock().getState()).getInventory());
    }

    private static void checkFurnaceLater(FurnaceInventory inv) {
        Bukkit.getServer().getScheduler().runTask(Rail.INSTANCE, () -> checkFurnace(inv));
    }

    private static void checkFurnace(FurnaceInventory inv) {
        // TODO Implement
        inv.getViewers().forEach(p -> ((Player)p).updateInventory());
    }

}
