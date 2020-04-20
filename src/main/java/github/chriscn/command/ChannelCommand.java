package github.chriscn.command;

import github.chriscn.StaffChat;
import github.chriscn.util.ChannelEnum;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class ChannelCommand implements TabCompleter {

    StaffChat plugin;
    public ChannelCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.notPlayer(sender);
        } else {
            Player player = (Player) sender;

            if (plugin.playerChannel.containsKey(player.getUniqueId())) {
                plugin.playerChannel.remove(player.getUniqueId());
            }

            player.sendMessage(args.toString());

            switch (args[0].toLowerCase()) {
                case "all":
                    plugin.playerChannel.put(player.getUniqueId(), ChannelEnum.ALL);
                    break;
                case "staff":
                    plugin.playerChannel.put(player.getUniqueId(), ChannelEnum.STAFF);
                    break;
                case "admin":
                    plugin.playerChannel.put(player.getUniqueId(), ChannelEnum.ADMIN);
                    break;
                default:
                    plugin.unknownChannel(sender);
                    return false;
            }

            player.sendMessage(ChatColor.AQUA + "You have been switched to: " + plugin.playerChannel.get(player.getUniqueId()).getName());
        }
        return true;
    }
}
