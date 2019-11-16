package com.bsu.fpm.message.bot.impl;

import java.util.Arrays;

import com.bsu.fpm.message.bot.AbstractBot;
import com.bsu.fpm.message.utils.StringUtils;

public final class FemineBot extends AbstractBot {
	
	private static final String BOT_NAME = "Feminizator"; 

	public FemineBot() {
		super(BOT_NAME);
	}

	@Override
	public String message(String message) {
		if (!StringUtils.isEmpty( message )) {
			return Arrays.stream( message.split("((?<= )|(?= ))"))
					.map((str)->{ return StringUtils.isEmpty(str) ? str : str + "-ka";})
					.reduce((a,b)->a + b)
					.get();
		}
		return message;
	}

}
