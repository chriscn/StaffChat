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

public class VirtualChannel implements Listener {

    private String name;
    private ArrayList<UUID> activeParticipation = new ArrayList<>();

    public Permission readPermission;
    public Permission writePermission;

    StaffChat plugin = new StaffChat();

    public VirtualChannel(String name) {
        this.name = name;
        this.readPermission = new Permission(plugin.config.getString("channel." + this.name + ".read"));
        this.writePermission = new Permission(plugin.config.getString("channel." + this.name + ".write"));

        Bukkit.getPluginManager().addPermission(readPermission);
        Bukkit.getPluginManager().addPermission(writePermission);

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

    private String messageFormat(String configPath, String message) {
        return ChatColor.translateAlternateColorCodes('&', new StaffChat().getConfig().getString(configPath));
    }
}
