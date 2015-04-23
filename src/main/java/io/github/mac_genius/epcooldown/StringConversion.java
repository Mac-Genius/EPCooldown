package io.github.mac_genius.epcooldown;

import org.bukkit.ChatColor;

/**
 * Created by Mac on 4/22/2015.
 */
public class StringConversion {

    public String conversion(String convertIn) {
        String output = "";
        char[] array = convertIn.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            if (c == '&') {
                if (array[i+1] == '0') {
                    output += ChatColor.BLACK;
                    i++;
                }
                else if (array[i+1] == '1') {
                    output += ChatColor.DARK_BLUE;
                    i++;
                }
                else if (array[i+1] == '2') {
                    output += ChatColor.DARK_GREEN;
                    i++;
                }
                else if (array[i+1] == '3') {
                    output += ChatColor.DARK_AQUA;
                    i++;
                }
                else if (array[i+1] == '4') {
                    output += ChatColor.DARK_RED;
                    i++;
                }
                else if (array[i+1] == '5') {
                    output += ChatColor.DARK_PURPLE;
                    i++;
                }
                else if (array[i+1] == '6') {
                    output += ChatColor.GOLD;
                    i++;
                }
                else if (array[i+1] == '7') {
                    output += ChatColor.GRAY;
                    i++;
                }
                else if (array[i+1] == '8') {
                    output += ChatColor.DARK_GRAY;
                    i++;
                }
                else if (array[i+1] == '9') {
                    output += ChatColor.BLUE;
                    i++;
                }
                else if (array[i+1] == 'a') {
                    output += ChatColor.GREEN;
                    i++;
                }
                else if (array[i+1] == 'b') {
                    output += ChatColor.AQUA;
                    i++;
                }
                else if (array[i+1] == 'c') {
                    output += ChatColor.RED;
                    i++;
                }
                else if (array[i+1] == 'd') {
                    output += ChatColor.LIGHT_PURPLE;
                    i++;
                }
                else if (array[i+1] == 'e') {
                    output += ChatColor.YELLOW;
                    i++;
                }
                else if (array[i+1] == 'f') {
                    output += ChatColor.WHITE;
                    i++;
                }
            }
            else {
                output += c;
            }
        }
        return output;
    }
}