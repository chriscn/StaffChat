package github.chriscn.channel;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;

public class VirtualChannel implements Listener {

    private StaffChat plugin;

    private String channelName;
    private String messageTemplate;
    private Permission channelReadPermission;
    private Permission channelWritePermission;

    public VirtualChannel(StaffChat plugin, String channelName, String messageTemplate, String basePermissionNode) {
        this.plugin = plugin;

        this.channelName = channelName;
        this.messageTemplate = messageTemplate;

        this.channelReadPermission = new Permission(basePermissionNode + ".read");
        this.channelWritePermission = new Permission(basePermissionNode + ".write");

        this.channelWritePermission.setDescription("Allow a player to write a message to the channel " + this.channelName);
        this.channelReadPermission.setDescription("Allow a player to read messages in the channel " + this.channelName);

        this.disconnectOnQuit = disconnectOnQuit;

        Bukkit.getPluginManager().addPermission(this.channelReadPermission); // registers permissions with plugin manager
        Bukkit.getPluginManager().addPermission(this.channelWritePermission);

        Bukkit.getPluginManager().registerEvents(this, plugin);

        plugin.channelRead.put(this.channelName, this.channelReadPermission); // add read permission to hashmap
        plugin.channelWrite.put(this.channelName, this.channelWritePermission); // adds write permission to hashmap
        plugin.channels.add(this.channelName); // add channel to all channels
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.playerChannelDB.containsKey(player.getUniqueId())) {
            if (plugin.playerChannelDB.get(player.getUniqueId()).equalsIgnoreCase(channelName)) {
                event.setCancelled(true);
                String msg = ChatColor.translateAlternateColorCodes('&', messageTemplate + " " + event.getMessage());

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission(this.channelReadPermission)) {
                        p.sendMessage(msg);
                    }
                }
            }
        }
    }
}
