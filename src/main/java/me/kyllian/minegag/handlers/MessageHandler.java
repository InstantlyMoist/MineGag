package me.kyllian.minegag.handlers;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.ChatColor;

public class MessageHandler {

    private MineGagPlugin plugin;

    public MessageHandler(MineGagPlugin plugin) {
        this.plugin = plugin;
    }

    public String colorTranslate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getViewingMemesMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.ViewingMemes"));
    }

    public String getStoppedViewingMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.StoppedViewing"));
    }

    public String getFullURLMessage(String url) {
        return colorTranslate(plugin.getConfig().getString("Messages.FullURL").replace("%url%", url));
    }

    public String getActionbarMessage(String title) {
        return colorTranslate(plugin.getConfig().getString("Messages.Actionbar").replace("%title%", title));
    }

    public String getNoPermissionMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.NoPermission"));
    }

    public String getNotAPlayerMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.NotAPlayer"));
    }

    public String getUnknownArgumentMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.UnknownArgument"));
    }

    public String getReloadedMessage() {
        return colorTranslate(plugin.getConfig().getString("Messages.Reloaded"));
    }
}
