package io.github.coachluck.pushaway;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class MainListener implements Listener {
    PushAway plugin;

    public MainListener(PushAway plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if(!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        int slotId = e.getPlayer().getInventory().getHeldItemSlot();
        ItemStack heldItem = e.getPlayer().getInventory().getItem(slotId);
        if(!heldItem.getType().equals(plugin.wand.getType())) return;
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        if(!heldItemMeta.getDisplayName().equals(plugin.wand.getItemMeta().getDisplayName())) return;
        if(!heldItemMeta.getLore().equals(plugin.wand.getItemMeta().getLore())) return;

        e.setCancelled(true);
        Player player = e.getPlayer();
        final int range = plugin.getConfig().getInt("Push-Away-Range");
        final double launchX = plugin.getConfig().getDouble("Push-Away-Launch-X");
        final double launchY = plugin.getConfig().getDouble("Push-Away-Launch-Y");
        boolean found = false;
        for (Entity entity : player.getNearbyEntities(range, range, range)) {
            if (entity instanceof Player) {
                found = true;
                Player p = (Player) entity;

                Location playerCenterLocation = player.getEyeLocation();
                Location playerToThrowLocation = p.getEyeLocation();

                double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
                double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
                double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

                Vector throwVector = new Vector(x, y, z);

                throwVector.normalize();
                throwVector.multiply(launchX);
                throwVector.setY(launchY);

                p.setVelocity(throwVector);
            }
        }

        if(found) {
            // TODO : SEND PUSH AWAY MESSAGE
            player.sendMessage(ItemUtil.format("&7You have pushed away surrounding players!"));
        }

    }
}
