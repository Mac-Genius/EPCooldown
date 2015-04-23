package io.github.mac_genius.epcooldown;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mac on 4/18/2015.
 */
public class InventoryUpdater implements Runnable {
    private Player player;
    private ItemStack itemStack;

    public InventoryUpdater(Player playerIn, ItemStack itemStackIn) {
        player = playerIn;
        itemStack = itemStackIn;

    }

    @Override
    public void run() {
        player.setItemInHand(itemStack);
    }
}
