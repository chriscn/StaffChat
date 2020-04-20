package github.chriscn.events;

import github.chriscn.StaffChat;
import github.chriscn.util.ChannelEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChatEvent implements Listener {

    StaffChat plugin;

    public PlayerChatEvent(StaffChat instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!(plugin.playerChannel.get(player.getUniqueId()) == ChannelEnum.ALL)) {
            if (plugin.playerChannel.get(player.getUniqueId()) == ChannelEnum.STAFF) {
                event.setCancelled(true);
            }
        }
    }

}
