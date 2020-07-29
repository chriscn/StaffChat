package github.chriscn.channel;

import github.chriscn.StaffChat;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

public class VirtualChannel implements Listener {

    private StaffChat plugin;

    private String channelName;
    private String prefix;
    private Permission readPermission;
    private Permission writePermission;

    public VirtualChannel(StaffChat plugin, String channelName, String prefix) {
        this.plugin = plugin;

        this.channelName = channelName;
        this.prefix = prefix;

        this.readPermission = new Permission("staffchat." + this.channelName + ".read");
        this.writePermission = new Permission("staffchat." + this.channelName + ".write");

        this.writePermission.setDescription("Allow a player to write a message to the channel " + this.channelName);
        this.readPermission.setDescription("Allow a player to read messages in the channel " + this.channelName);

        Bukkit.getPluginManager().addPermission(this.readPermission); // registers permissions with plugin manager
        Bukkit.getPluginManager().addPermission(this.writePermission);

        Bukkit.getPluginManager().registerEvents(this, plugin);

        plugin.virtualChannels.put(this.channelName, this);
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.playerChannelDB.containsKey(player.getUniqueId())) {
            if (plugin.playerChannelDB.get(player.getUniqueId()).equals(this)) {
                event.setCancelled(true);

                sendToAll(player, event.getMessage());
            }
        }
    }

    public void sendToAll(Player sender, String message) {
        String prefix = ChatColor.translateAlternateColorCodes('&', this.prefix + " " + sender.getDisplayName());
        String msg = prefix + ChatColor.WHITE + ": " + message;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(this.getReadPermission())) {
                p.sendMessage(msg);
            }
        }
    }

    public Permission getReadPermission() {
        return this.readPermission;
    }

    public Permission getWritePermission() {
        return this.writePermission;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getPrefix() {
        return this.prefix;
    }

    // debugging ease of use.
    public String getDebug() {
        return this.channelName + " " + this.prefix + " " + this.readPermission + " " + this.writePermission;
    }
}
