package com.bsu.fpm.message.bot;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bsu.fpm.message.bot.impl.FemineBot;
import com.bsu.fpm.message.bot.impl.SimpleBot;
import com.bsu.fpm.message.utils.ObjectUtils;

public class BotFactory {

	private static BotFactory factory;

	public static BotFactory getInstance() {
		if (factory == null) {
			synchronized (BotFactory.class) {
				if (factory == null) {
					factory = new BotFactory();
				}
			}
		}
		return factory;
	}

	private Map<String, AbstractBot> bots;

	private AtomicReference<AbstractBot> currentBot = new AtomicReference<AbstractBot>();

	private BotFactory() {
		this(null);
	}
	
	private BotFactory(String currentBotName) {
		super();
		registerBots();
		setCurrentBot(currentBotName);
	}
	
	private void registerBots() {
		AbstractBot[] availableBots = new AbstractBot[] { new FemineBot(), new SimpleBot() };
		this.bots = Arrays.stream(availableBots).filter((b) ->!ObjectUtils.isNull(b))
				.collect(Collectors.toMap(AbstractBot::getBotName, Function.identity()));
	}
	
	public boolean setCurrentBot(String currentBotName) {
		AbstractBot bot;
		synchronized (BotFactory.class) {
			if ( ObjectUtils.isNull(currentBotName)) {
				Optional<AbstractBot> firstBot = bots.values().stream().findFirst();
				bot = firstBot.orElse(null);
			} else {
				bot = bots.get(currentBotName);
			}
			if ( bot == null ) {
				return false;
			}
			currentBot.set(bot);
			return true;
		}
	}
	
	public AbstractBot getCurrentBot() {
		return currentBot.get();
	}
	
	public String[] getBotNames() {
		return bots.keySet().stream().toArray((size)-> new String[size]);
	}
	
}
