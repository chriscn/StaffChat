package github.chriscn;

import github.chriscn.command.ChannelCommand;
import github.chriscn.command.StaffChatCommand;
import github.chriscn.events.PlayerChatEvent;
import github.chriscn.util.ChannelEnum;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class StaffChat extends JavaPlugin {

    public ArrayList<UUID> inAdminChat = new ArrayList<>();
    public ArrayList<UUID> inStaffChat = new ArrayList<>();

    public Permission adminChatRead = new Permission("sc.admin.read");
    public Permission adminChatWrite = new Permission("sc.admin.write");
    public Permission staffChatRead = new Permission("sc.staff.read");
    public Permission staffChatWrite = new Permission("sc.staff.write");

    public FileConfiguration config;

    public HashMap<UUID, ChannelEnum> playerChannel = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().addPermission(adminChatRead);
        Bukkit.getPluginManager().addPermission(adminChatWrite);
        Bukkit.getPluginManager().addPermission(staffChatRead);
        Bukkit.getPluginManager().addPermission(staffChatWrite);

        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);

        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("channel").setTabCompleter(new ChannelCommand(this));

        getCommand("listchannel").setExecutor((sender, command, label, args) -> {
            if (sender.hasPermission(adminChatWrite)) {
                   for (ChannelEnum v : ChannelEnum.values()) {
                       sender.sendMessage("Channel: " + v.getName());
                       sender.sendMessage(allWithKey(v).toString());
                   }
            } else {
                noPermission(sender);
            }
            return true;
        });

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
