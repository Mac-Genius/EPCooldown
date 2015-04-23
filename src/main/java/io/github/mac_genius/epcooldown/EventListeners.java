package io.github.mac_genius.epcooldown;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import java.util.HashSet;

/**
 * Created by Mac on 4/18/2015.
 */
public class EventListeners implements Listener {
    private Plugin plugin;
    private BukkitScheduler scheduler;
    private int cooldown;
    private HashSet<Material> hashSet;
    public EventListeners(Plugin pluginIn) {
        plugin = pluginIn;
        scheduler = plugin.getServer().getScheduler();
        cooldown = 16;
        hashSet = new HashSet<>();
        hashSet.add(Material.AIR);
    }

    @EventHandler
    public void rightClickPearl(PlayerInteractEvent event) {
        //StringConversion string = new StringConversion();
        String output = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.cooldown message"));
        String rightClick = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("custom messages.rightclick message"));
        int cooldownTime = plugin.getConfig().getInt("cooldown");
        ArrayList<Block> list = new ArrayList<>(event.getPlayer().getLineOfSight(hashSet, 30));
        //event.getPlayer().sendMessage(list.toString());
        if (event.getPlayer().hasPermission("epc.use")) {
            if (event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL && !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if (plugin.getConfig().getString("scoreboard").equals("true") || plugin.getConfig().getString("scoreboard").equals("false")) {
                        output = output.replaceAll("%seconds%", event.getPlayer().getScoreboard().getObjective("epcooldown").getScore(event.getPlayer().getName()).getScore() + "");
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
                    if (plugin.getConfig().getString("scoreboard").equals("fancy")) {
                        ArrayList<String> entries = new ArrayList<>(event.getPlayer().getScoreboard().getEntries());
                        for (String s : entries) {
                            ArrayList<Score> scores = new ArrayList<>(event.getPlayer().getScoreboard().getScores(s));
                            for (Score c : scores) {
                                for (Score v : scores) {
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

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        scheduler.runTask(plugin, new ScoreboardSetup(event.getPlayer(), plugin));
    }

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
