package io.github.mac_genius.epcooldown;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This class sets what the player is holding back to the
 * original amount.
 *
 * @author John Harrison
 */
public class InventoryUpdater implements Runnable {
    private Player player;
    private ItemStack itemStack;

    /**
     * This takes in the player that right clicked the item and what he is holding.
     *
     * @param playerIn is the player who right clicked on the item
     * @param itemStackIn is what the player is holding
     */
    public InventoryUpdater(Player playerIn, ItemStack itemStackIn) {
        player = playerIn;
        itemStack = itemStackIn;
    }

    /**
     * This resets what the player was holding.
     */
    @Override
    public void run() {
        player.setItemInHand(itemStack);
    }
}
