/*
 *     File: MainCommand.java
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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    private final PushAway plugin;

    public MainCommand(PushAway plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final String giveMsg = plugin.getConfig().getString("Messages.Given-Wand");
        String giveOtherMsg = plugin.getConfig().getString("Messages.Give-Other-Wand");
        if(args.length == 0) {
            if(sender.hasPermission("pushaway.use")) {

                sender.sendMessage(ItemUtil.format("&8&m         [ &ePushAway &8]&m         "));
                sender.sendMessage(ItemUtil.format("&e/pushaway &8- &7Shows this page. Aliases &8[&7pa, pusha, paway&8]"));
                if(sender.hasPermission("pushaway.reload"))
                    sender.sendMessage(ItemUtil.format("&e/pushaway reload &8- &7Reloads the plugin."));
                if(sender.hasPermission("pushaway.givewand")) {
                    sender.sendMessage(ItemUtil.format("&e/pushaway give &8- &7Gives you a wand."));
                }
                if(sender.hasPermission("pushaway.givewand.others")) {
                    sender.sendMessage(ItemUtil.format("&e/pushaway give <player> &8- &7Gives the player a wand."));
                }
            }
            return true;
        }
        if(args.length == 1) {
            if(args[0].toLowerCase().startsWith("r")) {
                if(!sender.hasPermission("pushaway.reload")) {
                    sender.sendMessage(ItemUtil.format(plugin.getConfig().getString("Messages.Permission")));
                    return true;
                }
                plugin.reloadConfig();
                plugin.wand = ItemUtil.getWand();
                sender.sendMessage(ItemUtil.format("&7Push&cAway &areloaded..."));
                return true;
            }
            if(args[0].toLowerCase().startsWith("g")) {
                if(!sender.hasPermission("pushaway.givewand")) {
                    sender.sendMessage(ItemUtil.format(plugin.getConfig().getString("Messages.Permission")));
                    return true;
                }
                if(sender instanceof Player) {
                    Player p = (Player) sender;
                    p.getInventory().addItem(plugin.wand);
                    p.sendMessage(ItemUtil.format(giveMsg));
                    return true;
                }

                sender.sendMessage(ItemUtil.format("&cYou must be a player to use this command!"));
                return true;
            }
        }
        if(args.length == 2) {
            if(!args[0].toLowerCase().startsWith("g")) {
                sender.sendMessage("&cUnkown command! &eTry /pushaway for help.");
                return true;
            }
            if(!sender.hasPermission("pushaway.givewand.others")) {
                sender.sendMessage(ItemUtil.format(plugin.getConfig().getString("Messages.Permission")));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[1]);
            if(target == null) {
                sender.sendMessage("&c" + args[1] + " &7is currently offline.");
                return true;
            }
            target.getInventory().addItem(plugin.wand);
            target.sendMessage(ItemUtil.format(giveMsg));
            sender.sendMessage(ItemUtil.format(giveOtherMsg.replaceAll("%player%", target.getName())));
        }
        return true;
    }
}
