package github.chriscn;

import github.chriscn.command.StaffChatCommand;
import github.chriscn.events.PlayerChatEvent;
import net.md_5.bungee.api.ChatColor;
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

    public Permission adminChatRead = new Permission("sc.admin.read");
    public Permission adminChatWrite = new Permission("sc.admin.write")
    public Permission staffChatRead = new Permission("sc.staff.read");
    public Permission staffChatWrite = new Permission("sc.staff.write")

    public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().addPermission(adminChatRead);
        Bukkit.getPluginManager().addPermission(adminChatWrite);
        Bukkit.getPluginManager().addPermission(staffChatRead);
        Bukkit.getPluginManager().addPermission(staffChatWrite);

        Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);

        getCommand("staffchat").setExecutor(new StaffChatCommand(this));

        this.config = getConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void noPermission(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("message.no_permission")));
    }
}
