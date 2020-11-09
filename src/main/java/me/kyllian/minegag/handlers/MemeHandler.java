package me.kyllian.minegag.handlers;

import me.kyllian.minegag.MineGagPlugin;
import me.kyllian.minegag.utils.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class MemeHandler {

    private MineGagPlugin plugin;
    private URL url;

    public MemeHandler(MineGagPlugin plugin) {
        this.plugin = plugin;

        try {
            url = new URL("https://meme-api.herokuapp.com/gimme");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void sendMeme(Player player) {
        plugin.memesViewed++;
        new BukkitRunnable() {
            public void run() {
                try {
                    if (url == null) throw new IllegalStateException("URL Parsing failed");
                    PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream(url)));
                    JSONObject object = (JSONObject) new JSONParser().parse(readAll(bufferedReader));
                    URL memeUrl = new URL(object.get("url").toString());
                    player.sendMessage(plugin.getMessageHandler().getFullURLMessage(memeUrl.toString()));
                    Image image = ImageIO.read(getInputStream(memeUrl));
                    if (!playerData.isViewingMemes()) playerData.setOldItem(player.getInventory().getItemInMainHand());
                    playerData.setTitle(plugin.getMessageHandler().getActionbarMessage(object.get("title").toString()));
                    plugin.getMapHandler().sendMap(player, image.getScaledInstance(128, 128, image.SCALE_DEFAULT));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    private InputStream getInputStream(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        return urlConnection.getInputStream();
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
