package io.github.mac_genius.epcooldown;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created by Mac on 4/18/2015.
 */
public class EPCooldown extends JavaPlugin {
    Plugin plugin = this;

    public void onEnable() {
        plugin.saveDefaultConfig();
        BukkitScheduler scheduler = this.getServer().getScheduler();
        this.getCommand("epc").setExecutor(new Commands(this));
        getServer().getPluginManager().registerEvents(new EventListeners(plugin), this);
        scheduler.runTaskTimer(plugin, new CoolDownTimer(plugin), 0, 20);
        getLogger().info("EPCooldown Enabled");
    }

    public void onDisable() {
        getLogger().info("Plugin Disabled");
    }
}
