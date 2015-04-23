package io.github.mac_genius.epcooldown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * Created by Mac on 4/18/2015.
 */
public class Commands implements CommandExecutor {
    private Plugin plugin;

    public Commands(Plugin pluginIn) {
        plugin = pluginIn;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("epc")) {
            if (commandSender.hasPermission("epc.help")) {
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- EPCooldown Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc reload" + ChatColor.WHITE + " reloads the config");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc help" + ChatColor.WHITE + " commands");
                    return true;
                }
            }
            if (commandSender.hasPermission("epc.reload")) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    commandSender.sendMessage(ChatColor.GREEN + "[EPCooldown]" + ChatColor.WHITE + " Config reloaded.");
                    return true;
                }
            }
            if (commandSender.hasPermission("epc.help")) {
                if (strings[0].equalsIgnoreCase("help")) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- EPCooldown Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc reload" + ChatColor.WHITE + " reloads the config");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc help" + ChatColor.WHITE + " commands");
                    return true;
                }
            }
        }

        return false;
    }
}
