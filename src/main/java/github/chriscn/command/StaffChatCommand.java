package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    StaffChat plugin;

    public StaffChatCommand(StaffChat instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(plugin.staffChatWrite)) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
            }

            String msg = sb.toString();

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(plugin.staffChatRead)) {
                    p.sendMessage(ChatColor.BLUE + "[STAFF] <" + sender.getName() + "> " + msg);
                }
            }
        } else {
            plugin.noPermission(sender);
        }
        return true;
    }
}
