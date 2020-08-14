package me.totalfreedom.tfguilds.listener;

import me.totalfreedom.tfguilds.Common;
import me.totalfreedom.tfguilds.TFGuilds;
import me.totalfreedom.tfguilds.config.ConfigEntry;
import me.totalfreedom.tfguilds.guild.Guild;
import me.totalfreedom.tfguilds.guild.GuildRank;
import me.totalfreedom.tfguilds.util.GUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    private static final TFGuilds plugin = TFGuilds.getPlugin();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        Guild guild = Guild.getGuild(player);
        if (guild == null)
        {
            return;
        }

        if (Common.IN_GUILD_CHAT.contains(player))
        {
            guild.chat(player.getName(), e.getMessage());
            e.setCancelled(true);
            return;
        }

        GuildRank rank = null;
        for (GuildRank r : guild.getRanks())
        {
            if (r.getMembers().contains(player.getName()))
            {
                rank = r;
            }
        }

        String display;
        if (rank == null)
        {
            if (guild.getOwner().equals(player.getName()))
            {
                display = "Guild Owner";
            }
            else if (guild.hasModerator(player.getName()))
            {
                display = "Guild Moderator";
            }
            else
            {
                display = "Guild Member";
            }
        }
        else
        {
            display = rank.getName();
        }

        if (!ConfigEntry.GUILD_TAGS_ENABLED.getBoolean())
        {
            return;
        }

        if (plugin.players.contains(player.getName()))
        {
            if (!plugin.players.getBoolean(player.getName() + ".tag"))
            {
                return;
            }
        }

        if (guild.hasTag())
        {
            e.setFormat(GUtil.colorize(guild.getTag().replace("%rank%", display)) + ChatColor.RESET + " " + e.getFormat());
        }
    }
}