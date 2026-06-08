package com.ckanto.staffmode.model;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum StaffToolType {
    PLAYER_LIST("player-list", 3, Material.COMPASS, ChatColor.AQUA + "" + ChatColor.BOLD + "Compass " + ChatColor.GRAY + "- Player List"),
    VANISH("vanish", 4, Material.EMERALD, ChatColor.GREEN + "" + ChatColor.BOLD + "Smeraldo " + ChatColor.GRAY + "- Vanish"),
    FLIGHT("flight", 5, Material.FEATHER, ChatColor.YELLOW + "" + ChatColor.BOLD + "Piuma " + ChatColor.GRAY + "- Flight"),
    RANDOM_TELEPORT("random-teleport", 6, Material.BLAZE_ROD, ChatColor.BLUE + "" + ChatColor.BOLD + "Blaze Rod " + ChatColor.GRAY + "- Random TP"),
    INVSEE("invsee", 7, Material.CHEST, ChatColor.GOLD + "" + ChatColor.BOLD + "Cassa " + ChatColor.GRAY + "- InvSee");

    private final String configKey;
    private final int defaultSlot;
    private final Material defaultMaterial;
    private final String defaultName;

    StaffToolType(String configKey, int defaultSlot, Material defaultMaterial, String defaultName) {
        this.configKey = configKey;
        this.defaultSlot = defaultSlot;
        this.defaultMaterial = defaultMaterial;
        this.defaultName = defaultName;
    }

    public String configKey() {
        return configKey;
    }

    public int defaultSlot() {
        return defaultSlot;
    }

    public Material defaultMaterial() {
        return defaultMaterial;
    }

    public String defaultName() {
        return defaultName;
    }
}
