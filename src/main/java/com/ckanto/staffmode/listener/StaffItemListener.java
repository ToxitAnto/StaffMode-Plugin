package com.ckanto.staffmode.listener;

import com.ckanto.staffmode.StaffModePlugin;
import com.ckanto.staffmode.config.StaffConfig;
import com.ckanto.staffmode.manager.StaffModeManager;
import com.ckanto.staffmode.model.StaffToolType;
import com.ckanto.staffmode.util.PlayerTargetFinder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public final class StaffItemListener implements Listener {
    private final StaffModePlugin plugin;
    private final StaffConfig config;
    private final StaffModeManager staffModeManager;

    public StaffItemListener(StaffModePlugin plugin, StaffConfig config, StaffModeManager staffModeManager) {
        this.plugin = plugin;
        this.config = config;
        this.staffModeManager = staffModeManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (!staffModeManager.isInStaffMode(player)) {
            return;
        }

        StaffToolType toolType = staffModeManager.findTool(item);
        if (toolType == null) {
            return;
        }

        event.setCancelled(true);
        if (toolType == StaffToolType.PLAYER_LIST && isLeftOrRight(event.getAction())) {
            staffModeManager.sendPlayerList(player);
        } else if (toolType == StaffToolType.VANISH && isLeftOrRight(event.getAction())) {
            staffModeManager.vanish(player);
        } else if (toolType == StaffToolType.FLIGHT && isLeftOrRight(event.getAction())) {
            staffModeManager.enableFlight(player);
        } else if (toolType == StaffToolType.RANDOM_TELEPORT && isLeftOrRight(event.getAction())) {
            staffModeManager.randomTeleport(player);
        } else if (toolType == StaffToolType.INVSEE && isRight(event.getAction())) {
            Player target = PlayerTargetFinder.getLookedPlayer(player, config.invseeRange());
            if (target != null) {
                player.performCommand(config.primaryCommand("invsee") + " " + target.getName());
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (staffModeManager.isInStaffMode(event.getPlayer()) && staffModeManager.isStaffItem(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || !staffModeManager.isInStaffMode((Player) event.getWhoClicked())) {
            return;
        }

        boolean hotbarStaffSlot = event.getSlot() >= 0 && event.getSlot() <= 8 && staffModeManager.isStaffItem(event.getCurrentItem());
        boolean numberKeyStaffSlot = event.getHotbarButton() >= 0 && event.getHotbarButton() <= 8;
        if (hotbarStaffSlot || numberKeyStaffSlot || staffModeManager.isStaffItem(event.getCursor())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        staffModeManager.restore(event.getPlayer(), false);
    }

    private boolean isLeftOrRight(Action action) {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || isRight(action);
    }

    private boolean isRight(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }
}
