package github.chriscn.event;

import github.chriscn.StaffChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {

    StaffChat plugin;
    public PlayerDisconnect(StaffChat instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.config.getBoolean("default_channel_on_relog")) {
            if (plugin.masterChannels.containsKey(player.getUniqueId())) {
                plugin.masterChannels.remove(player.getUniqueId());
            }
        }
    }

}
