package com.robinmc.scrollingsigns;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ScrollingSignCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a player in order to execute this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
		if (args.length != 2)
			return false; //Invalid usage
		
		if (!(args[0].equals("1") || args[0].equals("2") || args[0].equals("3") || args[0].equalsIgnoreCase("4"))){
			return false; //Invalid usage
		}
		
		Block block = player.getTargetBlock((Set<Material>) null, 100);
		
		if (block.getType() != Material.SIGN_POST){
			player.sendMessage(ChatColor.RED + "You are not looking at a sign!");
			return true;
		}
		
		Sign sign = (Sign) block.getState();
		
		Main.addScrollingSign(sign, (Integer.parseInt(args[0]) - 1), args[1].replace("_", " "));
		player.sendMessage(ChatColor.DARK_AQUA + "Added sign with text " + ChatColor.AQUA + args[1] + ChatColor.DARK_AQUA + " on line " + ChatColor.AQUA + args[0]);
		return true;
	}

}
