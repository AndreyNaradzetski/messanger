package com.bsu.fpm.message.bot.impl;

import com.bsu.fpm.message.bot.AbstractBot;

public final class SimpleBot extends AbstractBot {
	
	private static final String BOT_NAME = "Simple"; 

	public SimpleBot() {
		super(BOT_NAME);
	}

	@Override
	public String message(String message) {
		return message;
	}

}
