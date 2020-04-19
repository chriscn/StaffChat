package github.chriscn.events;

import github.chriscn.StaffChat;
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
        UUID uuid = player.getUniqueId();
    }

}
