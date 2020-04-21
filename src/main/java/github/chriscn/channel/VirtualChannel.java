package github.chriscn.channel;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
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

    public void addPlayer(Player player) {
        if (player.hasPermission(writePermission)) {
            activeParticipation.add(player.getUniqueId());
        } else {
            new StaffChat().noPermission(player);
        }
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (activeParticipation.contains(player.getUniqueId())) {
            event.setCancelled(true);

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(this.readPermission)) {
                    // p.sendMessage(messageFormat(""));
                    p.sendMessage("TEST " + event.getMessage());
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    private String messageFormat(String configPath, String message) {
        return ChatColor.translateAlternateColorCodes('&', new StaffChat().getConfig().getString(configPath));
    }
}
