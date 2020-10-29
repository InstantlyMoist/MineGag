package me.kyllian.minegag.utils;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerData {

    private MineGagPlugin plugin;
    private UUID uuid;

    private String title;
    private ItemStack oldItem;

    public PlayerData(MineGagPlugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    public void reset() {
        Player player = Bukkit.getPlayer(uuid);
        plugin.getMapHandler().resetMap(plugin.getPlayerHandler().getItemInHand(player));
        plugin.getPlayerHandler().setItemInHand(player, oldItem);
        oldItem = null;
        title = null;
        plugin.getActionBarHandler().sendActionBar(player, "");
    }

    public boolean isViewingMemes() {
        return oldItem != null;
    }

    public ItemStack getOldItem() {
        return oldItem;
    }

    public void setOldItem(ItemStack oldItem) {
        this.oldItem = oldItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
