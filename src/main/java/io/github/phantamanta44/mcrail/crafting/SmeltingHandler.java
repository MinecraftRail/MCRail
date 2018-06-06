package io.github.phantamanta44.mcrail.crafting;

import io.github.phantamanta44.mcrail.Rail;
import io.github.phantamanta44.mcrail.model.IContainerItem;
import io.github.phantamanta44.mcrail.util.AdapterUtils;
import io.github.phantamanta44.mcrail.util.ItemUtils;
import io.github.phantamanta44.mcrail.util.JsonUtils;
import io.github.phantamanta44.mcrail.util.TileUtils;
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

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SmeltingHandler implements Listener {

    private static final Set<Material> VANILLA_FUEL = EnumSet.of(
            Material.LAVA_BUCKET, Material.COAL_BLOCK, Material.BLAZE_ROD, Material.COAL, Material.BOAT, Material.LOG,
            Material.LOG_2, Material.WOOD, Material.WOOD_PLATE, Material.FENCE, Material.FENCE_GATE,
            Material.WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.SPRUCE_WOOD_STAIRS,
            Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS, Material.TRAP_DOOR, Material.WORKBENCH,
            Material.BOOKSHELF, Material.CHEST, Material.TRAPPED_CHEST, Material.DAYLIGHT_DETECTOR, Material.JUKEBOX,
            Material.NOTE_BLOCK, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2, Material.BANNER, Material.BOW,
            Material.FISHING_ROD, Material.LADDER, Material.WOOD_PICKAXE, Material.WOOD_AXE, Material.WOOD_SWORD,
            Material.WOOD_HOE, Material.WOOD_SPADE, Material.SIGN, Material.WOOD_DOOR, Material.DARK_OAK_DOOR_ITEM,
            Material.ACACIA_DOOR_ITEM, Material.BIRCH_DOOR_ITEM, Material.SPRUCE_DOOR_ITEM, Material.JUNGLE_DOOR_ITEM,
            Material.WOOD_STEP, Material.SAPLING, Material.BOWL, Material.STICK, Material.WOOD_BUTTON, Material.WOOL,
            Material.CARPET);
    private static final String PLACEHOLDER =
            ChatColor.LIGHT_PURPLE + ChatColor.RESET.toString() + ChatColor.GREEN + "Smelting Placeholder";

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getType() == InventoryType.FURNACE) {
            if (event.getSlotType() == InventoryType.SlotType.FUEL
                    && !VANILLA_FUEL.contains(event.getCursor().getType())
                    && Rail.recipes().getBurnTime(event.getCursor()) != 0) {
                event.setCancelled(true);
                if (event.getClick() == ClickType.LEFT) {
                    if (ItemUtils.isNotNully(event.getCurrentItem())) {
                        if (ItemUtils.isMatch(event.getCurrentItem(), event.getCursor())) {
                            int transfer = Math.min(
                                    event.getCurrentItem().getAmount() + event.getCursor().getAmount(),
                                    event.getCursor().getMaxStackSize() - event.getCurrentItem().getAmount());
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                                event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() + transfer);
                                event.getCursor().setAmount(event.getCursor().getAmount() - transfer);
                                if (ItemUtils.isNully(event.getCursor()))
                                    event.setCursor(null);
                            });
                        } else {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                                ItemStack cursor = event.getCursor();
                                event.setCursor(event.getCurrentItem());
                                event.setCurrentItem(cursor);
                            });
                        }
                    } else {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                            event.setCurrentItem(event.getCursor());
                            event.setCursor(null);
                        });
                    }
                } else if (event.getClick() == ClickType.RIGHT) {
                    if (ItemUtils.isNotNully(event.getCurrentItem())) {
                        if (ItemUtils.isMatch(event.getCurrentItem(), event.getCursor())) {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                                if (event.getCurrentItem().getAmount() < event.getCurrentItem().getMaxStackSize()) {
                                    event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() + 1);
                                    if (event.getCursor().getAmount() == 1)
                                        event.setCursor(null);
                                    else
                                        event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                                }
                            });
                        } else {
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                                ItemStack cursor = event.getCursor();
                                event.setCursor(event.getCurrentItem());
                                event.setCurrentItem(cursor);
                            });
                        }
                    } else {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Rail.INSTANCE, () -> {
                            if (event.getCursor().getAmount() == 1) {
                                event.setCurrentItem(event.getCursor());
                                event.setCursor(null);
                            } else {
                                event.setCurrentItem(event.getCursor().clone());
                                event.getCurrentItem().setAmount(1);
                                event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                            }
                        });
                    }
                }
            }
        }
        placeholderCheckLater((Player)event.getWhoClicked(), event.getView().getBottomInventory());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        placeholderCheckLater((Player)event.getWhoClicked(), event.getView().getBottomInventory());
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
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
        if (!canBurn(inv.getSmelting(), inv)) {
            event.setCancelled(true);
        } else {
            int burnTime = Rail.recipes().getBurnTime(inv.getFuel());
            if (burnTime > 0) {
                IContainerItem containerItem = AdapterUtils.adapt(IContainerItem.class, inv.getFuel());
                if (containerItem != null) {
                    Bukkit.getServer().getScheduler()
                            .scheduleSyncDelayedTask(Rail.INSTANCE, () -> inv.setFuel(containerItem.getContainer()));
                }
                event.setBurning(true);
                event.setBurnTime(burnTime);
            }
        }
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
            lore.add(ChatColor.RESET + TileUtils.formatName(result.getType().name()));
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
