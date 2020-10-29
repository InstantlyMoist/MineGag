package me.kyllian.minegag.handlers;

import me.kyllian.minegag.MineGagPlugin;
import me.kyllian.minegag.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
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
        return playerData.computeIfAbsent(player, f -> new PlayerData(plugin, player.getUniqueId()));
    }

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
            if (playerData.getTitle() != null) {
                plugin.getActionBarHandler().sendActionBar(player, plugin.getMessageHandler().getActionbarMessage(playerData.getTitle()));
            }
        }
    }

    public ItemStack getItemInHand(Player player) {
        return Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7") ? player.getItemInHand() : player.getInventory().getItemInMainHand();
    }

    public void setItemInHand(Player player, ItemStack itemStack) {
        if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7")) player.setItemInHand(itemStack);
        else player.getInventory().setItemInMainHand(itemStack);
    }
}
