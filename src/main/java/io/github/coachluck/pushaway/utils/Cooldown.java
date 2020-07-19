/*
 *     File: Cooldown.java
 *     Last Modified: 7/19/20, 5:51 PM
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

package io.github.coachluck.pushaway.utils;

import io.github.coachluck.pushaway.Main;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Cooldown {

    @Getter private int timeRemaining;

    public Cooldown(UUID uuid, int cooldownTime) {
        Main plugin = Main.getPlugin(Main.class);
        timeRemaining = cooldownTime;

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                timeRemaining--;
                if (timeRemaining == 0) {
                    plugin.cooldowns.remove(uuid);
                    cancel();
                }
            }
        };
        task.runTaskTimerAsynchronously(plugin, 5, 20);
    }
}