package io.github.coachluck.pushaway;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static ItemStack getWand() {
        final PushAway plugin = PushAway.getPlugin(PushAway.class);
        final String materialName = plugin.getConfig().getString("Wand.Material");
        final Material mat = Material.getMaterial(materialName);

        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(format(plugin.getConfig().getString("Wand.Name")));

        List<String> lore = new ArrayList<>();
        for(String str : plugin.getConfig().getStringList("Wand.Lore")) {
            lore.add(format(str.replaceAll("%range%", Integer.toString(plugin.getConfig().getInt("Push-Away-Range")))));
        }

        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }

    public static String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
