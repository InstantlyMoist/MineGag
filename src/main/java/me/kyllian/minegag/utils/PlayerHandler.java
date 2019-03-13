package me.kyllian.minegag.utils;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class PlayerHandler extends BukkitRunnable implements Listener {

    private MineGagPlugin plugin;
    private HashMap<Player, PlayerData> playerData;

    public PlayerHandler(MineGagPlugin plugin) {
        this.plugin = plugin;
        playerData = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        runTaskTimer(plugin, 5, 5);
    }

    public PlayerData getPlayerData(Player player) {
        return playerData.computeIfAbsent(player, f -> new PlayerData(player.getUniqueId()));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = getPlayerData(player);
        if (!playerData.isViewingMemes()) return;
        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            playerData.reset();
            player.sendMessage(plugin.getMessageHandler().getStoppedViewingMessage());
            return;
        }
        if (event.getTo().getY() - event.getFrom().getY() > 0.4) plugin.getMemeHandler().sendMeme(player);
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
        if (playerData.isViewingMemes()) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("minegag.update")) player.sendMessage(plugin.getUpdateChecker().getUpdateMessage());
    }

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
            if (!playerData.isViewingMemes()) return;
            if (playerData.getCurrentTitle() == null) return;
            plugin.getActionBar().sendActionBar(player, plugin.getMessageHandler().getActionbarMessage(playerData.getCurrentTitle()));
            // TODO: Update actionbar
        }
    }
}
