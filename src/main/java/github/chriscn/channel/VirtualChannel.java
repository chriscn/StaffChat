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

    public VirtualChannel(String channelName, String messageTemplate, String permissionNode) {
        this.channelName = channelName;
        this.messageTemplate = messageTemplate;

        Bukkit.getPluginManager().addPermission(new Permission(permissionNode));
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.masterChannels.containsKey(player.getUniqueId())) {
            if (plugin.masterChannels.get(player.getUniqueId()) == channelName) {
                event.setCancelled(true);

                String msg = messageTemplate + " " + event.getMessage();

                plugin.masterChannels.forEach((uuid, channel) -> {
                    if (channel == channelName) {
                        Bukkit.getPlayer(uuid).sendMessage(msg);
                    }
                });
            }
        }
    }
}
