package me.kyllian.minegag.utils;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerData {

    private MineGagPlugin plugin;
    private UUID uuid;

    private boolean viewingMemes;
    private String currentTitle;
    private String currentUrl;
    private ItemStack changedItem;

    public PlayerData(MineGagPlugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;
    }

    public void reset() {
        Player player = Bukkit.getPlayer(uuid);
        viewingMemes = false;
        currentTitle = null;
        currentTitle = null;
        plugin.getPlayerHandler().setItemInHand(player, changedItem);
        changedItem = null;
    }

    public boolean isViewingMemes() {
        return viewingMemes;
    }

    public void setViewingMemes(boolean viewingMemes) {
        this.viewingMemes = viewingMemes;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public ItemStack getChangedItem() {
        return changedItem;
    }

    public void setChangedItem(ItemStack changedItem) {
        this.changedItem = changedItem;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }
}
