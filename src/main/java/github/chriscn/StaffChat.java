package github.chriscn;

import github.chriscn.channel.VirtualChannel;
import github.chriscn.command.SwitchChannelCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public final class StaffChat extends JavaPlugin {

    public final String LOGGING_PREFIX = "[STAFFCHAT]";
    public FileConfiguration config;

    public HashMap<UUID, String> masterChannels = new HashMap<>();
    public HashMap<String, Permission> channelPermissions = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupConfig();
        this.config = this.getConfig();

        VirtualChannel adminChannel = new VirtualChannel("admin", "&c[ADMIN]", "staffchat.admin");
        VirtualChannel staffChannel = new VirtualChannel("staff", "&e[STAFF]", "staffchat.staff");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }

    private void setupConfig() {
        try {
            if(!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info(LOGGING_PREFIX + " Configuration file was not found, generating one now.");
                this.saveDefaultConfig();
            } else {
                getLogger().info(LOGGING_PREFIX + " Configuration file was found, loading into plugin.");
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, LOGGING_PREFIX + " Error when loading the configuration file. ");
            e.printStackTrace();
        }
    }
}
