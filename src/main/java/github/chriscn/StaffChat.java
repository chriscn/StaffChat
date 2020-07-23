package github.chriscn;

import github.chriscn.channel.VirtualChannel;
import github.chriscn.command.SwitchChannelCommand;
import org.bukkit.configuration.file.FileConfiguration;
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

    public HashMap<UUID, String> masterChannels = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            if(!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info(LOGGING_PREFIX + " Configuration file was not found, generating one now.");
                saveDefaultConfig();
            } else {
                getLogger().info(LOGGING_PREFIX + " Configuration file was found, loading into plugin.");
            }
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, LOGGING_PREFIX + " Error when loading the configuration file. ");
            e.printStackTrace();
        }

        this.config = this.getConfig();

        List<String> channels = config.getStringList("channel");
        ArrayList<VirtualChannel> assignedChannels = new ArrayList<>();

        for (String channel : channels) {
            getLogger().info(LOGGING_PREFIX + " Adding channel with name: " + channel);
            assignedChannels.add(
                    new VirtualChannel(
                        channel,
                        config.getString("channel." + channel + ".message"),
                        config.getString("channel." + channel + ".permission")
                    )
            );
        }

        getCommand("channel").setExecutor(new SwitchChannelCommand());
        getCommand("channel").setTabCompleter(new SwitchChannelCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
    }
}
