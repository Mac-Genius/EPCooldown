package io.github.mac_genius.epcooldown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


/**
 * Created by Mac on 4/18/2015.
 */
public class ScoreboardSetup implements Runnable {
    private Player player;
    private Plugin plugin;
    private ScoreboardManager scoreboardManager;
    public ScoreboardSetup(Player playerIn, Plugin pluginIn) {
        player = playerIn;
        plugin = pluginIn;
        scoreboardManager = Bukkit.getScoreboardManager();
    }

    @Override
    public void run() {
        String output = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.scoreboard title"));
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("false")) {
            if (player.getScoreboard().getObjective("epcooldown") == null) {
                player.getScoreboard().registerNewObjective("epcooldown", "dummy");
                player.getScoreboard().getObjective("epcooldown").getScore(player.getName()).setScore(0);
                player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
            }
            if (player.getScoreboard().getObjective("epcooldown").getDisplaySlot() == DisplaySlot.SIDEBAR) {
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            }
            player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
        }
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("true")) {
            output = output.replaceAll("%player%", player.getName());
            if (player.getScoreboard().getObjective("epcooldown") == null) {
                player.getScoreboard().registerNewObjective("epcooldown", "dummy");
                player.getScoreboard().getObjective("epcooldown").getScore(player.getName()).setScore(0);
                player.getScoreboard().getObjective("epcooldown").setDisplayName(ChatColor.GREEN + "EPCooldown");
            }
            player.getScoreboard().getObjective("epcooldown").setDisplaySlot(DisplaySlot.SIDEBAR);
            player.getScoreboard().getObjective("epcooldown").setDisplayName(output);
        }
        if (plugin.getConfig().getString("scoreboard").equalsIgnoreCase("fancy")) {
            output = output.replaceAll("%player%", player.getName());
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
