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
    private Permission channelPermission;
    private boolean disconnectOnQuit;

    public VirtualChannel(StaffChat plugin, String channelName, String messageTemplate, String permissionNode, boolean disconnectOnQuit) {
        this.plugin = plugin;

        this.channelName = channelName;
        this.messageTemplate = messageTemplate;
        this.channelPermission = new Permission(permissionNode);

        this.disconnectOnQuit = disconnectOnQuit;

        Bukkit.getPluginManager().addPermission(this.channelPermission); // registers permission with plugin manager
        Bukkit.getPluginManager().registerEvents(this, plugin);

        plugin.channelPermissions.put(this.channelName, this.channelPermission); // adds channel with permission to main
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.masterChannels.containsKey(player.getUniqueId())) {
            if (plugin.masterChannels.get(player.getUniqueId()).equalsIgnoreCase(channelName)) {
                event.setCancelled(true);
                String msg = ChatColor.translateAlternateColorCodes('&', messageTemplate + " " + event.getMessage());

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission(this.channelPermission)) {
                        p.sendMessage(msg);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (disconnectOnQuit) {
            if (plugin.masterChannels.containsKey(player.getUniqueId())) {
                plugin.masterChannels.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!disconnectOnQuit) {
            if (plugin.masterChannels.containsKey(player.getUniqueId())) {
                player.sendMessage(ChatColor.YELLOW + "Heads up! You are chatting in " + plugin.masterChannels.get(player.getUniqueId()));
            }
        }
    }
}
