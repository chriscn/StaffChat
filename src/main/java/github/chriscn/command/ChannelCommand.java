package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChannelCommand implements CommandExecutor {

    StaffChat plugin;
    public ChannelCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                if (accessibleChannels(player).equalsIgnoreCase("")) {
                    player.sendMessage("You don't have any channels you can join :-(");
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "You must specify which channel you'd like to join: " + accessibleChannels(player));
                    return false;
                }
            } else if (args.length == 1) { // specififed a channel
                String channel = args[0].toLowerCase();

                if (plugin.channels.contains(channel)) { // check that channel exists
                    if (player.hasPermission(plugin.channelWrite.get(channel))) { // have permission for that channel
                        if (plugin.playerChannelDB.get(player.getUniqueId()).equals(channel)) {
                            player.sendMessage(ChatColor.YELLOW + "You are already in this channel silly!");
                        } else {
                            plugin.playerChannelDB.remove(player.getUniqueId()); // remove them from master channels
                            plugin.playerChannelDB.put(player.getUniqueId(), channel);
                            player.sendMessage(ChatColor.GREEN + "You are now chatting in " + ChatColor.YELLOW + channel);
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
                    player.sendMessage(ChatColor.RED + "The channel specified, " + channel + ", does not exist or you don't have permission to access it. The channels you can access are: " + accessibleChannels(player));
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

    /**
     * Loops through all the channels providing a list of all the channels that specific player can access.
     * @param player The player to check
     * @return A green coloured list with commas of all the channels
     */
    private String accessibleChannels(Player player) {
        ArrayList<String> channels = new ArrayList<>();

        plugin.channelRead.forEach((channel, permission) -> {
            if (player.hasPermission(permission)) {
                channels.add(channel.toLowerCase());
            }
        });

        channels.add("all"); // manually add the default all channel

        String channelString = ChatColor.GREEN + String.join(", ", channels);

        return channelString.trim();
    }
}
