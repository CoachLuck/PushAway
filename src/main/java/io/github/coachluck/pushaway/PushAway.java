package io.github.coachluck.pushaway;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public final class PushAway extends JavaPlugin {

    public ItemStack wand;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.getCommand("pushaway").setExecutor(new MainCommand(this));
        getServer().getPluginManager().registerEvents(new MainListener(this), this);
        wand = ItemUtil.getWand();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
