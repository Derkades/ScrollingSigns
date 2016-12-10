package com.robinmc.scrollingsigns.utils;

import org.bukkit.configuration.file.FileConfiguration;

import com.robinmc.scrollingsigns.Main;

public class Config {
	
	private static FileConfiguration config = null;
	
	public static FileConfiguration getConfig(){
		if (config == null)
			config = Main.getPlugin().getConfig();
		
		return config;
	}
	
	public static void reloadConfig(){
		Main.getPlugin().saveDefaultConfig();
		Main.getPlugin().reloadConfig();
		config = Main.getPlugin().getConfig();
	}

}
