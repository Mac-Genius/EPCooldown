package io.github.mac_genius.epcooldown;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;

/**
 * This class updates the players' cooldown time.
 *
 * @author John Harrison
 */
public class CoolDownTimer implements Runnable {
    private Plugin plugin;

    /**
     * This gets the main instance of the plugin.
     *
     * @param pluginIn is the main instance of the plugin
     */
    public CoolDownTimer(Plugin pluginIn) {
        plugin = pluginIn;
    }

    /**
     * The updates the players' cooldown time.
     */
    @Override
    public void run() {

        // A list of all online players
        ArrayList<? extends Player> list = new ArrayList<>(plugin.getServer().getOnlinePlayers());
        for(Player p : list) {

            // If the config setting "scoreboard" is true or false
            if (plugin.getConfig().getString("scoreboard").equals("true") || plugin.getConfig().getString("scoreboard").equals("false")) {

                // If the players' cooldown time is greater than 0
                if (p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).getScore() > 0) {
                    int score = p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).getScore();
                    score--;
                    p.getScoreboard().getObjective("epcooldown").getScore(p.getName()).setScore(score);
                }
            }

            // If the config setting "scoreboard" is fancy
            if (plugin.getConfig().getString("scoreboard").equals("fancy")) {

                // Gets the entries on the players' scoreboards
                ArrayList<String> entries = new ArrayList<>(p.getScoreboard().getEntries());
                for (String c: entries) {

                    // Gets the scores of each entry on the scoreboard
                    ArrayList<Score> scores = new ArrayList<>(p.getScoreboard().getScores(c));
                    for (Score s : scores) {

                        // If the scoreboard entry's score is 1
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
