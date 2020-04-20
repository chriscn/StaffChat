package github.chriscn.channel;

import github.chriscn.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.UUID;

public class VirtualChannel implements Listener, CommandExecutor {

    private String name;
    private String commandName;
    private ArrayList<UUID> activeParticipation = new ArrayList<>();

    public Permission readPermission;
    public Permission writePermission;

    public VirtualChannel(String name, String readPermissionNode, String writePermissionNode, String commandName) {
        this.name = name;
        this.commandName = commandName;
        this.readPermission = new Permission(readPermissionNode);
        this.writePermission = new Permission(writePermissionNode);

        Bukkit.getPluginManager().addPermission(readPermission);
        Bukkit.getPluginManager().addPermission(writePermission);

        new StaffChat().getCommand(commandName).setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, new StaffChat());
    }

    public void activeParticipants(Player player) {
        if (player.hasPermission(writePermission)) {
            activeParticipation.add(player.getUniqueId());
        } else {
            new StaffChat().noPermission(player);
        }
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
