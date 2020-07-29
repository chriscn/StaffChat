package github.chriscn.command;

import github.chriscn.StaffChat;
import github.chriscn.channel.VirtualChannel;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class ChannelCommand implements TabExecutor {

    StaffChat plugin;
    public ChannelCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must specify which channel you'd like to join: " + ChatColor.GREEN + String.join(", ", plugin.accessibleChannel(player)));
                return false;
            } else if (args.length == 1) { // specified a channel
                String channel = args[0].toLowerCase();

                if (plugin.virtualChannels.containsKey(channel)) { // check that channel exists
                    VirtualChannel virtualChannel = plugin.virtualChannels.get(channel); // converts string channel to a virtual channel

                    if (player.hasPermission(virtualChannel.getWritePermission())) { // check write permission for that channel
                        if (plugin.playerChannelDB.containsKey(player.getUniqueId())) {
                            if (plugin.playerChannelDB.get(player.getUniqueId()).equals(virtualChannel)) { // check if already in that same channel
                                player.sendMessage(ChatColor.YELLOW + "You are already in this channel.");
                            } else {
                                plugin.playerChannelDB.remove(player.getUniqueId()); // remove them from master channels
                                plugin.playerChannelDB.put(player.getUniqueId(), virtualChannel);
                                player.sendMessage(ChatColor.GREEN + "You are now chatting in " + ChatColor.YELLOW + virtualChannel.getChannelName());
                            }
                        } else {
                            plugin.playerChannelDB.put(player.getUniqueId(), virtualChannel);
                            player.sendMessage(ChatColor.GREEN + "You are now chatting in " + ChatColor.YELLOW + virtualChannel.getChannelName());
                        }
                    } else {
                        player.sendMessage(plugin.noPermission);
                    }
                } else if (channel.equalsIgnoreCase("all")) {
                    if (plugin.playerChannelDB.containsKey(player.getUniqueId())) {
                        plugin.playerChannelDB.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + "You are now chatting in " + ChatColor.YELLOW + "all");
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You are already in this channel.");
                    }
                    return true;
                } else {
                    // TODO
                    // Rework this error message
                    player.sendMessage(ChatColor.RED + "The channel specified, " + ChatColor.YELLOW + channel + ChatColor.RED + ", does not exist or you don't have permission to access it.");
                    player.sendMessage(ChatColor.RED + "The channels you can access are: " + ChatColor.GREEN + String.join(", ", plugin.accessibleChannel(player)));
                }
                return true;
            } else { // too many men (arguments) :P
                player.sendMessage(ChatColor.RED + "You've used too many arguments, please double check your syntax.");
                return false;
            }
        } else {
            commandSender.sendMessage(plugin.notPlayer);
            return true;
        }
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
