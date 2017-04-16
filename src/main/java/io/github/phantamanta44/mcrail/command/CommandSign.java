package io.github.phantamanta44.mcrail.command;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class CommandSign implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        } else if (!sender.hasPermission("rail.sign")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        } else if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Must provide a sign ID!");
            return true;
        } else if (!Rail.signRegistry().isValidId(args[0])) {
            sender.sendMessage(ChatColor.RED + "Invalid sign ID!");
            return true;
        }
        int amount = 1;
        if (args.length > 1) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid amount!");
                return true;
            }
        }
        ItemStack stack = new ItemStack(Material.SIGN, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("Rail Sign: " + args[0]);
        meta.setLore(Collections.singletonList("ID: " + args[0]));
        stack.setItemMeta(meta);
        ((Player)sender).getInventory().addItem(stack);
        return true;
    }

}
