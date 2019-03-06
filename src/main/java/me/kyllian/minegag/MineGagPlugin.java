package me.kyllian.minegag;

import me.kyllian.minegag.commands.MinegagCommand;
import me.kyllian.minegag.utils.*;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class MineGagPlugin extends JavaPlugin {

    private ActionBar actionBar;

    private MapHandler mapHandler;
    private MemeHandler memeHandler;
    private PlayerHandler playerHandler;
    private MessageHandler messageHandler;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        initializeHandlers();
        initializeCommands();

        Metrics metrics = new Metrics(this);
    }

    public void initializeHandlers() {
        actionBar = new ActionBar(this);

        mapHandler = new MapHandler();
        memeHandler = new MemeHandler(this);
        playerHandler = new PlayerHandler(this);
        messageHandler = new MessageHandler(this);
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
}
