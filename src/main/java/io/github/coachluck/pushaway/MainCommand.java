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
            // TODO Send help
        }
        if(args.length == 1) {
            if(args[0].toLowerCase().startsWith("r")) {
                if(!sender.hasPermission("pushaway.reload")) {
                    sender.sendMessage(ItemUtil.format(plugin.getConfig().getString("Messages.Permission")));
                    return true;
                }

                // perm message
                plugin.reloadConfig();
                plugin.wand = ItemUtil.getWand();
                sender.sendMessage(ItemUtil.format("&7Push&cAway &areloaded..."));
                return true;
            }
            if(args[0].toLowerCase().startsWith("g")) {
                // give wand
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
                // TODO : Unkown Command
                return true;
            }
            if(!sender.hasPermission("pushaway.givewand.others")) {
                sender.sendMessage(ItemUtil.format(plugin.getConfig().getString("Messages.Permission")));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[1]);
            target.getInventory().addItem(plugin.wand);
            target.sendMessage(ItemUtil.format(giveMsg));
            sender.sendMessage(ItemUtil.format(giveOtherMsg.replaceAll("%player%", target.getName())));
        }
        return true;
    }
}
