package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminChatCommand implements CommandExecutor {

    StaffChat plugin;
    public AdminChatCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(plugin.adminChatWrite)) {

        } else if (sender.hasPermission(plugin.adminChatRead)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to write to this channel, only read it. Please contact your server administrator if you believe this is an error.");
            return true;
        } else {
            plugin.noPermission(sender);
            return true;
        }
    }
}
