package github.chriscn;

import github.chriscn.channel.VirtualChannel;
import github.chriscn.command.ChannelCommand;
import github.chriscn.command.ChatCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public final class StaffChat extends JavaPlugin {

    public final String LOGGING_PREFIX = "[STAFFCHAT]";
    public FileConfiguration config;

    public HashMap<UUID, VirtualChannel> playerChannelDB;
    public HashMap<String, VirtualChannel> virtualChannels;

    public String noPermission;
    public String notPlayer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupConfig();
        this.config = this.getConfig();

        this.playerChannelDB = new HashMap<>();
        this.virtualChannels = new HashMap<>();

        this.noPermission = getConfigPath("messages.no_permission");
        this.notPlayer = getConfigPath("messages.not_player");

        new VirtualChannel(this,"admin", "&c[ADMIN]", "staffchat.admin");
        new VirtualChannel(this, "staff", "&e[STAFF]", "staffchat.staff");

        getCommand("channel").setExecutor(new ChannelCommand(this));
        getCommand("schat").setExecutor(new ChatCommand(this));
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

    /**
     * Gets all channels that a player has write access to
     * @param player Player to check
     * @return A List<String> of all channels that the player can write to
     */
    public List<String> accessibleChannel(Player player) {
        if (player != null) {
            List<String> channels = new ArrayList<>();
            virtualChannels.values().forEach(virtualChannel -> {
                if (player.hasPermission(virtualChannel.getWritePermission())) {
                    channels.add(virtualChannel.getChannelName());
                }
            });

            channels.sort(String::compareToIgnoreCase);
            channels.add("all");
            return channels;
        } else {
            return null;
        }
    }

    public String getConfigPath(String configPath) {
        if (config.getString(configPath) == null || config.getString(configPath).isEmpty()) {
            return ChatColor.RED + "You must define a value in your config. Config path for troubleshooting " + ChatColor.YELLOW + configPath;
        } else {
            return ChatColor.translateAlternateColorCodes('&', config.getString(configPath));
        }
    }
}
