package me.kyllian.minegag.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;

public class MapHandler {

    public void sendMap(Player player, Image image) {
        ItemStack map = new ItemStack(Material.MAP);
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                mapView.setScale(MapView.Scale.CLOSEST);
                mapCanvas.drawImage(0, 0, image);
            }
        });
        if (Bukkit.getVersion().contains("1.13")) {
            MapMeta meta = (MapMeta) map.getItemMeta();
            meta.setMapId(getMapID(mapView));
            map.setItemMeta(meta);
        } else map.setDurability(getMapID(mapView));
        if (Bukkit.getServer().getVersion().contains("1.8")) player.setItemInHand(map);
        else player.getInventory().setItemInMainHand(map);
    }

    public static Class<?> getMapNMS(String name) {
        try {
            return Class.forName("org.bukkit.map." + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static short getMapID(MapView view) {
        try {
            return (short) view.getId();
        } catch (NoSuchMethodError e) {
            try {
                Class<?> MapView = getMapNMS("MapView");
                Object mapID = MapView.getMethod("getId").invoke(view);
                return (short) mapID;
            } catch (Exception ex) {
                return 1;
            }
        }
    }
}
