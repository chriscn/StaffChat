package github.chriscn.channel;

import github.chriscn.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;

public class VirtualChannel implements Listener {

    private StaffChat plugin;

    private String channelName;
    private String messageTemplate;
    private Permission channelPermission;

    public VirtualChannel(String channelName, String messageTemplate, String permissionNode) {
        this.channelName = channelName;
        this.messageTemplate = messageTemplate;
        this.channelPermission = new Permission(permissionNode);

        Bukkit.getPluginManager().addPermission(this.channelPermission); // registers permission with plugin manager
        plugin.channelPermissions.put(this.channelName, this.channelPermission); // adds channel with permission to main
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.masterChannels.containsKey(player.getUniqueId())) {
            if (plugin.masterChannels.get(player.getUniqueId()) == channelName) {
                event.setCancelled(true);

                String msg = messageTemplate + " " + event.getMessage();

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission(this.channelPermission)) {
                        p.sendMessage(msg);
                    }
                }
            }
        }
    }
}
