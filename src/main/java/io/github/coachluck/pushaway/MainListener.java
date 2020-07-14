/*
 *     File: MainListener.java
 *     Last Modified: 7/14/20, 4:30 AM
 *     Project: PushAway
 *     Copyright (C) 2020 CoachL_ck
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
