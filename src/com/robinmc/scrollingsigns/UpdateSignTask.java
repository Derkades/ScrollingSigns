package com.robinmc.scrollingsigns;

import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

import com.robinmc.scrollingsigns.utils.Scroller;

public class UpdateSignTask extends BukkitRunnable {
	
	Scroller scroller;
	Sign sign;
	int line;
	String text;
	
	UpdateSignTask(ScrollingSign sign){
		super();
		scroller = new Scroller(sign.getText(), 18, 5, '&');
		this.sign = sign.getSign();
		line = sign.getLine();
		this.text = sign.getText();
	}
	
	@Override
	public void run() {
		sign.setLine(line, scroller.next());
		sign.update();
	}

}
