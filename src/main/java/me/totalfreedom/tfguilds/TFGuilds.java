package me.totalfreedom.tfguilds;

import me.totalfreedom.tfguilds.util.GLog;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.totalfreedom.tfguilds.command.CreateGuildCommand;
import me.totalfreedom.tfguilds.command.GuildChatCommand;
import me.totalfreedom.tfguilds.command.GuildTagCommand;
import me.totalfreedom.tfguilds.command.TfGuildsCommand;
import me.totalfreedom.tfguilds.config.Config;
import me.totalfreedom.tfguilds.listener.ChatManager;

public final class TFGuilds extends JavaPlugin
{
    public static TFGuilds plugin;
    public Config config;
    public Config guilds;

    @Override
    public void onEnable()
    {
        plugin = this;
        enableCommands();
        enableListeners();
        config = new Config(plugin, "config.yml");
        guilds = new Config(plugin, "guilds.yml");
        GLog.info("Enabled");
    }

    @Override
    public void onDisable()
    {
        config.save();
        guilds.save();
        GLog.info("Disabled");
    }

    private void enableCommands()
    {
        this.getCommand("tfguilds").setExecutor(new TfGuildsCommand());
        this.getCommand("createguild").setExecutor(new CreateGuildCommand());
        this.getCommand("guildtag").setExecutor(new GuildTagCommand());
        this.getCommand("guildchat").setExecutor(new GuildChatCommand());
    }

    private void enableListeners()
    {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new ChatManager(), this);
    }
}