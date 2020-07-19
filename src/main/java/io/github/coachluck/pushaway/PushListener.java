/*
 *     File: PushListener.java
 *     Last Modified: 7/19/20, 6:25 PM
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

import io.github.coachluck.pushaway.utils.Cooldown;
import io.github.coachluck.pushaway.utils.ItemUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public class PushListener implements Listener {
    private final Main plugin;

    public PushListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if((e.getAction() != Action.RIGHT_CLICK_AIR) && (e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;
        final int slotId = e.getPlayer().getInventory().getHeldItemSlot();

        final ItemMeta heldItemMeta = e.getPlayer().getInventory().getItem(slotId).getItemMeta();
        if(!heldItemMeta.getDisplayName().equals(plugin.wand.getItemMeta().getDisplayName())) return;
        if(!heldItemMeta.getLore().equals(plugin.wand.getItemMeta().getLore())) return;

        e.setCancelled(true);
        final Player player = e.getPlayer();
        final UUID pUUID = player.getUniqueId();

        if(player.hasPermission("pushaway.bypass-cooldown") && plugin.cooldowns.containsKey(pUUID)) {
            Cooldown cooldown = plugin.cooldowns.get(pUUID);
            player.sendMessage(ItemUtil.format(
                    plugin.getConfig().getString("Messages.Cooldown")
                            .replaceAll("%time%", Integer.toString(cooldown.getTimeRemaining()))
            ));
            return;
        }

        doLaunch(player);
    }

    private void doLaunch(Player player) {
        final UUID pUUID = player.getUniqueId();
        final int range = plugin.getConfig().getInt("Push-Away-Range");
        int playerCount = launchSurroundingPlayers(player, player.getNearbyEntities(range, range, range));

        if(playerCount == 0) {
            player.sendMessage(ItemUtil.format(
                    plugin.getConfig().getString("Messages.No-Players-Nearby")
            ));
            return;
        }
        if (plugin.getConfig().getBoolean("Sound.When-Pushed.Enabled")) {
            player.playSound(player.getLocation(),
                    Sound.valueOf(plugin.getConfig().getString("Sound.On-Push.Sound")),
                    (float) plugin.getConfig().getDouble("Sound.On-Push.Volume"),
                    (float) plugin.getConfig().getDouble("Sound.On-Push.Pitch"));
        }

        plugin.cooldowns.put(pUUID, new Cooldown(pUUID, plugin.getConfig().getInt("Usage-Cooldown")));
        player.sendMessage(ItemUtil.format(
                plugin.getConfig().getString("Messages.Pushed-Players")
                        .replaceAll("%num%", Integer.toString(playerCount))
        ));
    }

    private int launchSurroundingPlayers(Player player, List<Entity> nearbyPlayers) {
        int playerCount = 0;
        for (Entity entity : nearbyPlayers) {
            if (entity instanceof Player) {
                playerCount++;
                Player target = (Player) entity;
                launchPlayer(player, target);
                // TODO : Add particle effects
            }
        }
        return playerCount;
    }

    private void launchPlayer(Player player, Player target) {
        final double launchX = plugin.getConfig().getDouble("Push-Away-Launch-X");
        final double launchY = plugin.getConfig().getDouble("Push-Away-Launch-Y");
        final Location playerCenterLocation = player.getEyeLocation();
        final Location playerToThrowLocation = target.getEyeLocation();

        final double x = playerToThrowLocation.getX() - playerCenterLocation.getX();
        final double y = playerToThrowLocation.getY() - playerCenterLocation.getY();
        final double z = playerToThrowLocation.getZ() - playerCenterLocation.getZ();

        final Vector throwVector = new Vector(x, y, z);

        throwVector.normalize();
        throwVector.multiply(launchX);
        throwVector.setY(launchY);

        target.setVelocity(throwVector);
        target.sendMessage(ItemUtil.format(
                plugin.getConfig().getString("Messages.Pushed-By-Player")
                        .replaceAll("%player%", player.getName())
        ));

        if(plugin.getConfig().getBoolean("Sound.When-Pushed.Enabled")) {
            target.playSound(target.getLocation(),
                    Sound.valueOf(plugin.getConfig().getString("Sound.When-Pushed.Sound")),
                    (float) plugin.getConfig().getDouble("Sound.When-Pushed.Volume"),
                    (float) plugin.getConfig().getDouble("Sound.When-Pushed.Pitch"));
        }
        // TODO : Add particle effects
    }
}
