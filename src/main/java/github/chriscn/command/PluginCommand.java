package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

// This is just a debug / administrator command
public class PluginCommand implements TabExecutor {

    StaffChat plugin;
    public PluginCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                return false;
            } else if (args.length == 1) {
                String firstArgument = args[0].toLowerCase();
                if (firstArgument == "channels") {
                    plugin.virtualChannels.values().forEach(vc -> {
                        player.sendMessage(vc.getDebug());
                    });
                } else if (firstArgument == "config") {
                    FileConfiguration test = plugin.config;
                    player.sendMessage(test.getKeys(true).toArray().toString());

                }
            } else {
                return false;
            }
            return false;
        } else {
            commandSender.sendMessage(plugin.NOT_PLAYER);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
