package github.chriscn.command;

import github.chriscn.StaffChat;
import github.chriscn.channel.VirtualChannel;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatCommand implements TabExecutor {

    StaffChat plugin;
    public ChatCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must specify a channel and a message. Usage " + ChatColor.GREEN + " /schat <channel> <message>");
            } else if (args.length == 1) {
                player.sendMessage(ChatColor.RED + "You must specify a message after your channel. Usage " + ChatColor.GREEN + " /schat <channel> <message>");
            } else { // channel and message;
                String channel = args[0].toLowerCase();
                StringBuilder msg = new StringBuilder();

                for (int i = 1; i < args.length; i++) { // start from the second argument
                    msg.append(args[i]).append(" ");
                }

                String message = msg.toString().trim();

                if (plugin.virtualChannels.containsKey(channel)) {
                    VirtualChannel virtualChannel = plugin.virtualChannels.get(channel);
                    if (player.hasPermission(virtualChannel.getWritePermission())) {
                        virtualChannel.sendToAll(player, message);
                    } else {
                        // doesn't have write permission to send to channel
                    }
                } else {
                    // channel doesn't exist
                }
            }
        } else {

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            Player player = (Player) commandSender;
            return plugin.accessibleChannel(player);
        }
        return null;
    }

}
