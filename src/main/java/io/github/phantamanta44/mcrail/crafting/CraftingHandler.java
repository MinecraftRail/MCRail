package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

    private static final EnumSet<InventoryAction> PICKUP_ACTIONS = EnumSet.of(
            InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_HALF
    ), HOTBAR_ACTIONS = EnumSet.of(
            InventoryAction.HOTBAR_MOVE_AND_READD, InventoryAction.HOTBAR_SWAP
    ), ALL_ACTIONS = EnumSet.of(
            InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_HALF,
            InventoryAction.HOTBAR_MOVE_AND_READD, InventoryAction.HOTBAR_SWAP, InventoryAction.MOVE_TO_OTHER_INVENTORY
    );

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.CRAFTING
                || event.getView().getTopInventory().getType() == InventoryType.WORKBENCH) {
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                ItemStack onCursor = event.getWhoClicked().getItemOnCursor();
                if (ALL_ACTIONS.contains(event.getAction())
                        && !(PICKUP_ACTIONS.contains(event.getAction())
                                && ItemUtils.isNotNully(onCursor)
                                && ItemUtils.isFull(onCursor))) {
                    CraftingInventory inv = (CraftingInventory)event.getView().getTopInventory();
                    if (ItemUtils.isNotNully(inv.getResult()) && inv.getRecipe() == null) {
                        ItemStack expectedResult = inv.getResult().clone();
                        do {
                            ItemStack[] mat = inv.getMatrix();
                            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                                event.getWhoClicked().getInventory().addItem(inv.getResult());
                            } else if (PICKUP_ACTIONS.contains(event.getAction())) {
                                if (ItemUtils.isNully(onCursor))
                                    event.getWhoClicked().setItemOnCursor(inv.getResult());
                                else
                                    onCursor.setAmount(onCursor.getAmount() + 1);
                            } else if (HOTBAR_ACTIONS.contains(event.getAction())) {
                                ItemStack hb = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                                if (ItemUtils.isNully(hb))
                                    event.getWhoClicked().getInventory().setItem(event.getHotbarButton(), inv.getResult());
                                else
                                    event.getWhoClicked().getInventory().addItem(inv.getResult());
                            }
                            inv.setResult(null);
                            for (int i = 0; i < mat.length - 1; i++) {
                                if (ItemUtils.isNotNully(mat[i])) {
                                    if (mat[i].getAmount() > 1)
                                        mat[i].setAmount(mat[i].getAmount() - 1);
                                    else
                                        mat[i] = new ItemStack(Material.AIR, 0);
                                }
                            }
                            inv.setMatrix(mat);
                            checkCrafting(inv, event.getWhoClicked());
                        } while (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                                && ItemUtils.isNotNully(inv.getResult())
                                && expectedResult.isSimilar(inv.getResult()));
                        Bukkit.getServer().getScheduler().runTaskLater( // Stupid workaround bc MC client is badly coded
                                Rail.INSTANCE, () -> ((Player)event.getWhoClicked()).updateInventory(), 2L);
                    }
                }
                event.setCancelled(true);
            } else {
                checkCraftingLater((CraftingInventory)event.getView().getTopInventory(), event.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.CRAFTING
                || event.getView().getTopInventory().getType() == InventoryType.WORKBENCH) {
            checkCraftingLater((CraftingInventory)event.getView().getTopInventory(), event.getWhoClicked());
        }
    }

    private static void checkCraftingLater(CraftingInventory inv, HumanEntity pl) {
        Bukkit.getServer().getScheduler().runTask(Rail.INSTANCE, () -> checkCrafting(inv, pl));
    }

    private static void checkCrafting(CraftingInventory inv, HumanEntity pl) {
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
    }

}
