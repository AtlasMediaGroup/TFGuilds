package me.totalfreedom.tfguilds.command;

import java.util.ArrayList;
import java.util.List;
import me.totalfreedom.tfguilds.TFGuilds;
import me.totalfreedom.tfguilds.TFMBridge;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Common
{

    public static final String PREFIX = ChatColor.AQUA + "TFGuilds " + ChatColor.DARK_GRAY + "\u00BB " + ChatColor.GRAY;
    public static final String NOT_IN_GUILD = PREFIX + "You are not in a guild.";
    public static final String IN_GUILD = PREFIX + "You are already in a guild.";
    public static final String PLAYER_NOT_FOUND = PREFIX + "That player is not online.";
    public static final String PLAYER_NOT_IN_GUILD = PREFIX + "That player is not in your guild.";
    public static final String IN_GAME_ONLY = PREFIX + "You must be in-game to interact with guilds";
    public static final String USAGE = PREFIX + "Correct usage: " + ChatColor.GOLD;

    public static final TFMBridge tfmBridge = TFGuilds.getPlugin().getTfmBridge();

    public static List<Player> GUILD_CHAT = new ArrayList<>();
}
