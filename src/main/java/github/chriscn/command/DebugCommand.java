package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugCommand implements CommandExecutor {

    StaffChat plugin = new StaffChat();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin.masterChannels.forEach((uuid, channel) -> {
            commandSender.sendMessage("Player " + Bukkit.getPlayer(uuid).getDisplayName() + " Channel " + channel);
        });

        return false;
    }
}
