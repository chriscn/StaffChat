package github.chriscn.command;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatCommand implements CommandExecutor {

    StaffChat plugin = new StaffChat();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.notPlayer(sender);
            return true;
        } else {
            Player player = (Player) sender;
            ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));


            if (args.length == 0 || args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need to specify a channel and a message");
                return false;
            } else {
                String channel = args[0].toLowerCase();
                StringBuilder msg = new StringBuilder();

                if (true) { // there should be a check to make sure that the channel exists
                    if (true) { // does the player have permission

                    } else {

                    }
                } else {
                    // channel doesn't exit
                    // use /channel to get list of channels
                }
            }

        }

        return false;
    }
}
