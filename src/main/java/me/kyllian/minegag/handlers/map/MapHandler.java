package me.kyllian.minegag.handlers.map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public interface MapHandler {

    void loadData();
    void sendMap(Player player, Image image);
    void resetMap(ItemStack map);

}
