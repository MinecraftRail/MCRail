package io.github.phantamanta44.mcrail.command;

import io.github.phantamanta44.mcrail.Rail;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.stream.Collectors;

public class CommandItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("rail.items")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }
        sender.sendMessage(new String[] {
                "Valid Item IDs:",
                Rail.itemRegistry().stream().map(Map.Entry::getKey).collect(Collectors.joining(", "))
        });
        return true;
    }

}
