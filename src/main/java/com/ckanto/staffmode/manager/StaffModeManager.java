package com.ckanto.staffmode.manager;

import com.ckanto.staffmode.StaffModePlugin;
import com.ckanto.staffmode.config.StaffConfig;
import com.ckanto.staffmode.model.StaffToolType;
import com.ckanto.staffmode.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public final class StaffModeManager {
    private final StaffModePlugin plugin;
    private final StaffConfig config;
    private final ItemFactory itemFactory;
    private final Map<UUID, ItemStack[]> savedHotbars = new HashMap<UUID, ItemStack[]>();
    private final java.util.Set<UUID> flightActive = new java.util.HashSet<>();
    private final java.util.Set<UUID> vanishActive = new java.util.HashSet<>();
    private final Random random = new Random();

    public StaffModeManager(StaffModePlugin plugin, StaffConfig config) {
        this.plugin = plugin;
        this.config = config;
        this.itemFactory = new ItemFactory(config);
    }

    public void enter(Player player) {
        if (!savedHotbars.containsKey(player.getUniqueId())) {
            savedHotbars.put(player.getUniqueId(), copyHotbar(player.getInventory()));
        }

        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < 9; slot++) {
            inventory.setItem(slot, null);
        }

        for (StaffToolType type : StaffToolType.values()) {
            int slot = config.itemSlot(type) - 1;
            if (slot >= 0 && slot <= 8) {
                inventory.setItem(slot, itemFactory.create(type));
            }
        }

        player.updateInventory();
        player.sendMessage(config.message("staff-enabled"));
    }

    public void leave(Player player) {
        if (!restore(player, true)) {
            player.sendMessage(config.message("not-in-staff"));
        }
    }

    public boolean restore(Player player, boolean sendMessage) {
        ItemStack[] hotbar = savedHotbars.remove(player.getUniqueId());
        if (hotbar == null) {
            return false;
        }

        PlayerInventory inventory = player.getInventory();
        for (int slot = 0; slot < 9; slot++) {
            inventory.setItem(slot, cloneItem(hotbar[slot]));
        }

        player.updateInventory();
        flightActive.remove(player.getUniqueId());
        vanishActive.remove(player.getUniqueId());
        if (sendMessage) {
            player.sendMessage(config.message("staff-disabled"));
        }
        return true;
    }

    public void clear() {
        savedHotbars.clear();
    }

    public boolean isInStaffMode(Player player) {
        return savedHotbars.containsKey(player.getUniqueId());
    }

    public boolean isStaffItem(ItemStack item) {
        return findTool(item) != null;
    }

    public StaffToolType findTool(ItemStack item) {
        if (item == null || item.getType() == null || !item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore()) {
            return null;
        }

        List<String> lore = meta.getLore();
        if (lore == null || !lore.contains(config.itemTag())) {
            return null;
        }

        for (StaffToolType type : StaffToolType.values()) {
            if (item.getType() == config.itemMaterial(type)) {
                return type;
            }
        }

        return null;
    }

    public void sendPlayerList(Player player) {
        player.sendMessage(config.message("player-list-header"));
        for (Player online : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.WHITE + "- " + online.getName());
        }
        player.sendMessage(config.message("player-list-footer"));
    }

    public void vanish(Player player) {
        player.performCommand(config.primaryCommand("vanish"));
        if (vanishActive.remove(player.getUniqueId())) {
            player.sendMessage(config.message("vanish-disabled"));
        } else {
            vanishActive.add(player.getUniqueId());
            player.sendMessage(config.message("vanish-enabled"));
        }
    }

    public void enableFlight(Player player) {
        player.performCommand(config.primaryCommand("flight"));
        if (flightActive.remove(player.getUniqueId())) {
            player.sendMessage(config.message("flight-disabled"));
        } else {
            flightActive.add(player.getUniqueId());
            player.sendMessage(config.message("flight-enabled"));
        }
    }

    public void randomTeleport(Player player) {
        List<Player> targets = new ArrayList<Player>();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.getUniqueId().equals(player.getUniqueId())) {
                targets.add(online);
            }
        }

        if (targets.isEmpty()) {
            player.sendMessage(config.message("no-random-target"));
            return;
        }

        Player target = targets.get(random.nextInt(targets.size()));
        player.teleport(target.getLocation());
        player.sendMessage(config.message("random-teleport-success").replace("%player%", target.getName()));
    }

    private ItemStack[] copyHotbar(PlayerInventory inventory) {
        ItemStack[] hotbar = new ItemStack[9];
        for (int slot = 0; slot < 9; slot++) {
            hotbar[slot] = cloneItem(inventory.getItem(slot));
        }
        return hotbar;
    }

    private ItemStack cloneItem(ItemStack item) {
        return item == null ? null : item.clone();
    }
}
