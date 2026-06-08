package com.ckanto.staffmode.config;

import com.ckanto.staffmode.model.StaffToolType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffConfig {
    private final JavaPlugin plugin;

    public StaffConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String permission() {
        return plugin.getConfig().getString("permission", "staffmode.use");
    }

    public String message(String key) {
        return color(plugin.getConfig().getString("messages." + key, ""));
    }

    public String itemName(StaffToolType type) {
        return color(plugin.getConfig().getString("items." + type.configKey() + ".name", type.defaultName()));
    }

    public int itemSlot(StaffToolType type) {
        return plugin.getConfig().getInt("items." + type.configKey() + ".slot", type.defaultSlot());
    }

    public Material itemMaterial(StaffToolType type) {
        String materialName = plugin.getConfig().getString("items." + type.configKey() + ".material", type.defaultMaterial().name());
        Material material = Material.matchMaterial(materialName);
        return material == null ? type.defaultMaterial() : material;
    }

    public String itemTag() {
        return color(plugin.getConfig().getString("item-tag", "&8StaffMode"));
    }

    public int invseeRange() {
        return plugin.getConfig().getInt("invsee-range", 6);
    }

    public String primaryCommand(String key) {
        return command("commands." + key + ".primary");
    }

    public String fallbackCommand(String key) {
        return command("commands." + key + ".fallback");
    }

    public String command(String path) {
        FileConfiguration fileConfiguration = plugin.getConfig();
        return trimSlash(fileConfiguration.getString(path, ""));
    }

    public String color(String value) {
        return ChatColor.translateAlternateColorCodes('&', value == null ? "" : value);
    }

    private String trimSlash(String command) {
        if (command == null) {
            return "";
        }
        String trimmed = command.trim();
        while (trimmed.startsWith("/")) {
            trimmed = trimmed.substring(1);
        }
        return trimmed;
    }
}
