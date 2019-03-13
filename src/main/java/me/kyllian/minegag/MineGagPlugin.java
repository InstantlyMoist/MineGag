package me.kyllian.minegag;

import me.kyllian.minegag.commands.MinegagCommand;
import me.kyllian.minegag.utils.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public class MineGagPlugin extends JavaPlugin {

    public int memesViewed = 0;

    private ActionBar actionBar;

    private MapHandler mapHandler;
    private MemeHandler memeHandler;
    private PlayerHandler playerHandler;
    private MessageHandler messageHandler;

    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        initializeHandlers();
        initializeCommands();

        Metrics metrics = new Metrics(this);

        metrics.addCustomChart(new Metrics.SingleLineChart("memes_viewed", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return memesViewed;
            }
        }));
    }

    public void initializeHandlers() {
        actionBar = new ActionBar(this);

        mapHandler = new MapHandler();
        memeHandler = new MemeHandler(this);
        playerHandler = new PlayerHandler(this);
        messageHandler = new MessageHandler(this);

        updateChecker = new UpdateChecker(this, 65417);
    }

    public void initializeCommands() {
        getCommand("minegag").setExecutor(new MinegagCommand(this));
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

    public ActionBar getActionBar() {
        return actionBar;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
}
