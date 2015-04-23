package io.github.mac_genius.epcooldown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


/**
 * This sets up a player's scoreboard when he/she joins the game
 *
 * @author John Harrison
 */
public class ScoreboardSetup implements Runnable {
    private Player player;
    private Plugin plugin;
    private ScoreboardManager scoreboardManager;

    /**
     * This gets the player who joined and the main instance of the plugin.
     *
     * @param playerIn is the player who joined
     * @param pluginIn is the main instance of the plugin
     */
    public ScoreboardSetup(Player playerIn, Plugin pluginIn) {
        player = playerIn;
        plugin = pluginIn;
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    /**
     * This sets up the scoreboard for the player.
     */
    @Override
    public void run() {

        // Gets the scoreboard title
        String output = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.scoreboard title"));

        // If "scoreboard" in the config is false
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("false")) {

            // If the player does not have the objective yet
            if (player.getScoreboard().getObjective("epcooldown") == null) {
                player.getScoreboard().registerNewObjective("epcooldown", "dummy");
                player.getScoreboard().getObjective("epcooldown").getScore(player.getName()).setScore(0);
                player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
            }

            // Removes the objective from the sidebar
            if (player.getScoreboard().getObjective("epcooldown").getDisplaySlot() == DisplaySlot.SIDEBAR) {
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            }
            player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
        }

        // If "scoreboard" in the config is true
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("true")) {
            output = output.replaceAll("%player%", player.getName());

            // If the player does not have the objective yet
            if (player.getScoreboard().getObjective("epcooldown") == null) {
                player.getScoreboard().registerNewObjective("epcooldown", "dummy");
                player.getScoreboard().getObjective("epcooldown").getScore(player.getName()).setScore(0);
                player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
            }

            // Adds the object to the sidebar
            player.getScoreboard().getObjective("epcooldown").setDisplaySlot(DisplaySlot.SIDEBAR);
            player.getScoreboard().getObjective("epcooldown").setDisplayName(output);
        }

        // If "scoreboard" in the config is fancy
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("fancy")) {
            output = output.replaceAll("%player%", player.getName());

            // Gets a new scoreboard and sets it up
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
            player.setScoreboard(scoreboard);
            player.getScoreboard().registerNewObjective("epcooldownfancy", "dummy");
            player.getScoreboard().getObjective("epcooldownfancy").setDisplayName(output);
            player.getScoreboard().getObjective("epcooldownfancy").getScore(ChatColor.BLUE + "").setScore(3);
            player.getScoreboard().getObjective("epcooldownfancy").getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Cooldown Time:").setScore(2);
            player.getScoreboard().getObjective("epcooldownfancy").getScore("0").setScore(1);
            player.getScoreboard().getObjective("epcooldownfancy").setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }
}
