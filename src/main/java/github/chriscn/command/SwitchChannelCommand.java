package github.chriscn.command;

import github.chriscn.StaffChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class SwitchChannelCommand implements TabExecutor {

    private StaffChat plugin;

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> channels = new ArrayList<>();


        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {



        return false;
    }
}
