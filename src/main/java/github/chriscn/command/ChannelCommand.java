package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChannelCommand implements CommandExecutor {

    StaffChat plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.notPlayer(sender);
        } else {
            Player player = (Player) sender;
            List<String> channels = plugin.config.getStringList("channel");

            if (args.length == 0) {
                // listing all channels
                StringBuilder msg = new StringBuilder();
                msg.append("The available channels are: ");

                channels.forEach(channel -> {
                    if (player.hasPermission(plugin.config.getString("channel." + channel + ".read"))) {
                        msg.append(channel).append(", ");
                    }
                });

                player.sendMessage(msg.toString());
                return true;
            } else if (args.length == 1) {
                // switching to a specific channel
                String channel = args[0].toLowerCase();

                if (channels.contains(channel)) {
                    player.sendMessage(ChatColor.GREEN + "You have been switched to " + channel);

                    // implement switching logic

                } else {
                    plugin.unknownChannel(player);
                    player.sendMessage(ChatColor.YELLOW + "Use /channel to list your available channels.");
                    return false;
                }

            } else {
                player.sendMessage(ChatColor.RED + "Unknown argument.");
                return false;
            }
        }
    }
}
