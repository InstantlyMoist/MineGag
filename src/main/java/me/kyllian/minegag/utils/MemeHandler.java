package me.kyllian.minegag.utils;

import me.kyllian.minegag.MineGagPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class MemeHandler {

    private MineGagPlugin plugin;
    private URL url;

    public MemeHandler(MineGagPlugin plugin) {
        this.plugin = plugin;
        try {
            url = new URL("https://discord-mem.es/api/action/get_meme");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sendMeme(Player player) {
        new BukkitRunnable() {
            public void run() {
                try {
                    PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);

                    URLConnection connection = url.openConnection();
                    connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    JSONObject object = (JSONObject) new JSONParser().parse(readAll(bufferedReader));

                    playerData.setCurrentTitle((String) object.get("title"));
                    URL newURL = new URL((String) object.get("url"));
                    playerData.setCurrentUrl(newURL.toString());
                    player.sendMessage(plugin.getMessageHandler().getFullURLMessage(playerData.getCurrentUrl()));
                    URLConnection connection1 = newURL.openConnection();
                    connection1.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
                    Image image = ImageIO.read(connection1.getInputStream());
                    if (!playerData.isViewingMemes()) playerData.setChangedItem(player.getInventory().getItemInMainHand());
                    plugin.getMapHandler().sendMap(player, image.getScaledInstance(128, 128, image.SCALE_DEFAULT));
                } catch (Exception exception) {
                    //TODO: Maybe add a default failure image? IDK
                    exception.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
