package com.ckanto.staffmode.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;

public final class PlayerTargetFinder {
    private PlayerTargetFinder() {
    }

    public static Player getLookedPlayer(Player player, int range) {
        List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);
        BlockIterator iterator = new BlockIterator(player, range);

        while (iterator.hasNext()) {
            double blockX = iterator.next().getX() + 0.5D;
            double blockY = iterator.next().getY() + 0.5D;
            double blockZ = iterator.next().getZ() + 0.5D;

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof Player) || entity.getUniqueId().equals(player.getUniqueId())) {
                    continue;
                }

                double distanceX = Math.abs(entity.getLocation().getX() - blockX);
                double distanceY = Math.abs(entity.getLocation().getY() + 1.0D - blockY);
                double distanceZ = Math.abs(entity.getLocation().getZ() - blockZ);

                if (distanceX < 0.75D && distanceY < 1.6D && distanceZ < 0.75D) {
                    return (Player) entity;
                }
            }
        }

        return null;
    }
}
