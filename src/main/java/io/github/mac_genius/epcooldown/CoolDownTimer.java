package io.github.mac_genius.epcooldown;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;

/**
 * Created by Mac on 4/18/2015.
 */
public class CoolDownTimer implements Runnable {
    private Plugin plugin;

    public CoolDownTimer(Plugin pluginIn) {
        plugin = pluginIn;
    }

    @Override
    public void run() {
        ArrayList<? extends Player> list = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        for(Player p : list) {
            if (plugin.getConfig().getString("scoreboard").equals("true") || plugin.getConfig().getString("scoreboard").equals("false")) {
                if (p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).getScore() > 0) {
                    int score = p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).getScore();
                    score--;
                    p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).setScore(score);
                }
            }
            if (plugin.getConfig().getString("scoreboard").equals("fancy")) {
                ArrayList<String> entries = new ArrayList<>(p.getScoreboard().getEntries());
                for (String c: entries) {
                    ArrayList<Score> scores = new ArrayList<>(p.getScoreboard().getScores(c));
                    for (Score s : scores) {
                        if (s.getScore() == 1) {
                            int cooldowns = Integer.parseInt(s.getEntry());
                            if (cooldowns > 0) {
                                cooldowns--;
                                p.getScoreboard().resetScores(s.getEntry());
                                p.getScoreboard().getObjective("epcooldownfancy").getScore(cooldowns + "").setScore(1);
                            }
                        }
                    }
                }
            }
        }
    }
}
