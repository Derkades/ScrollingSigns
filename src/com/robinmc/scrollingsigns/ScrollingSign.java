package com.robinmc.scrollingsigns;

import org.bukkit.block.Sign;

public class ScrollingSign {
	
	private Sign sign;
	private int line;
	private String text;
	
	ScrollingSign(Sign sign, int line, String text){
		this.sign = sign;
		this.line = line;
		this.text = text;
	}
	
	public Sign getSign(){
		return sign;
	}
	
	public int getLine(){
		return line;
	}
	
	public String getText(){
		return text;
	}

}
