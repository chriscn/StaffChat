package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class ChannelCommand implements CommandExecutor {

    StaffChat plugin;
    public ChannelCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Set<String> channels = plugin.channelPermissions.keySet();

            if (args.length == 0) {
                if (accessibleChannels(player) == "") {
                    player.sendMessage("You don't have any channels you can join :-(");
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "You must specificy which channel you'd like to join: " + accessibleChannels(player));
                    return false;
                }
            } else if (args.length == 1) { // specififed a channel
                String channel = args[0].toLowerCase();

                if (plugin.channelPermissions.containsKey(channel)) { // check that channel exists
                    if (player.hasPermission(plugin.channelPermissions.get(channel))) { // have permission for that channel
                        if (plugin.masterChannels.get(player.getUniqueId()) == channel) {
                            player.sendMessage(ChatColor.YELLOW + "You are already in this channel silly!");
                        } else {
                            plugin.masterChannels.remove(player.getUniqueId()); // remove them from master channels
                            plugin.masterChannels.put(player.getUniqueId(), channel);
                            player.sendMessage(ChatColor.GREEN + "You are now chatting in " + channel);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have permission for that channel.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "The channel specified, " + channel + ", does not exist. The channels you can access are: " + accessibleChannels(player));
                }
                return true;
            } else { // too many men (arguments) :P
                return false;
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "We only allow players to chat in channels at the moment.");
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

        plugin.channelPermissions.forEach((channel, permission) -> {
            if (player.hasPermission(permission)) {
                channels.add(channel.toLowerCase());
            }
        });

        String channelString = ChatColor.GREEN + String.join(", ", channels);

        return channelString.trim();
    }
}
