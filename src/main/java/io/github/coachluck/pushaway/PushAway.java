/*
 *     File: PushAway.java
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
