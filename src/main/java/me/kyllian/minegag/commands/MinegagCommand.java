package me.kyllian.minegag.commands;

import me.kyllian.minegag.MineGagPlugin;
import me.kyllian.minegag.utils.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MinegagCommand implements CommandExecutor {

    private MineGagPlugin plugin;

    public MinegagCommand(MineGagPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command commands, String commandLabel, String[] args) {
        if (args.length == 0) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(plugin.getMessageHandler().getNotAPlayerMessage());
                return true;
            }
            Player player = (Player) commandSender;
            if (!player.hasPermission("minegag.view")) {
                player.sendMessage(plugin.getMessageHandler().getNoPermissionMessage());
                return true;
            }
            PlayerData playerData = plugin.getPlayerHandler().getPlayerData(player);
            playerData.setViewingMemes(true);
            playerData.setChangedItem(plugin.getPlayerHandler().getItemInHand(player));
            player.sendMessage(plugin.getMessageHandler().getViewingMemesMessage());
            plugin.getMemeHandler().sendMeme(player);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("minegag.reload")) {
                    commandSender.sendMessage(plugin.getMessageHandler().getNoPermissionMessage());
                    return true;
                }
                plugin.reloadConfig();
                commandSender.sendMessage(plugin.getMessageHandler().getReloadedMessage());
                return true;
            }
            if (args[0].equalsIgnoreCase("update")) {
                if (!commandSender.hasPermission("minegag.update")) {
                    commandSender.sendMessage(plugin.getMessageHandler().getNoPermissionMessage());
                    return true;
                }
                commandSender.sendMessage(plugin.getMessageHandler().getCheckingUpdateMessage());
                plugin.getUpdateChecker().check();
                commandSender.sendMessage(plugin.getUpdateChecker().getUpdateMessage());
                return true;
            }
        }
        commandSender.sendMessage(plugin.getMessageHandler().getUnknownArgumentMessage());
        return true;
    }
}
