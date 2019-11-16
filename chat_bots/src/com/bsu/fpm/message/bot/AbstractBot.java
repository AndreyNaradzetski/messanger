package com.bsu.fpm.message.bot;

public abstract class AbstractBot {

	private final String botName;
	
	public AbstractBot(String botName) {
		super();
		this.botName = botName;
	}

	public String getBotName() {
		return botName;
	}

	public abstract String message(String message);
	
}
