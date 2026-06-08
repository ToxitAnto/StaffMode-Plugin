package com.ckanto.staffmode.util;

import com.ckanto.staffmode.config.StaffConfig;
import com.ckanto.staffmode.model.StaffToolType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class ItemFactory {
    private final StaffConfig config;

    public ItemFactory(StaffConfig config) {
        this.config = config;
    }

    public ItemStack create(StaffToolType type) {
        ItemStack item = new ItemStack(config.itemMaterial(type), 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(config.itemName(type));
        List<String> lore = new ArrayList<String>();
        lore.add(config.itemTag());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
