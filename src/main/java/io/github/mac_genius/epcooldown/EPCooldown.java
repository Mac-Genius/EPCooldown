package io.github.mac_genius.epcooldown;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * This is the main class for the plugin. It forces
 * player to have a cooldown time before he can throw
 * another enderpearl.
 *
 * @author John Harrison
 */
public class EPCooldown extends JavaPlugin {
    Plugin plugin = this;

    /**
     * This is called when the plugin is enabled. It registers
     * the commands and the listeners.
     */
    public void onEnable() {
        plugin.saveDefaultConfig();
        BukkitScheduler scheduler = this.getServer().getScheduler();
        this.getCommand("epc").setExecutor(new Commands(this));
        getServer().getPluginManager().registerEvents(new EventListeners(plugin), this);
        scheduler.runTaskTimer(plugin, new CoolDownTimer(plugin), 0, 20);
        getLogger().info("EPCooldown Enabled");
    }

    /**
     * This is called when the plugin is disabled.
     */
    public void onDisable() {
        getLogger().info("Plugin Disabled");
    }
}
