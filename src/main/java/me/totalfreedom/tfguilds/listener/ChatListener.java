package me.totalfreedom.tfguilds.listener;

import me.totalfreedom.tfguilds.Common;
import me.totalfreedom.tfguilds.guild.Guild;
import me.totalfreedom.tfguilds.util.GUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        Guild guild = Guild.getGuild(player);
        if (guild == null)
            return;
        if (Common.IN_GUILD_CHAT.contains(player))
        {
            guild.chat(player.getName(), e.getMessage());
            e.setCancelled(true);
            return;
        }
        if (guild.hasTag())
        {
            e.setFormat(GUtil.colorize(guild.getTag()) + ChatColor.RESET + " " + e.getFormat());
        }
    }
}
