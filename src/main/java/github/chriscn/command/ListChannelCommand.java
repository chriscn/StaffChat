package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListChannelCommand implements CommandExecutor {

    StaffChat plugin;
    public ListChannelCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> channels = plugin.getConfig().getStringList("channel");

        StringBuilder msg = new StringBuilder();

        msg.append("The available channels are: ");

        channels.forEach(channel -> {
            if (sender.hasPermission(plugin.getConfig().getString("channel." + channel + ".read"))) {
                msg.append(channel).append(", ");
            }
        });

        sender.sendMessage(msg.length() > 0 ? msg.substring(0, msg.length() - 1) : "");
        
        return true;
    }
}
