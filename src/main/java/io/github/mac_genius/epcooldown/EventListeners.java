package io.github.mac_genius.epcooldown;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Endermite;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;

/**
 * This class implements the listener. It checks to see if
 * the player right clicks an enderpearl and if the cooldown
 * is greater that 0, the enderpearl will not be thrown. If the
 * player's cooldown is 0, the enderpearl will be thrown.
 *
 * @author John Harrison
 */
public class EventListeners implements Listener {
    private Plugin plugin;
    private BukkitScheduler scheduler;

    /**
     * This gets the main instance of the plugin.
     *
     * @param pluginIn is the main instance of the plugin
     */
    public EventListeners(Plugin pluginIn) {
        plugin = pluginIn;
        scheduler = plugin.getServer().getScheduler();
    }

    /**
     * This checks for a player to right click an enderpearl.
     *
     * @param event is the PlayerInteract event
     */
    @EventHandler
    public void rightClickPearl(PlayerInteractEvent event) {

        // Gets the messages from the config file
        String output = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.cooldown message"));
        String rightClick = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.rightclick message"));
        int cooldownTime = plugin.getConfig().getInt("cooldown");
        if (event.getPlayer().hasPermission("epc.use")) {

            // If the player clicks an enderpearl and isn't in creative mode
            if (event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {

                    // If the config setting "scoreboard" is true or false
                    if (plugin.getConfig().getString("scoreboard").equals("true") || plugin.getConfig().getString("scoreboard").equals("false")) {
                        output = output.replaceAll("%seconds%", event.getPlayer().getScoreboard().getObjective("epcooldown").getScore(event.getPlayer().getName()).getScore() + "");

                        // If the player's cooldown is greater than 0
                        if (event.getPlayer().getScoreboard().getObjective("epcooldown").getScore(event.getPlayer().getName()).getScore() > 0) {
                            event.setUseItemInHand(Event.Result.DENY);
                            ItemStack original = event.getPlayer().getItemInHand();
                            event.getPlayer().setItemInHand(null);
                            scheduler.runTaskLater(plugin, new InventoryUpdater(event.getPlayer(), original), 1);
                            if (!output.equals("")) {
                                event.getPlayer().sendMessage(output);
                            }
                        } else {
                            event.getPlayer().getScoreboard().getObjective("epcooldown").getScore(event.getPlayer().getName()).setScore(cooldownTime);
                        }
                    }

                    // If the config setting "scoreboard" is fancy
                    if (plugin.getConfig().getString("scoreboard").equals("fancy")) {

                        // Gets the list of entries on the player's scoreboard
                        ArrayList<String> entries = new ArrayList<>(event.getPlayer().getScoreboard().getEntries());
                        for (String s : entries) {

                            // Gets the list of scores for entries on the player's scoreboard
                            ArrayList<Score> scores = new ArrayList<>(event.getPlayer().getScoreboard().getScores(s));
                            for (Score c : scores) {
                                for (Score v : scores) {

                                    // If the score equals 1
                                    if (v.getScore() == 1) {
                                        int cooldowns = Integer.parseInt(v.getEntry());
                                        if (cooldowns > 0) {
                                            output = output.replaceAll("%seconds%", cooldowns + "");
                                            event.setUseItemInHand(Event.Result.DENY);
                                            ItemStack original = event.getPlayer().getItemInHand();
                                            event.getPlayer().setItemInHand(null);
                                            scheduler.runTaskLater(plugin, new InventoryUpdater(event.getPlayer(), original), 1);
                                            if (!output.equals("")) {
                                                event.getPlayer().sendMessage(output);
                                            }
                                        } else {
                                            event.getPlayer().getScoreboard().resetScores(v.getEntry());
                                            event.getPlayer().getScoreboard().getObjective("epcooldownfancy").getScore(cooldownTime + "").setScore(1);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // If a player right clicks a block with the enderpearl
                }else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setUseItemInHand(Event.Result.DENY);
                    ItemStack original = event.getPlayer().getItemInHand();
                    event.getPlayer().setItemInHand(null);
                    scheduler.runTaskLater(plugin, new InventoryUpdater(event.getPlayer(), original), 1);
                    if (!rightClick.equals("")) {
                        event.getPlayer().sendMessage(rightClick);
                    }
                }
            }
        }
    }

    /**
     * Checks to see if a player joins.
     *
     * @param event is the PlayerJoin event
     */
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        scheduler.runTask(plugin, new ScoreboardSetup(event.getPlayer(), plugin));
    }

    /**
     * Checks to see if an endermite spawns.
     *
     * @param event is the CreatureSpawn event
     */
    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent event) {
        String spawnEndermites = plugin.getConfig().getString("endermite spawning");
        if (spawnEndermites.equalsIgnoreCase("false")) {
            if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && event.getEntity() instanceof Endermite) {
                event.getEntity().remove();
            }
        }
    }
}
