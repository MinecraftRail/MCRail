package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;

public class CraftingHandler implements Listener {

    private static final EnumSet<InventoryAction> CRAFT_ACTIONS = EnumSet.of(
            InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_HALF,
            InventoryAction.HOTBAR_MOVE_AND_READD, InventoryAction.HOTBAR_SWAP, InventoryAction.MOVE_TO_OTHER_INVENTORY
    );

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.CRAFTING
                || event.getView().getTopInventory().getType() == InventoryType.WORKBENCH) {
            if (event.getSlotType() == InventoryType.SlotType.RESULT && CRAFT_ACTIONS.contains(event.getAction())) // FIXME this is BROKEN
                Bukkit.getServer().getScheduler().runTask(Rail.INSTANCE, () -> {
                    CraftingInventory inv = (CraftingInventory)event.getView().getTopInventory();
                    ItemStack[] mat = inv.getMatrix();
                    for (int i = 0; i < mat.length - 1; i++) {
                        if (ItemUtils.isNotNully(mat[i])) {
                            if (mat[i].getAmount() > 1)
                                mat[i].setAmount(mat[i].getAmount() - 1);
                            else
                                mat[i] = null;
                        }
                    }
                    inv.setMatrix(mat);
                });
            else
                checkCrafting((CraftingInventory)event.getView().getTopInventory(), event.getWhoClicked());
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.CRAFTING
                || event.getView().getTopInventory().getType() == InventoryType.WORKBENCH) {
            checkCrafting((CraftingInventory)event.getView().getTopInventory(), event.getWhoClicked());
        }
    }

    private void checkCrafting(CraftingInventory inv, HumanEntity pl) {
        Bukkit.getServer().getScheduler().runTask(Rail.INSTANCE, () -> {
            ItemStack[] mat = new ItemStack[9];
            if (inv.getMatrix().length == 10) {
                System.arraycopy(inv.getMatrix(), 0, mat, 0, 9);
            } else {
                System.arraycopy(inv.getMatrix(), 0, mat, 0, 2);
                System.arraycopy(inv.getMatrix(), 2, mat, 3, 2);
            }
            ItemStack result = Rail.recipes().recipeCheck(mat);
            if (result != null) {
                inv.setResult(result);
                ((Player)pl).updateInventory();
            }
        });
    }

}
