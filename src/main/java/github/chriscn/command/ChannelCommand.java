package github.chriscn.command;

import github.chriscn.StaffChat;
import github.chriscn.channel.VirtualChannel;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
                player.sendMessage(ChatColor.RED + "You must specify which channel you'd like to join: " + accessibleChannels(player));
                return false;
            } else if (args.length == 1) { // specified a channel
                String channel = args[0].toLowerCase();

                if (plugin.virtualChannels.containsKey(channel)) { // check that channel exists
                    VirtualChannel virtualChannel = plugin.virtualChannels.get(channel);

                    if (player.hasPermission(virtualChannel.getWritePermission())) { // check write permission for that channel
                        if (plugin.playerChannelDB.containsKey(player.getUniqueId())) {
                            if (plugin.playerChannelDB.get(player.getUniqueId()).equalsIgnoreCase(virtualChannel.getChannelName())) { // check if already in channel
                                player.sendMessage(ChatColor.YELLOW + "You are already in this channel silly!");
                            } else {
                                plugin.playerChannelDB.remove(player.getUniqueId()); // remove them from master channels
                                plugin.playerChannelDB.put(player.getUniqueId(), virtualChannel.getChannelName());
                                player.sendMessage(ChatColor.GREEN + "You are now chatting in " + ChatColor.YELLOW + virtualChannel.getChannelName());
                            }
                        } else {
                            plugin.playerChannelDB.put(player.getUniqueId(), channel);
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
                    player.sendMessage(ChatColor.RED + "The channels you can access are: " + accessibleChannels(player));
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
            List<String> channels = new ArrayList<>();
            Player player = (Player) commandSender;

            plugin.virtualChannels.values().forEach(virtualChannel -> {
                if (player.hasPermission(virtualChannel.getWritePermission())) {
                    channels.add(virtualChannel.getChannelName());
                }
            });

            channels.sort(String::compareToIgnoreCase);

            channels.add("all"); // manually add all channel

            return channels;
        }
        return null;
    }

    /**
     * Loops through all the channels providing a list of all the channels that specific player can access.
     * @param player The player to check
     * @return A green coloured list with commas of all the channels
     */
    private String accessibleChannels(Player player) {
        ArrayList<String> channels = new ArrayList<>();

        plugin.virtualChannels.values().forEach(virtualChannel -> {
            if (player.hasPermission(virtualChannel.getWritePermission())) {
                channels.add(virtualChannel.getChannelName());
            }
        });

        channels.sort(String::compareToIgnoreCase); // sorts them alphabetically

        channels.add("all"); // manually add the default all channel

        String channelString = ChatColor.GREEN + String.join(", ", channels);

        return channelString.trim();
    }

}
