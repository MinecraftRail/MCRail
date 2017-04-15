package io.github.phantamanta44.mcrail.sign;

import io.github.phantamanta44.mcrail.RailMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Sign;

import java.util.List;

public class SignBlockHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack stack = event.getItemInHand();
        if (stack.getType() == Material.SIGN && stack.hasItemMeta()) {
            ItemMeta meta = stack.getItemMeta();
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                String last = lore.get(lore.size() - 1);
                if (last.startsWith("ID: ")) {
                    String id = last.substring(4);
                    if (RailMain.INSTANCE.registry().isValidId(id)) {
                        RailMain.INSTANCE.signManager().register(id, event.getBlock());
                        Bukkit.getServer().getScheduler().runTaskLater(
                                RailMain.INSTANCE, () -> event.getPlayer().closeInventory(), 1L);
                    } else {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "Not a valid sign!");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignEdit(SignChangeEvent event) {
        if (RailMain.INSTANCE.signManager().existsAt(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN)) {
            RailMain.INSTANCE.signManager().onSignClick(event);
        }
    }

    private boolean breakCheck(Block block) {
        return (block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN)
                && RailMain.INSTANCE.signManager().breakCheck(block);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (breakCheck(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(this::breakCheck);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(this::breakCheck);
    }

    @EventHandler
    public void onPhysicsUpdate(BlockPhysicsEvent event) {
        if (event.getBlock().getType() == Material.SIGN_POST || event.getBlock().getType() == Material.WALL_SIGN) {
            Sign sign = (Sign)event.getBlock().getState().getData();
            if (!event.getBlock().getRelative(sign.getAttachedFace()).getType().isSolid()
                    && RailMain.INSTANCE.signManager().breakCheck(event.getBlock())) {
                event.setCancelled(true);
            }
        }
    }

}
