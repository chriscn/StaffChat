package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {

    StaffChat plugin;
    public DebugCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage("testing");

        plugin.masterChannels.forEach((uuid, channel) -> {
            commandSender.sendMessage("Player " + Bukkit.getPlayer(uuid).getDisplayName() + " Channel " + channel);
        });

        plugin.channelPermissions.forEach((channel, permission) -> {
            commandSender.sendMessage("Channel " + channel + " permssion " + permission.getName());
        });

        plugin.config.getKeys(true).forEach(key -> {
            commandSender.sendMessage(key);
        });

        plugin.config.getConfigurationSection("channel").getKeys(true).forEach(key -> {
            commandSender.sendMessage(key);
        });

        return true;
    }
}
