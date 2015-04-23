package io.github.mac_genius.epcooldown;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * This class handles the commands for the plugin.
 *
 * @author John Harrison
 */
public class Commands implements CommandExecutor {
    private Plugin plugin;

    /**
     * This fetches the main instance of the plugin.
     *
     * @param pluginIn is the main instance of the plugin
     */
    public Commands(Plugin pluginIn) {
        plugin = pluginIn;
    }

    /**
     * This is activated when a player uses a command.
     *
     * @param commandSender is the person that activates the command
     * @param command is the command activated
     * @param s is not used
     * @param strings is the extra words associated with the command
     * @return is whether the command is activated or not
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // If the player does /epc
        if (command.getName().equalsIgnoreCase("epc")) {
            if (commandSender.hasPermission("epc.help")) {
                // If the player only does /epc
                if (strings.length == 0) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- EPCooldown Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc reload" + ChatColor.WHITE + " reloads the config");
                    commandSender.sendMessage(ChatColor.GOLD + "/epc help" + ChatColor.WHITE + " commands");
                    return true;
                }
            }

            // If the player does /epc reload
            if (commandSender.hasPermission("epc.reload")) {
                if (strings[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    commandSender.sendMessage(ChatColor.GREEN + "[EPCooldown]" + ChatColor.WHITE + " Config reloaded.");
                    return true;
                }
            }

            // If the player does /epc help
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
