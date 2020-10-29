package me.kyllian.minegag.listeners;

import me.kyllian.minegag.MineGagPlugin;
import me.kyllian.minegag.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private MineGagPlugin plugin;

    public PlayerMoveListener(MineGagPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (!playerData.isViewingMemes()) return;
        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            playerData.reset();
            player.sendMessage(plugin.getMessageHandler().getStoppedViewingMessage());
            return;
        }
        if (event.getTo().getY() - event.getFrom().getY() > 0.4) plugin.getMemeHandler().sendMeme(player);
    }
}
