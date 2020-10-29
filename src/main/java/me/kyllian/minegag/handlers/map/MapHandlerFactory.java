package me.kyllian.minegag.handlers.map;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;

public class MapHandlerFactory {

    private MineGagPlugin plugin;

    public MapHandlerFactory(MineGagPlugin plugin) {
        this.plugin = plugin;
    }

    public MapHandler getMapHandler() {
        String version = Bukkit.getVersion();
        if (version.contains("1.16") || version.contains("1.15") || version.contains("1.14") || version.contains("1.13")) return new MapHandlerNew(plugin);
        else return new MapHandlerOld(plugin);
    }
}
