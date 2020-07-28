package github.chriscn;

import github.chriscn.channel.VirtualChannel;
import github.chriscn.command.ChannelCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public final class StaffChat extends JavaPlugin {

    public final String LOGGING_PREFIX = "[STAFFCHAT]";
    public FileConfiguration config;

    public ArrayList<String> channels;
    public HashMap<UUID, String> playerChannelDB;
    public HashMap<String, Permission> channelRead;
    public HashMap<String, Permission> channelWrite;

    public String noPermission;
    public String unknownChannel;
    public String notPlayer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupConfig();
        this.config = this.getConfig();
        this.playerChannelDB = new HashMap<>();
        this.channelRead = new HashMap<>();
        this.channelWrite = new HashMap<>();

        this.noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("messages.no_permission"));
        this.notPlayer = ChatColor.translateAlternateColorCodes('&', config.getString("messages.not_player"));
        this.unknownChannel = ChatColor.translateAlternateColorCodes('&', config.getString("messages.unknown_channel"));

        VirtualChannel adminChannel = new VirtualChannel(this,"admin", "&c[ADMIN]", "staffchat.admin", false);
        VirtualChannel staffChannel = new VirtualChannel(this,"staff", "&e[STAFF]", "staffchat.staff", true);

        getCommand("test").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                commandSender.sendMessage("NAME | CHANNEL");
                playerChannelDB.forEach((uuid, channel) -> {
                    commandSender.sendMessage(Bukkit.getPlayer(uuid).getDisplayName() + " | " + channel);
                });

                commandSender.sendMessage("CHANNEL | PERMISSION");
                channelRead.forEach((channel, permission) -> {
                    commandSender.sendMessage(channel + " | " + permission.getName());
                });

                return true;
            }
        });

        getCommand("channel").setExecutor(new ChannelCommand(this));

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
