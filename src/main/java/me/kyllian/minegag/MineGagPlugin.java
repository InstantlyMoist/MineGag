package me.kyllian.minegag;

import me.kyllian.minegag.commands.MinegagExecutor;
import me.kyllian.minegag.handlers.ActionBarHandler;
import me.kyllian.minegag.handlers.MemeHandler;
import me.kyllian.minegag.handlers.MessageHandler;
import me.kyllian.minegag.handlers.PlayerHandler;
import me.kyllian.minegag.handlers.map.MapHandler;
import me.kyllian.minegag.handlers.map.MapHandlerFactory;
import me.kyllian.minegag.listeners.ItemHeldListener;
import me.kyllian.minegag.listeners.PlayerMoveListener;
import me.kyllian.minegag.listeners.PlayerQuitListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public class MineGagPlugin extends JavaPlugin {

    public int memesViewed = 0;

    private ActionBarHandler actionBarHandler;

    private MapHandler mapHandler;
    private MemeHandler memeHandler;
    private PlayerHandler playerHandler;
    private MessageHandler messageHandler;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        initializeListners();
        initializeHandlers();
        initializeCommands();

        Metrics metrics = new Metrics(this, 4183);

        metrics.addCustomChart(new Metrics.SingleLineChart("memes_viewed", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return memesViewed;
            }
        }));
    }

    private void initializeListners() {
        new ItemHeldListener(this);
        new PlayerMoveListener(this);
        new PlayerQuitListener(this);
    }

    private void initializeHandlers() {
        actionBarHandler = new ActionBarHandler();

        mapHandler = new MapHandlerFactory(this).getMapHandler();
        memeHandler = new MemeHandler(this);
        playerHandler = new PlayerHandler(this);
        messageHandler = new MessageHandler(this);

    }

    private void initializeCommands() {
        getCommand("minegag").setExecutor(new MinegagExecutor(this));
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public MemeHandler getMemeHandler() {
        return memeHandler;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    public ActionBarHandler getActionBarHandler() {
        return actionBarHandler;
    }

}
