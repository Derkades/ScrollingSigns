package com.robinmc.scrollingsigns;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.robinmc.scrollingsigns.utils.Config;

public class Main extends JavaPlugin {
	
	private static Plugin plugin;
	
	public static Plugin getPlugin(){
		return plugin;
	}
	
	@Override
	public void onEnable(){
		plugin = this;
		getCommand("scrollingsign").setExecutor(new ScrollingSignCommand());
		
		for (ScrollingSign sign : Main.getScrollingSigns()){
			new UpdateSignTask(sign).runTaskTimer(plugin, 2*20, 7L);
		}
	}
	
	public static void replaceSignLine(Sign sign, int line, String text){
		sign.setLine(line, text);
		sign.update();
	}
	
	private static String generateRandomId(){
		return (UUID.randomUUID() + "" + UUID.randomUUID() + "" + UUID.randomUUID()).replace("-", "");
	}
	
	public static void addScrollingSign(Sign sign, int line, String text){
		String id = generateRandomId();
		Config.getConfig().createSection(id);
		ConfigurationSection section = Config.getConfig().getConfigurationSection(id);
		Location loc = sign.getBlock().getLocation();
		section.set("loc.x", loc.getBlockX()); //Integer
		section.set("loc.y", loc.getBlockY()); //Integer
		section.set("loc.z", loc.getBlockZ()); //Integer
		section.set("loc.world", loc.getWorld().getName()); //String
		section.set("line", line); //Integer (0-3)
		section.set("text", text); //String
		Main.getPlugin().saveConfig();
		ScrollingSign scrolling = new ScrollingSign(sign, line, text);
		new UpdateSignTask(scrolling).runTaskTimer(plugin, 0L, 7L);
	}
	
	public static List<ScrollingSign> getScrollingSigns(){
		List<ScrollingSign> list = new ArrayList<ScrollingSign>();
		for (String string : Config.getConfig().getKeys(false)){
			ConfigurationSection section = Config.getConfig().getConfigurationSection(string);
			int x = section.getInt("loc.x");
			int y = section.getInt("loc.y");
			int z = section.getInt("loc.z");
			World world = Bukkit.getWorld(section.getString("loc.world"));
			int line = section.getInt("line");
			String text = section.getString("text");
			Block block = new Location(world, x, y, z).getBlock();
			if (block.getType() != Material.SIGN_POST){
				Config.getConfig().set(string, null);
				Bukkit.getLogger().log(Level.WARNING, "Warning: block at " + x + "," + y + "," + z + " is not a sign anymore. This scrolling sign has been removed from the config.");
				Main.getPlugin().saveConfig();
				continue;
			}
			Sign sign = (Sign) new Location(world, x, y, z).getBlock().getState();
			ScrollingSign scroll = new ScrollingSign(sign, line, text);
			list.add(scroll);
		}
		return list;
	}

}
