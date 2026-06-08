package com.ckanto.staffmode;

import com.ckanto.staffmode.command.StaffCommand;
import com.ckanto.staffmode.config.StaffConfig;
import com.ckanto.staffmode.listener.StaffItemListener;
import com.ckanto.staffmode.manager.StaffModeManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffModePlugin extends JavaPlugin {
    private StaffConfig staffConfig;
    private StaffModeManager staffModeManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        staffConfig = new StaffConfig(this);
        staffModeManager = new StaffModeManager(this, staffConfig);
        StaffCommand staffCommand = new StaffCommand(staffConfig, staffModeManager);
        getCommand("staff").setExecutor(staffCommand);
        getCommand("leavestaff").setExecutor(staffCommand);
        Bukkit.getPluginManager().registerEvents(new StaffItemListener(this, staffConfig, staffModeManager), this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            staffModeManager.restore(player, false);
        }
        staffModeManager.clear();
    }
}
