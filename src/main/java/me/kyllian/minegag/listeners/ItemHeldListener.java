package me.kyllian.minegag.listeners;

import me.kyllian.minegag.MineGagPlugin;
import me.kyllian.minegag.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ItemHeldListener implements Listener {

    private MineGagPlugin plugin;

    public ItemHeldListener(MineGagPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (playerData.isViewingMemes()) event.setCancelled(true);
    }
}
