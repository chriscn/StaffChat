package github.chriscn;

import github.chriscn.command.StaffChatCommand;
import github.chriscn.events.PlayerChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class StaffChat extends JavaPlugin {

    public ArrayList<UUID> inAdminChat = new ArrayList<>();
    public ArrayList<UUID> inStaffChat = new ArrayList<>();

    public Permission adminChat = new Permission("staffchat.adminchat");
    public Permission staffChat = new Permission("staffchat.staffchat");

    public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().addPermission(adminChat);
        Bukkit.getPluginManager().addPermission(staffChat);
        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);

        getCommand("staffchat").setExecutor(new StaffChatCommand(this));

        this.config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void noPermission(CommandSender commandSender) {
        commandSender.sendMessage(config.getString("message.no_permission"));
    }
}
