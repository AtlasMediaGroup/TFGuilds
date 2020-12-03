package me.totalfreedom.tfguilds;

import me.totalfreedom.tfguilds.bridge.TFMBridge;
import me.totalfreedom.tfguilds.command.GuildChatCommand;
import me.totalfreedom.tfguilds.command.GuildChatSpyCommand;
import me.totalfreedom.tfguilds.command.GuildCommand;
import me.totalfreedom.tfguilds.command.TFGuildsCommand;
import me.totalfreedom.tfguilds.config.Config;
import me.totalfreedom.tfguilds.listener.ChatListener;
import me.totalfreedom.tfguilds.listener.JoinListener;
import me.totalfreedom.tfguilds.sql.SQLDatabase;
import me.totalfreedom.tfguilds.sql.SQLGuildData;
import me.totalfreedom.tfguilds.sql.SQLRankData;
import me.totalfreedom.tfguilds.sql.SQLUserData;
import me.totalfreedom.tfguilds.sql.SQLWorldData;
import me.totalfreedom.tfguilds.util.GLog;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TFGuilds extends JavaPlugin
{
    private static TFGuilds plugin;

    public static TFGuilds getPlugin()
    {
        return plugin;
    }

    public Config config;
    public TFMBridge bridge;
    public SQLDatabase sql;
    public SQLGuildData guildData;
    public SQLRankData rankData;
    public SQLWorldData worldData;
    public SQLUserData userData;

    @Override
    public void onEnable()
    {
        plugin = this;
        config = new Config("config.yml");
        bridge = new TFMBridge();
        sql = new SQLDatabase();
        guildData = new SQLGuildData();
        rankData = new SQLRankData();
        worldData = new SQLWorldData();
        userData = new SQLUserData();
        loadCommands();
        loadListeners();
        GLog.info("Enabled " + this.getDescription().getFullName());
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        config.save();
        GLog.info("Disabled " + this.getDescription().getFullName());
    }

    private void loadCommands()
    {
        this.getCommand("guild").setExecutor(new GuildCommand());
        this.getCommand("guildchat").setExecutor(new GuildChatCommand());
        this.getCommand("tfguilds").setExecutor(new TFGuildsCommand());
        this.getCommand("guildchatspy").setExecutor(new GuildChatSpyCommand());
    }

    private void loadListeners()
    {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new JoinListener(), this);
    }
}