package io.github.phantamanta44.mcrail.command;

import io.github.phantamanta44.mcrail.Rail;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class CommandItems implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("rail.items")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }
        int page = 1;
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                try {
                    Pattern pattern = Pattern.compile(StringUtils.join(args), Pattern.CASE_INSENSITIVE);
                    sender.sendMessage(new String[]{
                            ChatColor.GRAY + String.format("Item IDs Containing /%s/:", pattern.pattern()),
                            Rail.itemRegistry().stream()
                                    .map(Map.Entry::getKey)
                                    .filter(pattern.asPredicate())
                                    .sorted()
                                    .collect(Collectors.joining("\n"))
                    });
                } catch (PatternSyntaxException e2) {
                    sender.sendMessage(ChatColor.RED + "Invalid regular expression!");
                }
                return true;
            }
        }
        List<String> ids = Rail.itemRegistry().stream().map(Map.Entry::getKey).sorted().collect(Collectors.toList());
        int pages = (int)Math.ceil((float)ids.size() / 8F);
        if (page < 1 || page > pages) {
            sender.sendMessage(ChatColor.RED + "Invalid page number!");
        } else {
            sender.sendMessage(new String[]{
                    ChatColor.GRAY + String.format("Valid Item IDs (Page %d of %d):", page, pages),
                    ids.stream().skip((page - 1) * 8).limit(8).collect(Collectors.joining("\n"))
            });
        }
        return true;
    }

}
