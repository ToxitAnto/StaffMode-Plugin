package com.ckanto.staffmode.command;

import com.ckanto.staffmode.config.StaffConfig;
import com.ckanto.staffmode.manager.StaffModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class StaffCommand implements CommandExecutor {
    private final StaffConfig config;
    private final StaffModeManager staffModeManager;

    public StaffCommand(StaffConfig config, StaffModeManager staffModeManager) {
        this.config = config;
        this.staffModeManager = staffModeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(config.message("only-players"));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(config.permission())) {
            player.sendMessage(config.message("no-permission"));
            return true;
        }

        if (command.getName().equalsIgnoreCase("leavestaff") || isLeaveArgument(args)) {
            staffModeManager.leave(player);
            return true;
        }

        staffModeManager.enter(player);
        return true;
    }

    private boolean isLeaveArgument(String[] args) {
        return args.length > 0 && (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("leave"));
    }
}
