package me.totalfreedom.tfguilds.command;

import me.totalfreedom.tfguilds.Common;
import me.totalfreedom.tfguilds.guild.Guild;
import me.totalfreedom.tfguilds.util.GUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateSubcommand extends Common implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        List<String> BLACKLISTED_NAMES = Arrays.asList(
                "admin", "owner", "moderator", "developer", "console", "dev", "staff", "mod", "sra", "tca", "sta", "sa");

        if (sender instanceof ConsoleCommandSender)
        {
            sender.sendMessage(NO_PERMS);
            return true;
        }

        Player player = (Player) sender;
        String name = StringUtils.join(args, " ", 1, args.length);
        String identifier = GUtil.flatten(name);

        if (Guild.isInGuild(player))
        {
            sender.sendMessage(ChatColor.RED + "You are already in a guild!");
            return true;
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9? ,_-]+$");
        Matcher matcher = pattern.matcher(name);

        if (!matcher.matches())
        {
            sender.sendMessage(ChatColor.RED + "Guild names must be alphanumeric.");
            return true;
        }

        if (Guild.guildExists(identifier))
        {
            sender.sendMessage(ChatColor.RED + "A guild with a name similar to yours already exists!");
            return true;
        }

        for (String blacklisted : BLACKLISTED_NAMES)
        {
            if (args[0].equalsIgnoreCase(blacklisted))
            {
                if (!plugin.bridge.isAdmin(player))
                {
                    player.sendMessage(ChatColor.RED + "You may not use that name.");
                    return true;
                }
            }
        }

        Guild.createGuild(identifier, name, player);
        sender.sendMessage(tl(PREFIX + "Created a guild named \"" + GUtil.colorize(name) + "%p%\"!"));
        Bukkit.broadcastMessage(GUtil.colorize("&a" + sender.getName() + " has created guild &a&l" + name));
        return true;
    }
}