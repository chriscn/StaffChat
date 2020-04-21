package github.chriscn;

import github.chriscn.command.ChannelCommand;
import github.chriscn.command.ChatCommand;
import github.chriscn.events.PlayerChatEvent;
import github.chriscn.util.ChannelEnum;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class StaffChat extends JavaPlugin {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);

        getCommand("channel").setExecutor(new ChannelCommand());
        getCommand("chat").setExecutor(new ChatCommand());

        saveDefaultConfig();

        this.config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }

    public void noPermission(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message.no_permission")));
    }

    public void unknownChannel(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.unknown_channel")));
    }

    public void notPlayer(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message.not_player")));
    }

    public ArrayList<UUID> allWithKey(ChannelEnum channel) {
        ArrayList<UUID> result = new ArrayList<>();
        playerChannel.forEach((u, e) -> {
            if (channel == e) {
                result.add(u);
            }
        });
        return result;
    }
}
