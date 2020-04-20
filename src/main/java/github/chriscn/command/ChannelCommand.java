package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

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
}
