package me.kyllian.minegag.handlers.map;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;

public class MapHandlerFactory {

    private MineGagPlugin plugin;

    public MapHandlerFactory(MineGagPlugin plugin) {
        this.plugin = plugin;
    }

    public MapHandler getMapHandler() {
        String minecraftVersion = Bukkit.getVersion();
        int mainVer = Integer.parseInt(minecraftVersion.split("\\.")[1]);
        return mainVer >= 13 ? new MapHandlerNew(plugin) : new MapHandlerOld(plugin);
    }
}
